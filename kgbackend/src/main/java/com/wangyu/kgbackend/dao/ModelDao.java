package com.wangyu.kgbackend.dao;

import com.wangyu.kgbackend.pojo.Model;
import com.wangyu.kgbackend.pojo.ModelFileNode;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author wongy
 */
public interface ModelDao extends JpaRepository<Model,Integer> {
    /**
     * 根据模型文件和是否是节点查询
     * @param modelFileNode 模型文件
     * @param judgeNode 是否是节点（或者是关系）
     * @return 对应的列表
     */
    List<Model> findAllByModelFileNodeAndJudgeNode(ModelFileNode modelFileNode, Boolean judgeNode);

    /**
     * 根据模型文件删除所有对应的模型
     * @param modelFileNode 模型文件（主要要那个id）
     */
    @Transactional
    void deleteAllByModelFileNode(ModelFileNode modelFileNode);

    /**
     * 根据模型文件查找所有对应的模型
     * @param modelFileNode 模型文件
     * @return 查找的结果
     */
    List<Model> findAllByModelFileNode(ModelFileNode modelFileNode);

    /**
     * 根据id删除对应的model
     * @param id 对应的id
     * @return 对应的model
     */
    @Transactional
    Model deleteById(int id);
}
