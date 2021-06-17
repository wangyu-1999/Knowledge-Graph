package com.wangyu.kgbackend.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * @author wongy
 * 图元模板
 */
@Entity
@Table(name = "t_model")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Model {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id" ,nullable = false)
    int id;

    /**
     * 模板的名称
     */
    @Column(name="model_name" ,nullable = false)
    String name;
    /**
     * 是否是文件夹
     */
    @Column(name="is_node" ,nullable = false)
    Boolean judgeNode;
    /**
     * 是否包含属性
     */
    @Column(name="has_attribute", nullable = false)
    Boolean judgeAttribute;
    /**
     * 图片地址
     */
    @Column(name="img_url")
    String img;
    /**
     * 颜色，本来想用在关系的线条颜色上
     * 感觉没有必要就没有写
     */
    @Column(name = "line_color")
    String color;
    /**
     * 所对应的模板文件id
     */
    @ManyToOne
    @JoinColumn(name = "cid", nullable = false)
    ModelFileNode modelFileNode;

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

    public Boolean getJudgeNode() {
        return judgeNode;
    }

    public void setJudgeNode(Boolean judgeNode) {
        this.judgeNode = judgeNode;
    }

    public Boolean getJudgeAttribute() {
        return judgeAttribute;
    }

    public void setJudgeAttribute(Boolean judgeAttribute) {
        this.judgeAttribute = judgeAttribute;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ModelFileNode getModelFileNode() {
        return modelFileNode;
    }

    public void setModelFileNode(ModelFileNode modelFileNode) {
        this.modelFileNode = modelFileNode;
    }
}
