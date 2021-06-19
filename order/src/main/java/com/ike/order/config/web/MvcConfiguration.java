package com.ike.order.config.web;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * WebMvcConfigurer  //配置视图解析器 viewResolver
 * ApplicationContextAware  //获取ApplicationContext对象  实现这个接口可以获取spring容器中所有的bean
 */
@Configuration
@EnableWebMvc //开启SpringMVC注解模式  等效于:<mvc:annotation-driven/>
public class MvcConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    private static String resourceLocationsOfWin;

    private static String resourceLocationsOfOS;

    @Value("${winPath}")
    public void setResourceLocationsOfWin(String resourceLocationsOfWin) {
        MvcConfiguration.resourceLocationsOfWin = resourceLocationsOfWin;
    }

    @Value("${linuxPath}")
    public void setResourceLocationsOfOS(String resourceLocationsOfOS) {
        MvcConfiguration.resourceLocationsOfOS = resourceLocationsOfOS;
    }

    //Spring容器
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 静态资源配置
     *
     * @param registry 解析工具类
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
        //根据操作系统类型选择静态文件映射路径
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            registry.addResourceHandler("/upload/**").addResourceLocations("file:" + resourceLocationsOfWin.trim() + "/upload/");
        } else {
            registry.addResourceHandler("/upload/**").addResourceLocations("file:" + resourceLocationsOfOS.trim() + "/upload/");
        }
        //原代码 默认os
        //registry.addResourceHandler("/upload/**").addResourceLocations("file:/Users/baidu/work/image/upload/");

    }

    /**
     * 定义默认的请求处理器   等价于:<mvc:default-servlet-handler/>
     *
     * @param configurer 配置对象
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();//设置为可用
    }

    /**
     * 视图解析器
     *
     * @return 视图解析器对象
     */
    @Bean(name = "viewResolver")
    public ViewResolver creatViewResolver() {
        //视图解析器工具
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        //设置spring容器,获取其中的bean
        internalResourceViewResolver.setApplicationContext(this.applicationContext);
        //取消缓存
        internalResourceViewResolver.setCache(false);
        //设置解析前缀 加上该前缀在服务期内寻找资源
        internalResourceViewResolver.setPrefix("/WEB-INF/html/");
        //设置视图解析的后缀  加上该后缀查找资源
        internalResourceViewResolver.setSuffix(".html");

        return internalResourceViewResolver;
    }

    /**
     * 文件解析器
     *
     * @return 文件解析器对象
     */
    @Bean(name = "multipartResolver")
    public MultipartResolver creatMultipartResolver() {
        //文件解析器对象
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        //默认字符编码
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        //最大上传限制<!-- 1024*1024*20 上传限制20兆-->
        commonsMultipartResolver.setMaxUploadSize(20971520L);
        //最大缓存限制
        commonsMultipartResolver.setMaxInMemorySize(20971520);
        //返回文件解析器对象
        return commonsMultipartResolver;
    }

    /**
     * 注册拦截器
     *
     * @param registry 注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

}
