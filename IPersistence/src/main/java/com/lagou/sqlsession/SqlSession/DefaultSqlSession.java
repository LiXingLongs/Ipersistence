package com.lagou.sqlsession.SqlSession;

import com.lagou.pojo.Configration;
import com.lagou.pojo.MappedStatement;
import com.lagou.sqlsession.executor.SimpleExecutor;

import java.lang.reflect.*;
import java.util.List;

public class DefaultSqlSession implements SqlSesssion {

    private Configration configration;

    public DefaultSqlSession(Configration configration) {
        this.configration = configration;
    }


    /**
     *sqlSession中封装具体的操作方法
     *
     */
    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {
        return (List<E>) new SimpleExecutor().query(configration, configration.getMapperMap().get(statementId), params);
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<Object> objects = selectList(statementId, params);
        if (objects == null || objects.size() != 1) {
            throw new RuntimeException("返回结果不正确");
        } else {
            return (T) objects.get(0);
        }
    }

    @Override
    public void insert(String statementId, Object... params) throws Exception {
        new SimpleExecutor().insert(configration, configration.getMapperMap().get(statementId), params);
    }

    @Override
    public void update(String statementId, Object... params) throws Exception {
        new SimpleExecutor().update(configration, configration.getMapperMap().get(statementId), params);
    }

    @Override
    public void delete(String statementId, Object... params) throws Exception {
        new SimpleExecutor().delete(configration, configration.getMapperMap().get(statementId), params);
    }


    @Override
    public <T> T getMapper(Class<?> class1) {

        // 使用动态代理调用方法
        Object instance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{class1}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 获取方法名
                String methodName = method.getName();
                // 获取调用类名
                String className = method.getDeclaringClass().getName();
                String statementId = className + "." + methodName;

                // 获取当前方法的操作类型
                MappedStatement mappedStatement = configration.getMapperMap().get(statementId);
                if (mappedStatement == null) {
                    throw new RuntimeException("mapper xml parse error！");
                }
                switch (mappedStatement.getOperationType()) {
                    case MappedStatement.SELECT:
                        // 获取方法返回值 泛型类型参数化
                        Type genericReturnType = method.getGenericReturnType();
                        if (genericReturnType instanceof ParameterizedType) {
                            return selectList(statementId, args);
                        }
                        return selectOne(statementId, args);
                    case MappedStatement.INSERT:
                        insert(statementId, args);
                        System.out.println("插入成功！");
                        break;
                    case MappedStatement.UPDATE:
                        update(statementId, args);
                        System.out.println("更新成功！");
                        break;
                    case MappedStatement.DELETE:
                        delete(statementId, args);
                        System.out.println("删除成功！");
                        break;
                    default:
                        throw new RuntimeException("没有该方法操作类型！");
                }
                return new Object();
            }
        });
        return (T) instance;
    }
}
