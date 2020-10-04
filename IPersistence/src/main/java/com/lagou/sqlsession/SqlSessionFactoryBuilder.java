package com.lagou.sqlsession;

import com.lagou.config.XmlParseBuilder;
import com.lagou.pojo.Configration;
import com.lagou.sqlsession.SqlSessionFactory.DefaultSqlSessionFactory;
import com.lagou.sqlsession.SqlSessionFactory.SqlSessionFactory;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * SqlsessionFactory工厂构建类
 */
public class SqlSessionFactoryBuilder {

    /**
     * 解析配置文件 生成配置对象configration 返回SqlSessionFactory
     */
    public SqlSessionFactory build(InputStream is) throws PropertyVetoException, DocumentException {

        XmlParseBuilder parse = new XmlParseBuilder();
        Configration configration = parse.xmlParse(is);

        return new DefaultSqlSessionFactory(configration);
    }
}
