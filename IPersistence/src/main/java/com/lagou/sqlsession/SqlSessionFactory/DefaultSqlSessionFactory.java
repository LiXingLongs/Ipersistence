package com.lagou.sqlsession.SqlSessionFactory;

import com.lagou.pojo.Configration;
import com.lagou.sqlsession.SqlSession.DefaultSqlSession;
import com.lagou.sqlsession.SqlSession.SqlSesssion;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configration configration;

    public DefaultSqlSessionFactory(Configration configration) {
        this.configration = configration;
    }

    /**
     * 返回Sqlsession
     *
     */
    @Override
    public SqlSesssion openSession() {
        return new DefaultSqlSession(configration);
    }
}
