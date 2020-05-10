package com.demo.dive.cube.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;
import java.io.File;

@Configuration

//@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {






  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  /*@Bean(name = "multipartResolver")
  public CommonsMultipartResolver getResolver() throws IOException {
    CommonsMultipartResolver resolver = new CommonsMultipartResolver();
    resolver.setMaxUploadSize(5242880);//5MB
    return resolver;
  }*/

  /*@Bean(name = "commonsMultipartResolver")
  public MultipartResolver multipartResolver() throws IOException {
      org.springframework.web.multipart.commons.CommonsMultipartResolver multipartResolver = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
      multipartResolver.setMaxUploadSize(100000000);
      return multipartResolver;
  }*/

  /*@Bean
  public FilterRegistrationBean multipartFilterRegistrationBean() {
    final MultipartFilter multipartFilter = new MultipartFilter();
    final FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(multipartFilter);
    //filterRegistrationBean.addInitParameter("multipartResolverBeanName", "commonsMultipartResolver");
    filterRegistrationBean.addInitParameter("multipartResolver", "commonsMultipartResolver");
    return filterRegistrationBean;
  }*/



  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    File directory = new File (".");
    String absPath = directory.getAbsolutePath();

    String readPath =  String.format("%sstudentImages/", absPath.replace("\\", "/").replace(".", ""));
	  String formatted = readPath;

      registry.addResourceHandler("/studentImage/**").addResourceLocations(String.format("file:///%s", formatted));

  }



}
