package com.wangyu.kgbackend.controller;

import com.wangyu.kgbackend.pojo.FileNode;
import com.wangyu.kgbackend.result.Result;
import com.wangyu.kgbackend.result.ResultFactory;
import com.wangyu.kgbackend.service.FileNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wongy
 * 管理文件目录的类
 */
@RestController
public class FileNodeController {

    @Autowired
    FileNodeService fileNodeService;

    /**
     * 添加一个文件节点，如果文件节点存在了，那么更新这个节点
     * 这里文件节点由id唯一标识，原则上前端不允许传id过来
     * @param fileNode 文件节点
     * @return 包含添加的文件节点结果类
     */
    @PostMapping("/api/file_list/add_file")
    public Result addOfUpdate(@RequestBody FileNode fileNode){
        fileNodeService.addFileNode(fileNode);
        return ResultFactory.buildSuccessResult(fileNode);
    }

    /**
     * 这里返回构造好的文件节点根目录，文件在其中按照层级放置
     * @return 包含文件根节点的结果类
     */
    @GetMapping("/api/file_list/list_all")
    public Result listAll(){
        FileNode root = fileNodeService.listFileList();
        if(root == null) {
            fileNodeService.initializeFileList();
            root = fileNodeService.listFileList();
        }
        return  ResultFactory.buildSuccessResult(root);
    }

    /**
     * 初始化目录节点，删除已经有的目录，然后添加一个根目录返回
     * @return 新的根目录节点
     */
    @GetMapping("/api/file_list/init_list")
    public Result initFileList(){
        fileNodeService.initializeFileList();
        FileNode root = fileNodeService.listFileList();
        return  ResultFactory.buildSuccessResult(root);
    }

    /**
     * 删除一个文件节点
     * @param fileNode 删除的文件节点
     * @return  Result类，保存着被删除的节点
     */
    @PostMapping("/api/file_list/delete_file")
    public Result deleteFile(@RequestBody FileNode fileNode){
        fileNodeService.deleteFileNode(fileNode);
        return ResultFactory.buildSuccessResult(fileNode);
    }
}
