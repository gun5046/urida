package com.urida.coreweb.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    public void addCorsMappings(CorsRegistry registry) {
//		System.out.println("CORS Setting");
//		Allow all origins.
//		Allow "simple" methods GET, HEAD and POST.
//		Allow all headers.
//		Set max age to 1800 seconds (30 minutes).
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
//			.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD")
                .maxAge(1800);

    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");

        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/3.52.5/");
    }

}
