package com.wangyu.kgbackend.dao;

import com.wangyu.kgbackend.pojo.FileNode;
import com.wangyu.kgbackend.pojo.SubGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;


/**
 * @author wongy
 */
public interface SubGraphDao extends JpaRepository<SubGraph,Integer> {
    /**
     * 根据文件删除子图
     * @param fileNode 图谱文件
     */
    @Transactional
    void deleteAllByFileNode(FileNode fileNode);

    /**
     * 根据文件查询所有对应的子图
     * @param fileNode 图谱文件
     * @return 对应的子图列表
     */
    List<SubGraph> findAllByFileNode(FileNode fileNode);

    /**
     * 根据名字删除
     * @param name 对应的名字
     */
    @Transactional
    void deleteByNameAndFileNode(String name,FileNode fileNode);
}
