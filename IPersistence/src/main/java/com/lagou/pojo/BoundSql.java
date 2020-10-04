package com.lagou.pojo;

import com.lagou.utils.ParameterMapping;

import java.util.List;

public class BoundSql {

    private String sqlText;
    private List<ParameterMapping> mappingList;

    public BoundSql(String sqlText, List<ParameterMapping> mappingList) {
        this.sqlText = sqlText;
        this.mappingList = mappingList;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public List<ParameterMapping> getMappingList() {
        return mappingList;
    }

    public void setMappingList(List<ParameterMapping> mappingList) {
        this.mappingList = mappingList;
    }
}
