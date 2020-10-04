package com.lagou.sqlsession.executor;

import com.lagou.pojo.Configration;
import com.lagou.pojo.MappedStatement;

import java.util.List;

public interface Executor {

    <E> List<E> query(Configration configration, MappedStatement mappedStatement, Object...params) throws Exception;

    void insert(Configration configration, MappedStatement mappedStatement, Object...params) throws Exception;

    void update(Configration configration, MappedStatement mappedStatement, Object...params) throws Exception;

    void delete(Configration configration, MappedStatement mappedStatement, Object...params) throws Exception;
}
