package com.wangyu.kgbackend.controller;

import com.wangyu.kgbackend.pojo.ModelFileNode;
import com.wangyu.kgbackend.result.Result;
import com.wangyu.kgbackend.result.ResultFactory;
import com.wangyu.kgbackend.service.ModelFileNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wongy
 * 管理模板文件目录的类
 */
@RestController
public class ModelFileNodeController {

    @Autowired
    ModelFileNodeService modelFileNodeService;

    /**
     * 添加一个文件节点，如果文件节点存在了，那么更新这个节点
     * 这里文件节点由id唯一标识，原则上前端不允许传id过来
     * @param modelFileNode 文件节点
     * @return 包含添加的文件节点结果类
     */
    @PostMapping("/api/model_list/add_file")
    public Result addOfUpdate(@RequestBody ModelFileNode modelFileNode){
        modelFileNodeService.addModelFileNode(modelFileNode);
        return ResultFactory.buildSuccessResult(modelFileNode);
    }

    /**
     * 这里返回构造好的文件节点根目录，文件在其中按照层级放置
     * @return 包含文件根节点的结果类
     */
    @GetMapping("/api/model_list/list_all")
    public Result listAll(){
        ModelFileNode root = modelFileNodeService.listModelFileList();
        if(root == null){
            modelFileNodeService.initializeModelFileList();
            root = modelFileNodeService.listModelFileList();
        }
        return  ResultFactory.buildSuccessResult(root);
    }

    /**
     * 初始化目录节点，删除已经有的目录，然后添加一个根目录返回
     * @return 新的根目录节点
     */
    @GetMapping("/api/model_list/init_list")
    public Result initModelFileList(){
        modelFileNodeService.initializeModelFileList();
        ModelFileNode root = modelFileNodeService.listModelFileList();
        return  ResultFactory.buildSuccessResult(root);
    }

    /**
     * 删除一个文件节点
     * @param modelFileNode 删除的文件节点
     * @return  Result类，保存着被删除的节点
     */
    @PostMapping("/api/model_list/delete_file")
    public Result deleteModelFile(@RequestBody ModelFileNode modelFileNode){
        modelFileNodeService.deleteModelFileNode(modelFileNode);
        return ResultFactory.buildSuccessResult(modelFileNode);
    }
}
