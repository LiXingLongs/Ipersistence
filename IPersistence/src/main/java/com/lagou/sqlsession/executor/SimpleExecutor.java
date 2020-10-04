package com.lagou.sqlsession.executor;

import com.lagou.pojo.BoundSql;
import com.lagou.pojo.Configration;
import com.lagou.pojo.MappedStatement;
import com.lagou.utils.GenericTokenParser;
import com.lagou.utils.ParameterMapping;
import com.lagou.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor {
    /**
     * 封装了具体JDBC操纵，使用JDBC访问数据库的流程
     *
     */
    @Override
    public <E> List<E> query(Configration configration, MappedStatement mappedStatement, Object... params) throws Exception {
        // 1.获取数据库连接
        Connection connection = configration.getDataSource().getConnection();

        // 2.解析sql 将sql转成成jdbc可执行的sql语句 并解析参数
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        // 3.获取预编译执行器
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        // 4.设置参数
        Class<?> paramClass = getParamClass(mappedStatement.getParamType());
        // 反射参数类

        List<ParameterMapping> mappingList = boundSql.getMappingList();
        for (int i = 0; i < mappingList.size(); i++) {
            ParameterMapping parameterMapping = mappingList.get(i);
            String content = parameterMapping.getContent();
            Field field = paramClass.getDeclaredField(content);
            field.setAccessible(true);
            Object param = field.get(params[0]);
            preparedStatement.setObject(i + 1, param);
        }

        // 5.运行sql
        ResultSet resultSet = preparedStatement.executeQuery();

        // 6.获取结果并封装
        // 获取返回结果类型
        Class<?> resultClass = getParamClass(mappedStatement.getResultType());
        ArrayList<Object> objects = new ArrayList<>();
        while (resultSet.next()) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            Object instance = resultClass.newInstance();
            // 获取字段个数
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                // 获取字段名称
                String columnName = metaData.getColumnName(i);
                Object value = resultSet.getObject(columnName);
                PropertyDescriptor descriptor = new PropertyDescriptor(columnName, resultClass);
                Method writeMethod = descriptor.getWriteMethod();
                writeMethod.invoke(instance, value);
            }
            objects.add(instance);
        }

        return (List<E>) objects;
    }

    @Override
    public void insert(Configration configration, MappedStatement mappedStatement, Object... params) throws Exception {
        executeUpdate(configration, mappedStatement, params);
    }

    @Override
    public void update(Configration configration, MappedStatement mappedStatement, Object... params) throws Exception {
        executeUpdate(configration, mappedStatement, params);
    }

    @Override
    public void delete(Configration configration, MappedStatement mappedStatement, Object... params) throws Exception {
        executeUpdate(configration, mappedStatement, params);
    }

    private void executeUpdate(Configration configration, MappedStatement mappedStatement, Object... params) throws Exception {
        // 1.获取数据库连接
        Connection connection = configration.getDataSource().getConnection();

        // 2.解析sql 将sql转成成jdbc可执行的sql语句 并解析参数
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        // 3.获取预编译执行器
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        // 4.设置参数
        Class<?> paramClass = getParamClass(mappedStatement.getParamType());
        // 反射参数类

        List<ParameterMapping> mappingList = boundSql.getMappingList();
        for (int i = 0; i < mappingList.size(); i++) {
            ParameterMapping parameterMapping = mappingList.get(i);
            String content = parameterMapping.getContent();
            Field field = paramClass.getDeclaredField(content);
            field.setAccessible(true);
            Object param = field.get(params[0]);
            preparedStatement.setObject(i + 1, param);
        }

        // 5.运行sql
        preparedStatement.executeUpdate();
    }

    private BoundSql getBoundSql(String sql) {
        ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", handler);
        String sqlText = genericTokenParser.parse(sql);
        return new BoundSql(sqlText, handler.getParameterMappings());
    }

    private Class<?> getParamClass(String paramClass) throws ClassNotFoundException {
        if (paramClass != null && paramClass.trim() != "") {
            Class<?> aClass = Class.forName(paramClass);
            return aClass;
        }
        return null;
    }

}
