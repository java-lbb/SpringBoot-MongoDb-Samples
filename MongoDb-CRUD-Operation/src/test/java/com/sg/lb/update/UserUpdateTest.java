package com.sg.lb.update;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
public class UserUpdateTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void update(){
    }
}
