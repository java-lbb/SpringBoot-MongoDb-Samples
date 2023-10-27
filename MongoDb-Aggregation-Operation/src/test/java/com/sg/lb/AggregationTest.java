package com.sg.lb;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

/**
 * @author LiuBing
 * @date: 2023/10/27 13:03
 * @description: 聚合测试
 */
@SpringBootTest
public class AggregationTest {

    @Test
    public void groupTest(){
        GroupOperation group = group("age");
        project("firstname");
        Aggregation aggregation = Aggregation.newAggregation();
    }
}
