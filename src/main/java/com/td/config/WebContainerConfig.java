package com.td.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.td.Interceptor.MyInterceptor;
import com.td.controller.BeanNameController;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;

// 因为切入点是Controller，所以必须标注在子容器配置类中，否则不生效
@EnableAspectJAutoProxy
@EnableWebMvc
@Configuration
@ComponentScan(
        basePackages = "com.td.controller",
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class),
        useDefaultFilters = false
)
public class WebContainerConfig implements WebMvcConfigurer {

    // 拦截器配置方式1：针对映射器进行局部配置
    // 注意：
    // 1. 使用BeanNameUrlHandlerMapping映射时，控制器的BeanName必须以URI形式编写
    // 2. 如果HandlerMapping是内置的，那么再手动再添加时必须与原来的BeanName一致，否则无法替换
    //      而如果无法替换，那么手动加入的会排在后面而无法得到应用，所以比较建议注入后进行修改
//    @Autowired
//    public void beanNameHandlerMapping(BeanNameUrlHandlerMapping origin) {
//        origin.setInterceptors(new MyInterceptor());
//    }
//
    @Bean("/test")
    public BeanNameController beanNameController() {
        return new BeanNameController();
    }


    // 拦截配置方式2：全局配置
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor());
    }




    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/views/", ".jsp");
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {

        // 设置日期格式
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter
                = new MappingJackson2HttpMessageConverter(objectMapper);

        return mappingJackson2HttpMessageConverter;
    }
}
