package com.wangyu.kgbackend.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * @author wongy
 * 模板属性
 */
@Entity
@Table(name = "t_model_attribute")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class ModelAttribute {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    int id;
    /**
     * 属性名称
     */
    @Column(name = "attribute", nullable = false)
    String key;
    /**
     * 对应的图元模板
     */
    @ManyToOne
    @JoinColumn(name = "cid")
    Model model;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
