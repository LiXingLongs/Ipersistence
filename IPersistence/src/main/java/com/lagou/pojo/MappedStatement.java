package com.lagou.pojo;

/**
 * 解析mapper.xml的映射对象
 */
public class MappedStatement {

    /**
     * 定义标签操作类型
     */
    public static final int INSERT = 1;
    public static final int UPDATE = 2;
    public static final int DELETE = 3;
    public static final int SELECT = 4;

    /****
     * 文件唯一ID
     */
    private String id;
    /**
     * 返回值类型
     */
    private String resultType;
    /***
     * 参数类型
     */
    private String paramType;
    /**
     * 要执行的sql语句
     */
     private String sql;

    /**
     * 标签操作语句类型
     *
     */
    private Integer operationType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }
}
