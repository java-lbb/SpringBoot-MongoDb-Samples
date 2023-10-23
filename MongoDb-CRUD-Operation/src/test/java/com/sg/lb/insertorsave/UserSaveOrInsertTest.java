package com.sg.lb.insertorsave;

import com.sg.lb.entity.Animal;
import com.sg.lb.entity.User;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * @author LiuBing
 * @date: 2023/10/23 17:28
 * @description: UserSaveOrInsertTest
 */
@SpringBootTest
public class UserSaveOrInsertTest {

    private final static List<User> userList = new ArrayList<>();

    static  {
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setName(RandomString.make());
            user.setAge(new Random().nextInt(30));
            userList.add(user);
        }
    }

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 插入一个对象。 如果存在具有相同 id 的现有文档，则会生成错误。
     */
    @Test
    public void insert(){
        User user = new User();
        user.setName("小明");
        user.setAge(18);
        mongoTemplate.insert(user);// 插入，有Document注解值则为该集合名，没有注解则以类名小写开头即 "user"
        System.out.println(user);
        //mongoTemplate.insert(user,"user");指定集合名称
    }

    /**
     * 批量插入
     */
    @Test
    public void batchInsert(){
        Collection<User> users = mongoTemplate.insert(userList, User.class);
        for (User user : users) {
            System.out.println(user);
        }
        // mongoTemplate.insert(userList); !!! 这种写法进行批量插入是错误的。他会把整个集合对象当成一个 document 处理， 会类型转换异常。
        // mongoTemplate.insert(userList,"user");
        // mongoTemplate.insertAll(userList);
    }


    /**
     * 保存对象，如果存在相同 id 的文档，则会覆盖该条文档，不会报错。
     */
    @Test
    public void save(){
        User user = new User();
        user.setId("123");
        user.setName("小明");
        user.setAge(18);
        mongoTemplate.save(user);
        System.out.println(user);
    }
}
