package com.wangyu.kgbackend.service;

import com.wangyu.kgbackend.dao.SubGraphDao;
import com.wangyu.kgbackend.pojo.FileNode;
import com.wangyu.kgbackend.pojo.SubGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wongy
 */
@Service
public class SubGraphService {
    @Autowired
    SubGraphDao subGraphDao;

    /**
     * 根据文件删除子图
     * @param fileNode 文件
     */
    public void deleteByFileNode(FileNode fileNode){
        subGraphDao.deleteAllByFileNode(fileNode);
    }

    /**
     * 添加子图
     * @param subGraph 对应的子图
     */
    public void addSubGraph(SubGraph subGraph){
        subGraphDao.save(subGraph);
    }

    /**
     * 根据文件列出所有子图
     * @param fileNode 文件
     * @return 对应的子图列表
     */
    public List<SubGraph> listByFileNode(FileNode fileNode){
        return subGraphDao.findAllByFileNode(fileNode);
    }

    /**
     * 删除对应的子图
     * @param subGraph 对应的子图
     */
    public void deleteSubGraph(SubGraph subGraph){
        subGraphDao.deleteById(subGraph.getId());
    }

    /**
     * 根据名字删除
     * @param name 对应的名字
     */
    public void deleteByName(String name,FileNode fileNode){
        subGraphDao.deleteByNameAndFileNode(name,fileNode);
    }
}
