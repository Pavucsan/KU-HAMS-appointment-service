package com.ku.kuhamsappointmentservice.configuration.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<ApiLoggingFilter> logFilter() {
        FilterRegistrationBean<ApiLoggingFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new ApiLoggingFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1); // High priority

        return registrationBean;
    }
}
