package com.lagou.io;

import java.io.InputStream;

public class Resources {

    /**
     * 读取sqlMapConfig.xml,生成文件流。
     *
     * @param path 路径
     * @return 返回配置文件流
     */
    public static InputStream getResourceAsStream(String path){
        InputStream is = Resources.class.getClassLoader().getResourceAsStream(path);
        return is;
    }
}
