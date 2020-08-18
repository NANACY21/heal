package com.personal.config;

import com.personal.config.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * web配置
 *
 * @author 李箎
 */
@Configuration
//@EnableWebMvc
public class WebMvcConfigurerImpl implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     * 解决跨域问题
     * 随意加配置类可能导致跨域无效，数据加载不出来
     * .allowedOrigins(ConstPool.WEB_IP)
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowCredentials(true);
    }

    /**
     * 该方法用来配置静态资源，例如如html，js，css等
     * 不要别的类再来一个同名的空方法
     * 解决：前端图片url为磁盘文件，浏览器安全机制拦截掉不显示
     * 浏览器不能访问本地资源，设置虚拟路径
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }

    /**
     * 拦截器配置
     * 注册添加拦截器
     * 该方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/register", "/isLogged");
    }

    /**
     * 已登录在线用户
     * @return
     */
//    @Bean
//    public ServletListenerRegistrationBean getListener() {
//        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
//        servletListenerRegistrationBean.setListener(new HttpSessionListenerImpl());
//        return servletListenerRegistrationBean;
//    }
}
