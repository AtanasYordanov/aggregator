package softuni.aggregator.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.aggregator.constants.CacheConstants;

import java.util.Arrays;


@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache(CacheConstants.COMPANIES),
                new ConcurrentMapCache(CacheConstants.EMPLOYEES),
                new ConcurrentMapCache(CacheConstants.EMPLOYEES_COPANIES)));
        return cacheManager;
    }
}
