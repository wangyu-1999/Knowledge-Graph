package com.wangyu.kgbackend.controller;

import com.alibaba.fastjson.JSONObject;

import com.wangyu.kgbackend.result.Result;
import com.wangyu.kgbackend.result.ResultFactory;
import com.wangyu.kgbackend.util.Neo4jUtil;

import com.wangyu.kgbackend.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author wongy
 * Noe4j的相关Controller类
 */
@RestController
public class Neo4jController {
    @Autowired
    private Neo4jUtil neo4jUtil;

    /**
     * 添加节点
     * @param jsonObject 对应的节点格式json
     * @return 包含点集和边集的结果
     */
    @PostMapping("/api/neo4j/add_node")
    public Result addNode(@RequestBody JSONObject jsonObject) {
        if (jsonObject.containsKey("neo4j_file_id")) {
            String cql = "create (:File" +
                    jsonObject.get("neo4j_file_id").toString() +
                    StringUtils.uniformJsonString(jsonObject.toJSONString()) + ")";
            Neo4jUtil.add(cql);
            return ResultFactory.buildSuccessResult(
                    Neo4jUtil.selectByFile(
                            jsonObject.get("neo4j_file_id").toString()));
        }
        return ResultFactory.buildFailResult("参数有误");
    }

    /**
     * 添加关系
     * @param jsonObject 对应的关系格式json
     * @return 包含点集和边集的结果
     */
    @PostMapping("/api/neo4j/add_relation")
    public Result addRelation(@RequestBody JSONObject jsonObject) {
        if (jsonObject.containsKey("from") &&
                jsonObject.containsKey("to") &&
                jsonObject.containsKey("neo4j_file_id")) {
            String cql = "match (m),(n) where id(m)=" +
                    jsonObject.get("from").toString() +
                    " and id(n)=" + jsonObject.get("to").toString() +
                    " create (m)-[:File" +
                    jsonObject.get("neo4j_file_id").toString() +
                    StringUtils.uniformJsonString(jsonObject.toJSONString()) +
                    "]->(n)";
            Neo4jUtil.add(cql);
            return ResultFactory.buildSuccessResult(
                    Neo4jUtil.selectByFile(
                            jsonObject.get("neo4j_file_id").toString()));
        }
        return ResultFactory.buildFailResult("参数有误");
    }

    /**
     * 删除一个节点
     * @param jsonObject 对应的节点格式json
     * @return 处理的结果
     */
    @PostMapping("/api/neo4j/delete_node")
    public Result deleteNode(@RequestBody JSONObject jsonObject) {
        if (jsonObject.containsKey("id")) {
            Neo4jUtil.deleteNodeById(jsonObject.get("id").toString());
            return ResultFactory.buildSuccessResult("success");
        }
        return ResultFactory.buildFailResult("参数错误");
    }

    /**
     * 删除一段关系
     * @param jsonObject 对应的关系格式json
     * @return 处理的结果
     */
    @PostMapping("/api/neo4j/delete_relation")
    public Result deleteRelation(@RequestBody JSONObject jsonObject) {
        if (jsonObject.containsKey("id")) {
            Neo4jUtil.deleteRelaById(jsonObject.get("id").toString());
            return ResultFactory.buildSuccessResult("success");
        }
        return ResultFactory.buildFailResult("参数错误");
    }

    /**
     * 设置节点的属性
     * @param jsonObject 对应的json
     * @return 处理的节点id
     */
    @PostMapping("/api/neo4j/set_node")
    public static Result setNode(@RequestBody JSONObject jsonObject) {
        if (jsonObject.containsKey("id")) {
            Set<Map.Entry<String, Object>> entrySet = jsonObject.entrySet();
            List<String> strs = new ArrayList<>();
            for (Map.Entry entry : entrySet) {
                if (entry.getKey() != "id") {
                    String str = "n." + entry.getKey() + "=" +
                            (StringUtils.isNumeric(entry.getValue().toString()) ?
                                    entry.getValue().toString() : "\"" + entry.getValue().toString() + "\"");
                    strs.add(str);
                }
            }
            String cql = "match(n) where id(n)=" + jsonObject.get("id") + " set ";
            if (strs.size() < 1) {
                return ResultFactory.buildFailResult("参数个数错误");
            } else if (strs.size() == 1) {
                cql = cql + strs.get(0);
            } else {
                cql = cql + strs.get(0);
                for (int i = 1; i < strs.size(); i++) {
                    cql = cql + "," + strs.get(i);
                }
            }
            Neo4jUtil.add(cql);
            return ResultFactory.buildSuccessResult(jsonObject.get("id"));
        }
        return ResultFactory.buildFailResult("参数错误");
    }

    /**
     * 设置关系的属性
     * @param jsonObject 对应的json
     * @return 处理的关系id
     */
    @PostMapping("/api/neo4j/set_relation")
    public static Result setRelation(@RequestBody JSONObject jsonObject) {
        if (jsonObject.containsKey("id")) {
            Set<Map.Entry<String, Object>> entrySet = jsonObject.entrySet();
            List<String> strs = new ArrayList<>();
            for (Map.Entry entry : entrySet) {
                if (entry.getKey() != "id") {
                    String str = "n." + entry.getKey() + "=" +
                            (StringUtils.isNumeric(entry.getValue().toString()) ?
                                    entry.getValue().toString() : "\"" + entry.getValue().toString() + "\"");
                    strs.add(str);
                }
            }
            String cql = "match()-[n]-() where id(n)=" + jsonObject.get("id") + " set ";
            if (strs.size() < 1) {
                return ResultFactory.buildFailResult("参数个数错误");
            } else if (strs.size() == 1) {
                cql = cql + strs.get(0);
            } else {
                cql = cql + strs.get(0);
                for (int i = 1; i < strs.size(); i++) {
                    cql = cql + "," + strs.get(i);
                }
            }
            Neo4jUtil.add(cql);
            return ResultFactory.buildSuccessResult(cql);
        }
        return ResultFactory.buildFailResult("参数错误");
    }

    /**
     * 列出所有的节点和关系
     * @param jsonObject 对应的格式json
     * @return 包含节点集合和关系集合的结果
     */
    @PostMapping("/api/neo4j/list")
    public static Result listAll(@RequestBody JSONObject jsonObject) {
        if (jsonObject.containsKey("neo4j_file_id")) {
            return ResultFactory.buildSuccessResult(Neo4jUtil.selectByFile(jsonObject.get("neo4j_file_id").toString()));
        }
        return ResultFactory.buildFailResult("参数错误");
    }

    /**
     * 子图查询的相关类
     * @param jsonObject 子图查询语句对应json
     * @return 向前端返回边集合和点集合
     */
    @PostMapping("/api/neo4j/find_subgraph")
    public static Result findSubgraph(@RequestBody JSONObject jsonObject){
        return ResultFactory.buildSuccessResult(Neo4jUtil.findSubgraph(jsonObject.get("id").toString(),
                jsonObject.get("name").toString(),jsonObject.get("jumpNum").toString()));
    }

    /**
     * 子图存储的相关类
     * @param jsonObject 子图存储语句对应json
     * @return 向前端返回边集合和点集合
     */
    @PostMapping("/api/neo4j/save_subgraph")
    public static Result saveSubgraph(@RequestBody JSONObject jsonObject){
        return ResultFactory.buildSuccessResult(Neo4jUtil.saveSubgraph(jsonObject.get("id").toString(),
                jsonObject.get("name").toString(),jsonObject.get("jumpNum").toString(),
                jsonObject.get("subgraphName").toString()));
    }
}
