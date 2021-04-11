package com.td;

import org.apache.catalina.startup.Tomcat;

import java.nio.file.Paths;

public class TomcatStarter {

    private static final String DEFAULT_CONTEXT_PATH;
    private static final String DEFAULT_WEB_CLASSES_PATH;

    static {
        DEFAULT_CONTEXT_PATH = "/"; // 访问路径
        DEFAULT_WEB_CLASSES_PATH = Paths.get("target/classes").toAbsolutePath().toString(); // 可执行项目所在路径
    }

    public static void start() throws Exception {

        Tomcat tomcat = new Tomcat();

        tomcat.addWebapp(DEFAULT_CONTEXT_PATH, DEFAULT_WEB_CLASSES_PATH);

        tomcat.setPort(8888);
        tomcat.start();

        // 启动连接器，处理HTTP请求
        tomcat.getConnector().start();
    }

    public static void main(String[] args) {
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
