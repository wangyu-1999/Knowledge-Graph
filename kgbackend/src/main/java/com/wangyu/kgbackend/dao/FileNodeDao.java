package com.wangyu.kgbackend.dao;

import com.wangyu.kgbackend.pojo.FileNode;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author wongy
 */
public interface FileNodeDao extends JpaRepository<FileNode, Integer> {
}
