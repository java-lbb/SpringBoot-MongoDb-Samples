package com.sg.lb;

import com.sg.lb.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author LiuBing
 * @date: 2023/10/23 17:28
 * @description:
 */
@SpringBootTest
public class UserServiceTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void saveTest(){
        User user = new User();
        user.setName("小明");
        user.setAge(18);
        mongoTemplate.insert(user);
        User one =  mongoTemplate.findOne(Query.query(Criteria.where("name").is(user.getName())),User.class);
        System.out.println(one);
    }

}
