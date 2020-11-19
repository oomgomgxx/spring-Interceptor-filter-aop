package com.td;

import com.td.config.RootContainerConfig;
import com.td.config.WebContainerConfig;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer<getServletApplicationContextInitializers> extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static final String SERVLET_MAPPING_PATH = "/";

    // 父容器
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ RootContainerConfig.class };
    }

    // 子容器
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{ WebContainerConfig.class };
    }

    // 映射路径
    @Override
    protected String[] getServletMappings() {
        return new String[]{ SERVLET_MAPPING_PATH };
    }
}
