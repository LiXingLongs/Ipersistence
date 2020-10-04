package com.lagou.test;

import com.lagou.dao.IUserDao;
import com.lagou.io.Resources;
import com.lagou.pojo.User;
import com.lagou.sqlsession.SqlSession.SqlSesssion;
import com.lagou.sqlsession.SqlSessionFactory.SqlSessionFactory;
import com.lagou.sqlsession.SqlSessionFactoryBuilder;
import org.junit.Before;

import java.io.InputStream;

public class Test {
    private SqlSesssion sqlSesssion;

    @Before
    public void before() throws Exception {
        InputStream is = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        sqlSesssion = sqlSessionFactory.openSession();
    }

    /**
     * 测试手动编写的持久层框架
     */
    @org.junit.Test
    public void testSelect() throws Exception {

        // 定义参数
        User user = new User();
        user.setId(1);
        user.setUsername("lucy");
//        User userQ = sqlSesssion.selectOne("user.selectOne", user);
//        System.out.println(userQ);
//        List<User> userQ = sqlSesssion.selectList("user.selectList", user);
//        System.out.println(Arrays.toString(userQ.toArray()));
        IUserDao userDao = sqlSesssion.getMapper(IUserDao.class);
        User result = userDao.findByCondition(user);
        System.out.println(result);

    }

    @org.junit.Test
    public void testInsert() throws Exception {

        // 定义参数
        User user = new User();
        user.setId(3);
        user.setUsername("lili");
        IUserDao userDao = sqlSesssion.getMapper(IUserDao.class);
        userDao.insert(user);

    }

    @org.junit.Test
    public void testUpdate() throws Exception {

        // 定义参数
        User user = new User();
        user.setId(3);
        user.setUsername("wang");
        IUserDao userDao = sqlSesssion.getMapper(IUserDao.class);
        userDao.update(user);

    }

    @org.junit.Test
    public void testDelete() throws Exception {

        // 定义参数
        User user = new User();
        user.setId(3);
        IUserDao userDao = sqlSesssion.getMapper(IUserDao.class);
        userDao.delete(user);

    }
}
