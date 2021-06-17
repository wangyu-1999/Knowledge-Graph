package com.wangyu.kgbackend.controller;

import com.wangyu.kgbackend.pojo.FileNode;
import com.wangyu.kgbackend.pojo.SubGraph;
import com.wangyu.kgbackend.result.Result;
import com.wangyu.kgbackend.result.ResultFactory;
import com.wangyu.kgbackend.service.SubGraphService;
import com.wangyu.kgbackend.util.Neo4jUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author wongy
 */
@RestController
public class SubGraphController {
    @Autowired
    SubGraphService subGraphService;
    @Autowired
    Neo4jUtil neo4jUtil;

    /**
     * 根据文件加载子图目录
     * @param fileNode 对应的文件
     * @return 包含子图目录的结果
     */
    @PostMapping("/api/subgraph/list_all")
    public Result listByFile(@RequestBody FileNode fileNode){
        return ResultFactory.buildSuccessResult(subGraphService.listByFileNode(fileNode));
    }

    /**
     * 添加子图
     * @param subGraph 对应的子图
     * @return 对应的子图
     */
    @PostMapping("/api/subgraph/add")
    public Result addSubGraph(@RequestBody SubGraph subGraph){
        subGraphService.addSubGraph(subGraph);
        return ResultFactory.buildSuccessResult(subGraph);
    }

    /**
     * 根据名称删除对应子图
     * @param subGraph 对应子图
     * @return 对应子图
     */
    @PostMapping("/api/subgraph/delete")
    public Result deleteSubGraph(@RequestBody SubGraph subGraph){
        subGraphService.deleteByName(subGraph.getName(),subGraph.getFileNode());
        String id = subGraph.getFileNode().getId() + "";
        Neo4jUtil.removeSubgraph(id,subGraph.getName());
        return ResultFactory.buildSuccessResult(subGraph);
    }

    /**
     * 选中对应的子图
     * @param subGraph 对应子图
     * @return 包含点集和边集的结果
     */
    @PostMapping("/api/subgraph/select")
    public Result selectSubGraph(@RequestBody SubGraph subGraph){
        String id = subGraph.getFileNode().getId() + "";
        Map result = Neo4jUtil.takeSubgraph(id,subGraph.getName());
        return ResultFactory.buildSuccessResult(result);
    }
}
