package softuni.aggregator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import softuni.aggregator.web.interceptor.PerformanceInterceptor;
import softuni.aggregator.web.interceptor.UsernameInterceptor;

@Configuration
@EnableSpringDataWebSupport
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PerformanceInterceptor())
                .addPathPatterns("/imports/**", "/exports/**");

        registry.addInterceptor(new UsernameInterceptor());
    }
}
