package com.td;


import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

// 指定Web容器启动时需要处理的接口实现
// WebApplicationInitializer是Spring用于在Web环境下进行容器配置的接口
//@HandlesTypes(WebApplicationInitializer.class)
public class WebServletContainerInitializer implements ServletContainerInitializer {

    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        // TODO: 容器启动时需要做的配置

//        ctx.addFilter("my-filter", new HttpFilter(){
//
//            @Override
//            protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//                System.out.println("执行过滤器：" + this.getFilterName());
//                chain.doFilter(request, response);
//            }
//
//            @Override
//            public void destroy() {
//                // TODO
//            }
//        }).addMappingForUrlPatterns(null, false, "/*");
//
//        ctx.addListener(new ServletRequestListener() {
//            @Override
//            public void requestInitialized(ServletRequestEvent sre) {
//                System.out.println("ServletRequest被初始化");
//            }
//            @Override
//            public void requestDestroyed(ServletRequestEvent sre) {
//                // TODO
//            }
//        });

    }
}