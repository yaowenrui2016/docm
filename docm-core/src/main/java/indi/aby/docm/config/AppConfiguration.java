package indi.aby.docm.config;

import indi.aby.docm.core.auth.IAuthServiceApi;
import indi.aby.docm.core.operlog.InitOperLogFilter;
import indi.aby.docm.core.auth.AuthFilter;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableScheduling
@EnableAspectJAutoProxy
@EnableTransactionManagement
@MapperScan(basePackages = {"indi.aby.docm"},annotationClass = Mapper.class)
public class AppConfiguration {

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1允许任何域名使用
        corsConfiguration.addAllowedHeader("*"); // 2允许任何头
        corsConfiguration.addAllowedMethod("*"); // 3允许任何方法
        corsConfiguration.addExposedHeader("X-AUTH-TOKEN");
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        return new CorsFilter(source);
    }

    @Bean
    public AuthFilter authFilter(@Value("${docm.auth.freeUrl:}")String[] freeUrls,
                                 @Autowired IAuthServiceApi authServiceApi) {
        return new AuthFilter(freeUrls, authServiceApi);
    }

    @Bean
    public InitOperLogFilter initOperLogFilter() {
        return new InitOperLogFilter();
    }

    @Bean
    public PlatformTransactionManager txManager(@Qualifier("serviceDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
