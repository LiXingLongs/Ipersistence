package com.lagou.config.XMLMapperBuilder;

import com.lagou.pojo.Configration;
import com.lagou.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuilder {

    private Configration configration;

    public XMLMapperBuilder(Configration configration) {
        this.configration = configration;
    }

    public void parse(InputStream is) throws DocumentException {

        Document document = new SAXReader().read(is);
        Element rootElement = document.getRootElement();
        // 获取根节点命名空间
        String namespace = rootElement.attributeValue("namespace");
        // 解析查询语句
        List<Element> selectNodes = rootElement.selectNodes("//select");
        clean(selectNodes, namespace, MappedStatement.SELECT);
        // 解析添加语句
        List<Element> insertNodes = rootElement.selectNodes("//insert");
        clean(insertNodes, namespace, MappedStatement.INSERT);
        // 解析更新语句
        List<Element> updateNodes = rootElement.selectNodes("//update");
        clean(updateNodes, namespace, MappedStatement.UPDATE);
        // 解析删除语句
        List<Element> deleteNodes = rootElement.selectNodes("//delete");
        clean(deleteNodes, namespace, MappedStatement.DELETE);

    }

    private void clean(List<Element> list, String namespace, Integer operationType){
        if (list != null && !list.isEmpty()) {
            for (Element element : list) {
                MappedStatement mappedStatement = new MappedStatement();
                String id = element.attributeValue("id");
                mappedStatement.setId(id);
                mappedStatement.setParamType(element.attributeValue("paramType"));
                mappedStatement.setResultType(element.attributeValue("resultType"));
                mappedStatement.setOperationType(operationType);
                mappedStatement.setSql(element.getTextTrim());
                String statementId = namespace + "." + id;
                configration.getMapperMap().put(statementId, mappedStatement);
            }
        }
    }
}
