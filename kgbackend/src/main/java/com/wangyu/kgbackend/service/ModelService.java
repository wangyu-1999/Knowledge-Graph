package com.wangyu.kgbackend.service;

import com.wangyu.kgbackend.dao.ModelAttributeDao;
import com.wangyu.kgbackend.dao.ModelDao;
import com.wangyu.kgbackend.pojo.Model;
import com.wangyu.kgbackend.pojo.ModelFileNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wongy
 */
@Service
public class ModelService {
    @Autowired
    ModelDao modelDao;
    @Autowired
    ModelAttributeService modelAttributeService;

    /**
     * 添加一个模型
     * @param model 对应的模型
     */
    public void addModel(Model model){
        modelDao.save(model);
    }

    /**
     * 根据模型文件和对应类型查找模型，返回列表
     * @param model 包含参数的模型
     * @return 对应的模型列表
     */
    public List<Model> listByModelFileNodeAndJudgeNode(Model model) {
        return modelDao.findAllByModelFileNodeAndJudgeNode(model.getModelFileNode(), model.getJudgeNode());
    }

    /**
     * 删除所有的模板
     */
    public void deleteAll(){
        modelAttributeService.deleteAll();
        modelDao.deleteAllInBatch();
    }

    /**
     * 根据模板文件删除对应模板
     * @param modelFileNode 对应的模板文件
     */
    public void deleteByModelFileNode(ModelFileNode modelFileNode){
        List<Model> modelList = modelDao.findAllByModelFileNode(modelFileNode);
        for(Model model:modelList){
            modelAttributeService.deleteByModel(model);
        }
        modelDao.deleteAllByModelFileNode(modelFileNode);
    }

    /**
     * 修改模型的属性为true
     * @param model 对应的模型
     * @return 对应的模型属性
     */
    public boolean setAttribute(Model model){
        Model changedModel = modelDao.findById(model.getId()).get();
        if (changedModel != null) {
            changedModel.setJudgeAttribute(true);
            modelDao.save(changedModel);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 修改图片地址的url
     * @param model 对应的模型
     * @return 修改图片地址后的模型
     */
    public Model changeUrl(Model model){
        Model oldModel = modelDao.findById(model.getId()).get();
        oldModel.setImg(model.getImg());
        modelDao.save(oldModel);
        return oldModel;
    }

    /**
     * 删除对应的模型
     * @param model 对应的模型
     * @return 删除的模型
     */
    public Model deleteModel(Model model){
        modelAttributeService.deleteByModel(model);
        modelDao.deleteById(model.getId());
        return model;
    }
}
