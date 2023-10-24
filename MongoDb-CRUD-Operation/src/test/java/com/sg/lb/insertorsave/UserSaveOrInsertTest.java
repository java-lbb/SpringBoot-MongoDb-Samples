package com.sg.lb.insertorsave;

import cn.hutool.core.util.RandomUtil;
import com.sg.lb.entity.Address;
import com.sg.lb.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author LiuBing
 * @date: 2023/10/23 17:28
 * @description: UserSaveOrInsertTest
 */
@SpringBootTest
public class UserSaveOrInsertTest {

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 插入一个对象。 如果存在具有相同 id 的现有文档，则会生成错误。
     */
    @Test
    public void insert(){
        User user = this.getUserList(1).get(0);
        user.setFriends(this.getUserList(2));
        mongoTemplate.insert(user);// 插入，有Document注解值则为该集合名，没有注解则以类名小写开头即 "user"
        System.out.println(user);
        //mongoTemplate.insert(user,"user");指定集合名称
    }

    /**
     * 批量插入
     */
    @Test
    public void batchInsert(){
        List<User> userList = this.getUserList(5);
        for (User user : userList) {
            user.setFriends(this.getUserList(2));
        }
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
        User user = getUserList(1).get(0);
        user.setFriends(getUserList(2));
        mongoTemplate.save(user);
        System.out.println(user);
    }


    public static List<User> getUserList(int limit){
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            User user = new User();
            user.setFirstname(RandomUtil.randomString(3));
            user.setLastname(RandomUtil.randomString(3));
            user.setAge(RandomUtil.randomInt(10,30));
            user.setHeight(RandomUtil.randomDouble(160.0,190.0));
            user.setHobbies(RandomUtil.randomEleList(Arrays.asList("篮球","羽毛球","乒乓球","游泳","跑步","滑雪"),3) );
            List<Address> addressList = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                Address address = new Address();
                address.setProvince("广东省");
                address.setCity(Arrays.asList("广州市","深圳市","东莞市","中山市","江门市").get(RandomUtil.randomInt(3)));
                addressList.add(address);
            }
            user.setAddress(addressList.get(RandomUtil.randomInt(5)));
            userList.add(user);
        }
        return userList;
    }
}
