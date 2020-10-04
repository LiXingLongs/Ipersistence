package com.lagou.sqlsession.SqlSessionFactory;

import com.lagou.sqlsession.SqlSession.SqlSesssion;

/**
 * sqlsession工厂
 *
 */
public interface SqlSessionFactory {

    SqlSesssion openSession();
}
