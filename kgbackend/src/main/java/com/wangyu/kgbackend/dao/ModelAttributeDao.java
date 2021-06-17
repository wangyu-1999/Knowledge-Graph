package com.wangyu.kgbackend.dao;

import com.wangyu.kgbackend.pojo.Model;
import com.wangyu.kgbackend.pojo.ModelAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author wongy
 */
public interface ModelAttributeDao extends JpaRepository<ModelAttribute,Integer> {
    /**
     * 删除模型所有的属性
     * @param model 模型
     */
    @Transactional
    void deleteAllByModel(Model model);

    /**
     * 根据模型查询所有的属性
     * @param model 模型
     * @return 对应的属性列表
     */
    List<ModelAttribute> findAllByModel(Model model);
}
