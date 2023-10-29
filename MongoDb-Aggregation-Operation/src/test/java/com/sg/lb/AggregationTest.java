package com.sg.lb;

import org.bson.BasicBSONObject;
import org.bson.BsonDocument;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * @author LiuBing
 * @date: 2023/10/27 13:03
 * @description: 聚合测试
 */
@SpringBootTest
public class AggregationTest {

    @Autowired
    MongoTemplate mongoTemplate;


    /**
     * $match 聚合阶段。过滤条件
     */
    @Test
    public void matchStage(){
        MatchOperation match = match(Criteria.where("firstname").is("小").and("lastname").is("明"));
        Aggregation aggregation = newAggregation(match);
        List<User> results = mongoTemplate.aggregate(aggregation, User.class, User.class).getMappedResults();
        results.forEach(System.out::println);
    }

    /**
     *  $project 投影阶段
     */
    @Test
    public void projectStage(){
        /*
        * 1. project("name", "netPrice")  -> {$project: {name: 1, netPrice: 1}}
        * 2. project().and("thing1").as("thing2")  ->  {$project: {thing1: $thing2}}
        * 3. project("a","b").and("thing1").as("thing2")   ->   {$project: {a: 1, b: 1, thing2: $thing1}}
        * 4. project().andInclude("a","b").andExclude("_id")  ->    {$project: {a: 1, b: 1, _id : -1}
        * 4. project().andExclude("a","b")  ->   {$project: {a: -1, b: -1}
        */

        // 如果是在 mongoTemplate.aggregate() 中手动指定 "collectionName" 时， project 这里必须要传 mongodb 中实际存储的字段名，而不是 User 类中的属性名，不然会找不到字段映射关系。
        ProjectionOperation projectionOperation = project("age","first_name").andInclude("height","address");
        Aggregation aggregation = newAggregation(projectionOperation);
        // 聚合函数这里最好手动指定 "collectionName",如果通过 User.class 来获取注解上的集合名时，一些字段值映射关系会有bug.
        List<User> results = mongoTemplate.aggregate(aggregation, "user", User.class).getMappedResults();
        results.forEach(System.out::println);
    }

    /**
     * $bucket 分桶聚合操作
     */
    @Test
    public void bucketsTest(){
        /*
         * 1. bucket("height").withBoundaries(160,175,190) -> {$bucket: {groupBy: $height, boundaries: [160, 175, 190]}}
         * 2. bucket("age").withBoundaries(18,25).withDefaultBucket("other")  -> {$bucket: {groupBy: $age,  boundaries: [18, 25], default: "other"}}
         * 3. bucket("age").withBoundaries(16,20,28).andOutputCount().as("count") ->  {$bucket: {groupBy: $age,  boundaries: [16,20,25], output: { count: { $sum: 1} } }}
         *
         */
        BucketOperation bucketOperation =
                bucket("height").withBoundaries(160, 175, 190).andOutput("friends").push().as("friends");
        Aggregation aggregation = newAggregation(bucketOperation);
        List<User> results = mongoTemplate.aggregate(aggregation, "user", User.class).getMappedResults();
        results.forEach(System.out::println);
    }
    @Test
    public void groupTest(){
        GroupOperation group1 = group("first_name", "last_name").sum("age").as("ages");
        SortOperation sort = sort(Sort.Direction.ASC, "ages", "first_name", "last_name");
        Aggregation aggregation = newAggregation(group1,sort);
        List<User> results = mongoTemplate.aggregate(aggregation, "user", User.class).getMappedResults();
        results.forEach(System.out::println);
    }

}
