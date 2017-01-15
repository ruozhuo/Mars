package com.yiran.ruozhuo.controller;

import com.yiran.ruozhuo.model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Reader;

/**
 * Created by ruozhuo on 2017/1/6.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader;

    static {
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/get")
    public String getUserById(@RequestParam(value = "id") String id) {
        SqlSession session = sqlSessionFactory.openSession();
        User user = null;
        try {
            user = (User) session.selectOne("User.selectUserByID", 1);
            session.commit();
            System.out.println(user);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user.toString();
    }

}
