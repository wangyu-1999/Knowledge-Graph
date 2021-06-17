package com.wangyu.kgbackend.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * @author wongy
 * 子图
 */
@Entity
@Table(name = "t_subgraph")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class SubGraph {
    /**
     * 自增编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id" ,nullable = false)
    int id;

    /**
     * 子图的名称
     */
    @Column(name="subgraph_name" ,nullable = false)
    String name;

    /**
     * 子图所属的文件
     */
    @ManyToOne
    @JoinColumn(name = "cid",nullable = false)
    FileNode fileNode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileNode getFileNode() {
        return fileNode;
    }

    public void setFileNode(FileNode fileNode) {
        this.fileNode = fileNode;
    }
}
