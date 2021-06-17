package com.wangyu.kgbackend.util;

import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 通用的neo4j调用类
 */
@Component
public class Neo4jUtil {
    private static Driver driver;

    @Autowired
    public Neo4jUtil(Driver driver) {
        Neo4jUtil.driver = driver;
    }

    /**
     * 执行添加cql
     *
     * @param cql 查询语句
     */
    public static void add(String cql) {
        //启动事务
        try (Session session = driver.session();
             Transaction tx = session.beginTransaction()) {
            tx.run(cql);
            //提交事务
            tx.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据文件id查询所有节点
     *
     * @param id 文件的id
     * @return 包含节点的List
     */
    public static List<Map> selectNodeByFile(String id) {
        String cql = "MATCH (node:File" + id + ") RETURN node";
        Session session = driver.session();
        StatementResult result = session.run(cql);
        session.close();
        List<Record> list = result.list();
        List<Map> resList = new ArrayList<>();
        for (Record r : list) {
            Map resMap = new HashMap();
            resMap.putAll(r.get("node").asMap());
            resMap.put("id", r.get("node").hashCode());
            resList.add(resMap);
        }
        return resList;
    }

    /**
     * 根据文件id查询所有边
     *
     * @param id 文件的id
     * @return 包含边的List
     */
    public static List<Map> selectRelaByFile(String id) {
        String cql = "MATCH path=()-[:File" + id + "]->() RETURN path";
        Session session = driver.session();
        StatementResult result = session.run(cql);
        session.close();
        List<Record> list = result.list();
        List<Map> resList = new ArrayList<>();
        for (Record r : list) {
            Iterable<Relationship> relationships = r.get("path").asPath().relationships();
            for (Relationship relationship : relationships) {
                Map resMap = new HashMap();
                resMap.putAll(relationship.asMap());
                resMap.put("id", relationship.hashCode());
                resList.add(resMap);
            }
        }
        return resList;
    }

    /**
     * 根据文件id查询节点和边
     *
     * @param id 文件的id
     * @return 包含节点和边的map
     */
    public static Map selectByFile(String id) {
        Map resMap = new HashMap();
        resMap.put("nodes", selectNodeByFile(id));
        resMap.put("links", selectRelaByFile(id));
        return resMap;
    }

    /**
     * 根据id删除关系
     *
     * @param id 关系的id
     */
    public static void deleteRelaById(String id) {
        String cql = "Match ()-[r]-() Where ID(r)=" + id + " Delete r";
        add(cql);
    }

    /**
     * 根据id删除节点
     *
     * @param id 节点的id
     */
    public static void deleteNodeById(String id) {
        String cql = "match (n) where id(n)=" + id + " detach delete n";
        add(cql);
    }

    /**
     * 根据文件id删除所有内容
     *
     * @param id 文件的id
     */
    public static void deleteByFileId(String id) {
        String cql = "match (n:File" + id + ") " + "detach delete n";
        add(cql);
    }

    /**
     * 根据子图的条件查找子图
     *
     * @param id      对应知识图谱的id
     * @param name    实体的名称
     * @param jumpNum 对应的跳数
     * @return 对应的映射结果对
     */
    public static Map findSubgraph(String id, String name, String jumpNum) {
        String cql = "match data = (na:File" + id + "{name:" + "\"" + name + "\"" +
                "})-[rel*1.." + jumpNum + "]->(nb) return na,rel,nb";
        String cql2 = "match data = (n:File" + id + ") where n.name=" +  "\"" + name + "\"" + " return n";
        Session session = driver.session();
        Session session2 = driver.session();
        StatementResult result = session.run(cql);
        StatementResult result2 = session2.run(cql2);
        session.close();
        session2.close();
        List<Record> list = result.list();
        List<Record> list2 = result2.list();
        List<Map> nodeList = new ArrayList<>();
        List<Map> relaList = new ArrayList<>();
        for (Record r : list2){
            Map resMap = new HashMap();
            resMap.putAll(r.get("n").asMap());
            resMap.put("id", r.get("n").hashCode());
            nodeList.add(resMap);
        }
        for (Record r : list) {
            {
                Map resMap = new HashMap();
                resMap.putAll(r.get("nb").asMap());
                resMap.put("id", r.get("nb").hashCode());
                nodeList.add(resMap);
            }
            List relations = r.get("rel").asList();
            Relationship relationship = (Relationship)relations.get(relations.size() - 1);
            Map resMap = new HashMap();
            resMap.putAll(relationship.asMap());
            resMap.put("id", relationship.hashCode());
            relaList.add(resMap);
        }
        Map resMap = new HashMap();
        resMap.put("nodes", nodeList);
        resMap.put("links", relaList);
        return resMap;
    }

    /**
     * 根据条件存储子图
     * @param id 文件id
     * @param name 节点的名称
     * @param jumpNum 跳数
     * @param subgraphName 子图名称
     */
    public static Map saveSubgraph(String id, String name, String jumpNum,String subgraphName) {
        String cql1 =  "match data = (n:File" + id + ") where n.name=" +  "\"" + name + "\"" + " set n:" + subgraphName;
        String cql2 = "match data = (na:File" + id + "{name:" + "\"" + name + "\"" +
                "})-[rel*1.." + jumpNum + "]->(nb) set nb:" + subgraphName;
        Session session1 = driver.session();
        session1.run(cql1);
        Session session2 = driver.session();
        session2.run(cql2);
        session1.close();
        session2.close();
        return takeSubgraph(id, subgraphName);
    }

    /**
     * 取出存储的子图
     * @param id 文件的id
     * @param subgraphName 子图的名称
     * @return 对应的边集合和点集合
     */
    public static Map takeSubgraph(String id, String subgraphName){
        String cql1 = "match (n:File" + id + ":" + subgraphName + ") return n";
        String cql2 = "match (na:File" + id + ":" + subgraphName + ")-[rel*1]->(nb:File" + id +
                ":" + subgraphName + ") return rel";
        Session session1 = driver.session();
        StatementResult result1 = session1.run(cql1);
        Session session2 = driver.session();
        StatementResult result2 = session2.run(cql2);
        session1.close();
        session2.close();
        List<Record> list1 = result1.list();
        List<Record> list2 = result2.list();
        List<Map> nodeList = new ArrayList<>();
        List<Map> relaList = new ArrayList<>();
        for (Record r: list1){
            Map resMap = new HashMap();
            resMap.putAll(r.get("n").asMap());
            resMap.put("id", r.get("n").hashCode());
            nodeList.add(resMap);
        }
        for(Record r:list2){
            List relations = r.get("rel").asList();
            Relationship relationship = (Relationship)relations.get(0);
            Map resMap = new HashMap();
            resMap.putAll(relationship.asMap());
            resMap.put("id", relationship.hashCode());
            relaList.add(resMap);
        }
        Map resMap = new HashMap();
        resMap.put("nodes", nodeList);
        resMap.put("links", relaList);
        return resMap;
    }

    /**
     * 删除对应的子图，在noe4j中删除对应的标签
     * @param id 对应的文件id
     * @param subgraphName 子图文件名
     */
    public static void removeSubgraph(String id, String subgraphName){
        String cql = "match (n:File" + id + ":" + subgraphName + ") remove n:" + subgraphName;
        Session session = driver.session();
        session.run(cql);
        session.close();
    }

}