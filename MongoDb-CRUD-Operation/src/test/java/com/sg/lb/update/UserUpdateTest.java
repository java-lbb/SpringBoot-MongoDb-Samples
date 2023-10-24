package com.sg.lb.update;

import com.mongodb.client.result.UpdateResult;
import com.sg.lb.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@SpringBootTest
public class UserUpdateTest {

    @Autowired
    MongoTemplate mongoTemplate;

    public void update(){
        /*
        updateFirst：更新与查询文档条件匹配的第一个文档，不支持排序。 若支持排序可以使用 findAndModify 应用 sort 进行排序后更新第一条

        updateMulti：更新与查询文档条件匹配的所有对象。
        */
    }

    @Test
    public void simpleUpdate(){
        // query: { "first_name" : "小", "last_name" : "明"} and update: { "$set" : { "height" : 178.5}}
        Query query = query(where("firstname").is("小").and("lastname").is("明"));
        Update update = new Update().set("height", 178.5);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, User.class);
        long matchedCount = updateResult.getMatchedCount(); // 满足匹配的文档数
        System.out.println("matchedCount: " + matchedCount);
        long modifiedCount = updateResult.getModifiedCount(); // 修改成功的文档数
        System.out.println("modifiedCount: " + modifiedCount);
    }

    /**
     * 更新数组中满足条件的第一个元素值
     */
    @Test
    public void update_array_01(){
        /* 1. query: { "first_name" : "小", "last_name" : "明", "hobbies" : "篮球"}  and update: { "$set" : { "hobbies.$" : "足球"}}
         * 2.  $ 代表一个占位符，只会更新数组中满足条件的第一个元素，如果 hobbies : ["篮球","篮球","羽毛球"] ,那么只会把第一个 "篮球" 修改为 "足球"。
         * 3. 注意： query 语句中 必须要含有 update 语句中的数组字段 ！
         */
        Query query = query(where("firstname").is("小").and("lastname").is("明").and("hobbies").is("篮球"));
        Update update = new Update().set("hobbies.$","足球");
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, User.class);// 即 MongoDB 中的 db.collection.updateOne() 更新满足条件的第一条文档中的相应数据
        System.out.println("match: " + updateResult.getMatchedCount() + " " + "modified: " + updateResult.getModifiedCount());
    }

    /**
     * 更新文档数组中满足条件的第一个嵌套文档中的值
     */
    @Test
    public void update_array_02(){
        /*
        *  1. query: { "first_name" : "小", "last_name" : "明", "friends.first_name" : "小"} and update: { "$set" : { "friends.$.age" : 25}}
        *  2. $ 代表一个占位符，只会更新数组中满足条件的第一个元素，下面例子中即使小明有多个朋友的 firstname 为"小"，但只会更新第一条元素的 age 为25
        */

        Query query = query(where("firstname").is("小").and("lastname").is("明").and("friends.firstname").is("小"));
        Update update = new Update().set("friends.$.age",25); //
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, User.class);
        System.out.println("match: " + updateResult.getMatchedCount() + " " + "modified: " + updateResult.getModifiedCount());
    }

    /**
     * 更新数组中的所有元素值
     */
    @Test
    public void update_array_03(){
        // hobbies: ["篮球","足球","羽毛球"] -> hobbies:["篮球","篮球","篮球"]
        // $[] 语法一般用于数组中元素一起改变，比如 $inc 操作 -> 同时增加
        Query query = query(where("hobbies").is("篮球"));
        Update update = new Update().set("hobbies.$[]","足球");
        mongoTemplate.updateFirst(query, update, User.class);
    }

    /**
     * 更新数组中满足指定过滤条件的多个值
     *
     */
    @Test
    public void update_array_04(){
        // query: { "hobbies" : { "$in" : ["篮球", "足球"]}} and update: { "$set" : { "hobbies.$[elem]" : "橄榄球"} and arrayFilters：[{ elem : "足球"}]
        // hobbies : ["足球" ,"足球" ,"篮球"] -> hobbies : ["橄榄球", "橄榄球" , "篮球"]
        Query query = query(where("hobbies").in("篮球","足球"));
        Update update = new Update().set("hobbies.$[elem]","橄榄球").filterArray(where("elem").is("足球"));
        mongoTemplate.updateFirst(query, update, User.class);
    }

    /**
     * 更新嵌套文档数组中满足指定过滤条件的多个文档中的字段值
     */
    @Test
    public void update_array_05(){
        /* query: { "last_name" : "明"} and update: { "$set" : { "friends.$[elem].age" : 18} } and arrayFilters: [{ elem.height : { $gt : 175, $lt : 180} }]
        *  把 lastname 等于 "明" 的第一条文档中，朋友 friends 文档数组中满足身高 height 大于180，小于185 的所有文档元素的 age 修改为 18
        */
        Query query = query(where("lastname").is("明"));
        Update update = new Update().set("friends.$[elem].age",18).filterArray(where("elem.height").gt(180).lt(185));
        mongoTemplate.updateFirst(query, update, User.class);
    }

    /**
     * 更新嵌套文档数组中满足指定过滤条件的多个文档中相应数组的值
     */
    @Test
    public void update_array_06(){
        /**
         * db.user.updateOne(
         *     {
         *         last_name: "明"
         *     },
         *     {
         *         $set: {
         *             "friends.$[first].hobbies.$[second]": "游泳"
         *         }
         *     },
         *     {
         *         arrayFilters: [{
         *             "first.age": {
         *                 $gt: 20
         *             }
         *         }, {
         *             "second": {
         *                 $eq: "篮球"
         *             }
         *         }]
         *     }
         * )
         *
         * 把 friends 文档数组中满足 age 大于 18 的 并且 hobbies 中元素为 "篮球" 的都更新为 "游泳"
         */
        Query query = query(where("lastname").is("明"));
        Update update = new Update().set("friends.$[first].hobbies.$[second]", "游泳" ).filterArray(where("first.age").gt(18)).filterArray(where("second").is("篮球"));
        mongoTemplate.updateFirst(query, update, User.class);
    }

    /**
     * 向数组中添加字段值，如果该值不存在。<br/>
     *  <a href="https://www.mongodb.com/docs/v4.4/reference/operator/update/addToSet/#mongodb-update-up.-addToSet"> $addToSet</a>
     *
     */
    @Test
    public void addToSet(){
        // 往 hobbies 数组中添加值
        Query query = query(where("lastname").is("明"));
        Update update = new Update().addToSet("hobbies", "攀岩"); // 如果已经存在 "攀岩" 则不会添加进去
        // 注意：此方式只能添加单个值，如果是这种形式的话： new Update().addToSet("hobbies", Arrays.asList("攀岩","拳击")) 即 -> { "$addToSet" : { "hobbies" : ["攀岩","拳击"] }, 那么最终 hobbies 数组字段如下：hobbies: ["篮球, ["攀岩","拳击"]]
        mongoTemplate.updateFirst(query,update, User.class);
    }
}
