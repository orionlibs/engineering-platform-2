package com.dimi.user.configuration;

import com.dimi.core.api.filter.TraceFilter;
import com.dimi.core.api.filter.UserIDFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class HTTPFilterConfiguration
{
    @Bean
    public FilterRegistrationBean<TraceFilter> traceFilterRegistration(TraceFilter traceFilter)
    {
        FilterRegistrationBean<TraceFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(traceFilter);
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registration.addUrlPatterns("/*");
        return registration;
    }


    @Bean
    public FilterRegistrationBean<UserIDFilter> userIDFilterRegistration(UserIDFilter userIDFilter)
    {
        FilterRegistrationBean<UserIDFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(userIDFilter);
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        registration.addUrlPatterns("/*");
        return registration;
    }
}
