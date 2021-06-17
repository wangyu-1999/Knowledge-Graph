package com.wangyu.kgbackend.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wongy
 * 模板文件
 */
@Entity
@Table(name = "t_model_files")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class ModelFileNode {
    /**
     * 文件的唯一标识，原则上不允许前端传入设置
     * 由数据库自增来设置
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int id;
    /**
     * 文件名称
     */
    @Column(name = "file_name",nullable = false)
    private String label;
    /**
     * 父节点的id，根节点的父节点为0
     */
    @Column(name = "parent_id")
    private int parentId;
    /**
     * 是否是文件夹
     * 根据这个字段判断是文件夹还是文件
     */
    @Column(name = "is_folder",nullable = false)
    private Boolean judgeFolder;
    /**
     * 文件所在的层数
     * 根节点默认为1层
     */
    @Column(name = "file_level",nullable = false)
    private int fileNodeLevel;
    /**
     * 节点的子节点，不需要持久化
     * 加载完成根据上面的信息还原出结构
     */
    @Transient
    private List<ModelFileNode> children = new ArrayList<ModelFileNode>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<ModelFileNode> getChildren() {
        return children;
    }

    public void setChildren(List<ModelFileNode> children) {
        this.children = children;
    }

    public int getFileNodeLevel() {
        return fileNodeLevel;
    }

    public void setFileNodeLevel(int fileNodeLevel) {
        this.fileNodeLevel = fileNodeLevel;
    }

    public Boolean getJudgeFolder() {
        return judgeFolder;
    }

    public void setJudgeFolder(Boolean judgeFolder) {
        this.judgeFolder = judgeFolder;
    }
}
