package com.lagou.sqlsession.SqlSession;

import java.util.List;

public interface SqlSesssion {


    <E> List<E> selectList(String statementId, Object...params) throws Exception;

    <T> T selectOne(String statementId, Object...params) throws Exception;

    void insert(String statementId, Object...params) throws Exception;

    void update(String statementId, Object...params) throws Exception;

    void delete(String statementId, Object...params) throws Exception;

    <T> T getMapper(Class<?> class1);

}
