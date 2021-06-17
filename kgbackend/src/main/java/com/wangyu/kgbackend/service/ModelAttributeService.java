package com.wangyu.kgbackend.service;

import com.wangyu.kgbackend.dao.ModelAttributeDao;
import com.wangyu.kgbackend.pojo.Model;
import com.wangyu.kgbackend.pojo.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wongy
 */
@Service
public class ModelAttributeService {
    @Autowired
    ModelAttributeDao modelAttributeDao;

    /**
     * 删除所有的属性
     */
    public void deleteAll(){
        modelAttributeDao.deleteAllInBatch();
    }

    /**
     * 根据模型删除属性
     */
    public void deleteByModel(Model model){
        modelAttributeDao.deleteAllByModel(model);
    }

    /**
     * 向属性表中添加属性
     */
    public void addAttributes(List<ModelAttribute> modelAttributeList){
        for(ModelAttribute modelAttribute:modelAttributeList){
            modelAttributeDao.save(modelAttribute);
        }
    }

    /**
     * 根据模型列出对应属性
     * @param model 模型
     * @return 对应的属性列表
     */
    public List<ModelAttribute>listByModel(Model model){
        return modelAttributeDao.findAllByModel(model);
    }
}
