package softuni.aggregator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import softuni.aggregator.web.interceptor.LogInterceptor;
import softuni.aggregator.web.interceptor.PerformanceInterceptor;
import softuni.aggregator.web.interceptor.UserDataInterceptor;

@Configuration
@EnableSpringDataWebSupport
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PerformanceInterceptor())
                .addPathPatterns("/imports/xing", "/imports/orbis", "/imports/employees");

        registry.addInterceptor(new UserDataInterceptor())
                .excludePathPatterns("/register", "/login");
        registry.addInterceptor(new LogInterceptor());
    }
}
