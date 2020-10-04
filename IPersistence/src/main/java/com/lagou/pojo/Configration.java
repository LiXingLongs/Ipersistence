package com.lagou.pojo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于存储读取sqlMapConfig.xml配置文件的映射对象
 */
public class Configration {

    /**
     * 映射数据源
     */
    private DataSource dataSource;

    /**
     * 解析的mapper.xml文件
     *
     * key是statementId mapper的唯一标识
     */
    private Map<String, MappedStatement> mapperMap = new HashMap<>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMapperMap() {
        return mapperMap;
    }

    public void setMapperMap(Map<String, MappedStatement> mapperMap) {
        this.mapperMap = mapperMap;
    }
}
