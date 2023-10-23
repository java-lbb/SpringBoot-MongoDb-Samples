package com.sg.lb.query;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
public class UserQueryTest {
    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void query(){
    }
}
