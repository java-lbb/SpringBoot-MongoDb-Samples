package com.sg.lb.query;

import com.sg.lb.entity.Address;
import com.sg.lb.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@SpringBootTest
public class UserQueryTest {

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 以 mongodb shell json 形式的原始查询语句
     */
    @Test
    public void baseQuery(){
        BasicQuery basicQuery = new BasicQuery("{age: { $lt : 18} }");
        List<User> result = mongoTemplate.find(basicQuery, User.class);
        result.forEach(System.out::println);
    }


    @Test
    public void find(){
        /*

        findOne：返回查询的结果的第一个文档。

        findAll：返回集合中指定类型的文档。

        findById：返回给定 id 和目标类的对象。

        find：将查询的结果映射到指定类型的列表并返回。

        findAndRemove： 返回与查询匹配的第一个文档，并将其从数据库的集合中删除。

        */
    }

    @Test
    public void query_01(){
        // 查询所有年龄大于15，并且升高在170,180之间的所有 user
        // { "age" : { "$gt" : 15}, "height" : { "$in" : [170.0, 180.0]}}
        List<User> users = mongoTemplate.query(User.class).matching(query(where("age").gt(15).and("height").in(170.0, 180.0))).all();
        users.forEach(System.out::println);
    }

    @Test
    public void query_02(){
        // { "name" : "小明", "height" : { "$gt" : 160}}
        /*  通过日志信息我们可以发现，如果 属性 firstname 上没有 @Field 注解或者有注解但是未指定字段值。此时会以 where 条件中 key 作为 MongoDB 中实际查询的字段
            如果 firstname 属性上 有 @Field 注解 并且 指定了 first_name 值，那么实际在 MongoDB 中查询时会以注解值字段 first_name 为准。
        */
        User one = mongoTemplate.findOne(query(where("firstname").is("小").and("height").gt(160)), User.class);
        // User one = mongoTemplate.findOne(query(where("first_name").is("小").and("height").gt(160)), User.class);
        System.out.println(one);
    }

    @Test
    public void query_03(){
        // { "age" : { "$lt" : 20}, "$and" : [{ "height" : { "$in" : [180.0, 190.0]}}, { "first_name" : "小明"}]}
        User one = mongoTemplate.findOne(
                query(
                        where("age").lt(20)
                        .andOperator(
                                where("height").in(180,190),
                                where("last_name").is("明")
                        )
                ), User.class);
        System.out.println(one);
    }

    @Test
    public void query_04(){
        // 1. { "age" : { "$lt" : 20}, "hobbies" : "篮球"}
        //年龄大于20且hobbies 中至少有一个元素等于篮球的第一条文档
        User one = mongoTemplate.findOne(
                query(
                        where("age").lt(20).and("hobbies").is("篮球")
                ), User.class);
        System.out.println(one);

        // 2. { "address.city" : "深圳市"} 查询嵌套文档 address 中 city 为深圳市的第一条文档
        User two = mongoTemplate.findOne(query(where("address.city").is("深圳市")), User.class);
        System.out.println(two);

        // 3. { "friends" : { "$elemMatch" : { "name" : "小明", "age" : { "$gt" : 18}, "hobbies" : { "$all" : ["篮球", "羽毛球"]}}}}
        // 查询 "在 friends 嵌套文档数组中 满足其中一条嵌套文档的 name 为小明，age 大于18，爱好必须同时满足篮球和羽毛球" 的所有文档
        List<User> three = mongoTemplate.find(query(where("friends").elemMatch(where("firstname").is("小明").and("age").gt(18).and("hobbies").all("篮球","羽毛球"))), User.class);
        System.out.println(three);

    }

    /**
     * 查询指定值并去重 findDistinct
     */
    @Test
    public void query_05(){
        // 1.查询所有去重lastname
        List<String> lastnames = mongoTemplate.query(User.class).distinct("lastname").as(String.class).all();
        System.out.println(lastnames);
        // 2.查询所有 address 去重
        List<Address> addressList = mongoTemplate.query(User.class).distinct("address").as(Address.class).all();
        System.out.println(addressList);
    }

    /**
     * 指定所需返回的字段
     */
    @Test
    public void selectField(){

        /*
         同理， 如果属性上没有 @Field 值 那么会以实际参数的 key 为准，有该注解则字段以相应 value 值 为准
        */

        // 1. include： 只返回 firstname 和 age 字段 以及默认返回的 id 字段
        Query query1 = query(where("age").lt(20));
        query1.fields().include("first_name").include("age");
        // query1.fields().include("firstname").include("age"); 同样可行,实际的字段是以注解值为准。
        System.out.println(mongoTemplate.findOne(query1, User.class));

        System.out.println("==============================");

        // 2. exclude: 排除 firstname 和 age 字段，返回其他所有字段
        Query query2 = query(where("age").lt(20));
        query2.fields().exclude("firstname").exclude("age");
        System.out.println(mongoTemplate.findOne(query2, User.class));

        System.out.println("==============================");

        // 3. exclude: 排除 id 字段， 并且只返回 firstname 字段 和 height 字段
        Query query3 = query(where("age").lt(20));
        query3.fields().exclude("id").include("firstname");
        System.out.println(mongoTemplate.findOne(query3, User.class));

        System.out.println("==============================");

        // 4. include: 只返回 firstname 字段和在嵌入式文档 address 中 只返回 city 字段，以及默认返回的 id 字段
        Query query4 = query(where("age").lt(20));
        query4.fields().include("firstname").include("address.city");
        System.out.println(mongoTemplate.findOne(query4, User.class));
    }


}
