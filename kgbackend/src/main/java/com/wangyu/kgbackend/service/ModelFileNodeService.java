package com.wangyu.kgbackend.service;

import com.wangyu.kgbackend.dao.ModelDao;
import com.wangyu.kgbackend.dao.ModelFileNodeDao;
import com.wangyu.kgbackend.pojo.Model;
import com.wangyu.kgbackend.pojo.ModelFileNode;
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
public class ModelFileNodeService {
    @Autowired
    ModelFileNodeDao modelFileNodeDao;
    @Autowired
    ModelService modelService;

    /**
     * 还原文件目录的结构
     * @return 文件目录的根节点
     */
    public ModelFileNode listModelFileList(){
        List<ModelFileNode> modelFileNodes = modelFileNodeDao.findAll(new Sort(Sort.Direction.ASC,"fileNodeLevel"));
        // modelFileNodeQueue 中存放按照所在层数排序好的所有节点
        Queue<ModelFileNode> modelFileNodeQueue = new LinkedList<ModelFileNode>();
        modelFileNodeQueue.addAll(modelFileNodes);
        // 文件目录的根节点
        ModelFileNode root = modelFileNodeQueue.poll();
        // 之后会逐层的将 x+1 层的节点放到对应的 x 层中的父节点的 children 属性中去
        // parents 存放 x 层节点
        Queue<ModelFileNode> parents = new LinkedList<ModelFileNode>();
        // children 存放 x+1 层节点
        Queue<ModelFileNode> children = new LinkedList<ModelFileNode>();
        parents.offer(root);
        // 当前那一层是表示父节点层，即 x 层
        int currentLevel = 1;
        // 当前操作的节点
        ModelFileNode tempNode;

        if(!modelFileNodeQueue.isEmpty()){
            tempNode = modelFileNodeQueue.poll();
        } else {
            return root;
        }

        do{
            // 出队，不断地取出x + 1 层的节点
            while (tempNode.getFileNodeLevel() == currentLevel + 1) {
                children.offer(tempNode);
                if (!modelFileNodeQueue.isEmpty()) {
                    tempNode = modelFileNodeQueue.poll();
                } else {
                    break;
                }
            }
            // 将 x+1 层节点和 x 层匹配
            for (ModelFileNode kid : children){
                for (ModelFileNode father: parents){
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
     * @param modelFileNode 新的文件节点
     */
    public void addModelFileNode(ModelFileNode modelFileNode){
        modelFileNodeDao.save(modelFileNode);
    }

    /**
     * 初始化文件列表
     * 删除所有节点，并且添加一个根节点
     */
    public void initializeModelFileList(){
        // 需要删除对应的模型先
        modelService.deleteAll();
        modelFileNodeDao.deleteAllInBatch();
        ModelFileNode modelFilenode = new ModelFileNode();
        modelFilenode.setId(1);
        modelFilenode.setLabel("root");
        modelFilenode.setFileNodeLevel(1);
        modelFilenode.setJudgeFolder(true);
        modelFilenode.setParentId(0);
        addModelFileNode(modelFilenode);
    }

    /**
     * 删除一个文件节点
     * @param modelFileNode 传入的文件节点
     */
    public void deleteModelFileNode(ModelFileNode modelFileNode){
        List<ModelFileNode> children = modelFileNode.getChildren();
        for (ModelFileNode node: children) {
            deleteModelFileNode(node);
        }
        // 先删除对应的模板，再删除模板文件
        if (modelFileNodeDao.existsById(modelFileNode.getId())) {
            modelService.deleteByModelFileNode(modelFileNode);
        }
        modelFileNodeDao.deleteById(modelFileNode.getId());
    }
}
