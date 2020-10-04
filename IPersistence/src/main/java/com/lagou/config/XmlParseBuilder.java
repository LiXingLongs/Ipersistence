package com.lagou.config;

import com.lagou.config.XMLMapperBuilder.XMLMapperBuilder;
import com.lagou.io.Resources;
import com.lagou.pojo.Configration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * 解析xml构建类
 *
 */
public class XmlParseBuilder {

    private Configration configration;

    public XmlParseBuilder() {
        this.configration = new Configration();
    }

    // 解析xml文件 并将解析结果封装到config对象中
    public Configration xmlParse(InputStream is) throws DocumentException, PropertyVetoException {

        // 开始解析xml文件
        Document document = new SAXReader().read(is);
        // 获取根节点
        Element rootElement = document.getRootElement();
        // 获取properties标签
        List<Element> list = rootElement.selectNodes("//property");
        Properties param = new Properties();

        // 获取配置属性
        for (Element element : list) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            param.setProperty(name, value);
        }

        // 获取dataSource
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(param.getProperty("driverClass"));
        dataSource.setJdbcUrl(param.getProperty("jdbc"));
        dataSource.setUser(param.getProperty("username"));
        dataSource.setPassword(param.getProperty("password"));
        // 获取连接数据库配置
        configration.setDataSource(dataSource);

        // 解析sqlMapConfig.xml中的mapper配置文件
        List<Element> mapperList = rootElement.selectNodes("//mapper");
        if (mapperList != null && !mapperList.isEmpty()) {
            for (Element element : mapperList) {
                // 获取mapper配置文件的流
                String mapperPath = element.attributeValue("resource");
                InputStream mapperIs = Resources.getResourceAsStream(mapperPath);
                XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configration);
                xmlMapperBuilder.parse(mapperIs);
                // 填充configraion完毕
            }
        }

        return configration;
    }
}
