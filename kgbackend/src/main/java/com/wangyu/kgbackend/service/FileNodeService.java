package com.wangyu.kgbackend.service;

import com.wangyu.kgbackend.dao.FileNodeDao;
import com.wangyu.kgbackend.pojo.FileNode;
import com.wangyu.kgbackend.util.Neo4jUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author wongy
 */
@Service
public class FileNodeService {
    @Autowired
    FileNodeDao fileNodeDao;
    @Autowired
    SubGraphService subGraphService;

    /**
     * 还原文件目录的结构
     * @return 文件目录的根节点
     */
    public FileNode listFileList(){
        List<FileNode> fileNodes = fileNodeDao.findAll(new Sort(Sort.Direction.ASC,"fileNodeLevel"));
        // fileNodeQueue 中存放按照所在层数排序好的所有节点
        Queue<FileNode> fileNodeQueue = new LinkedList<FileNode>();
        fileNodeQueue.addAll(fileNodes);
        // 文件目录的根节点
        FileNode root = fileNodeQueue.poll();
        // 之后会逐层的将 x+1 层的节点放到对应的 x 层中的父节点的 children 属性中去
        // parents 存放 x 层节点
        Queue<FileNode> parents = new LinkedList<FileNode>();
        // children 存放 x+1 层节点
        Queue<FileNode> children = new LinkedList<FileNode>();
        parents.offer(root);
        // 当前那一层是表示父节点层，即 x 层
        int currentLevel = 1;
        // 当前操作的节点
        FileNode tempNode;

        if(!fileNodeQueue.isEmpty()){
            tempNode = fileNodeQueue.poll();
        } else {
            return root;
        }

        do{
            // 出队，不断地取出x + 1 层的节点
            while (tempNode.getFileNodeLevel() == currentLevel + 1) {
                children.offer(tempNode);
                if (!fileNodeQueue.isEmpty()) {
                    tempNode = fileNodeQueue.poll();
                } else {
                    break;
                }
            }
            // 将 x+1 层节点和 x 层匹配
            for (FileNode kid : children){
                for (FileNode father: parents){
                    if (father.getJudgeFolder()==true && kid.getParentId() == father.getId()){
                        father.getChildren().add(kid);
                        break;
                    }
                }
            }
            // 进行完一轮进行下一轮
            parents.clear();
            parents.addAll(children);
            children.clear();
            currentLevel++;
            // 没有新的子节点了，跳出循环
            if (tempNode.getFileNodeLevel() <= currentLevel) {
                break;
            }
        }while (true);
        return root;
    }

    /**
     * 添加一个新的文件节点
     * @param fileNode 新的文件节点
     */
    public void addFileNode(FileNode fileNode){
        fileNodeDao.save(fileNode);
    }

    /**
     * 初始化文件列表
     * 删除所有节点，并且添加一个根节点
     */
    public void initializeFileList(){
        fileNodeDao.deleteAllInBatch();
        FileNode filenode = new FileNode();
        filenode.setId(1);
        filenode.setLabel("root");
        filenode.setFileNodeLevel(1);
        filenode.setParentId(0);
        filenode.setJudgeFolder(true);
        addFileNode(filenode);
    }

    /**
     * 删除一个文件节点
     * @param fileNode 传入的文件节点
     */
    public void deleteFileNode(FileNode fileNode){
        List<FileNode> children = fileNode.getChildren();
        for (FileNode node: children) {
            deleteFileNode(node);
        }
        Neo4jUtil.deleteByFileId(fileNode.getId()+"");
        subGraphService.deleteByFileNode(fileNode);
        fileNodeDao.deleteById(fileNode.getId());
    }
}
