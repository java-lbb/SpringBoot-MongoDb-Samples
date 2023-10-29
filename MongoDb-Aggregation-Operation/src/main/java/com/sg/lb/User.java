package com.sg.lb;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;

/**
 * @author LiuBing
 * @date: 2023/10/23 15:35
 * @description: user
 */
@Data
@Document(collection = "user") // 如果没有该注解，则插入或者保存文档时默认创建的集合名为类名小写开头，即 "user" 。如果指定了 @Document， 则以 value/collection 值为准
public class User {

    /**
     * 1.关于有无 @MongoId 的问题<br/>
     * ① 如果没有 @MongoId ，则属性名必须是 "id"，不然与 MongoDB 中的 "_id" 行不成映射关系，即每次插入或者查询时得不到 _id 值 <br/>
     * ② 如果有 @MongoId，则属性名可以不必是 "id"。<p/>
     *
     * 2. 当 id 属性存在情况下或者指定了 @MongoId， 插入或保存时<br/>
     * ① 如果未设置 id 属性值，则其值将由数据库自动生成 ObjectId。为了获取自动生成的结果，类中的 id 属性类型必须是 String、ObjectId 、BigInteger 这三者之一，否则会类型转换异常。<br/>
     * ② 如果设置了 id 属性值，存储到 MongoDB 中 _id 值就是实际的 java 数据类型对应转换的 MongoDB 中的 Bson 类型, 即 ObjectId 对应 ObjectId, String 对应 String，Integer 对应 int。<br/>
     * ③ 通常情况下 MongoDB 中 _id 值存储为 objectId, MongoDB 可以实现 ObjectId 到 String 的转换。因此一般 java 中 id 为 String 类型， 注解中中 targetType 声明为 ObjectId。
     */
    @MongoId(targetType = FieldType.OBJECT_ID)
    private String id;

    @Field(value = "first_name")
    private String firstname;

    @Field(value = "last_name")
    private String lastname;

    @Field(value = "age")
    private Integer age;

    @Field(value = "height")
    private Double height;

    @Field(value = "hobbies")
    private List<String> hobbies;

    @Field(value = "friends")
    private List<List<User>> friends;

    @Field(value = "address")
    private Address address;

    @Field(value = "gmt_create")
    private Date gmtCreate;

    @Field(value = "gmt_modified")
    private Date gmtModified;
}
