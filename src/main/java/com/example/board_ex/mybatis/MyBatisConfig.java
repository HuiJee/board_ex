package com.example.board_ex.mybatis;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
// 이 클래스가 Spring의 Java 기반 설정 클래스임을 나타냄.
// Spring은 이 클래스를 통해 빈의 설정을 제공받음
@RequiredArgsConstructor
public class MyBatisConfig{

    private final ApplicationContext applicationContext;

    // prefix 상의 프로퍼티 설정을 HikariConfig 빈에 주입
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    @Bean
    public HikariConfig hikariConfig() {return new HikariConfig();}

    // HikariConfig 객체는 데이터 베이스 연결 설정을 담음
    // HikariDataSource 여기에 담아 처리해줘야 연결을 처리!

    // HikariConfig 빈을 이용하여 데이터 베이스 연결에 필요한 DataSource 빈 생성
    // HikariCP는 고성능 JDBC 커넥션 풀 라이브러리로, 여기서는 boot의 자동 설정을 이용하여 설정함
    @Bean
    public DataSource dataSource() {return new HikariDataSource(hikariConfig());}

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath*:/mappers/*.xml"));
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/config/config.xml"));

        try {
            return sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 강의와 다르게 templates에서의 html이 열리지 않았다...
    // thymleaf 디펜던시 추가 안하면 static하위 html만 열리는 듯...
//    @Override
//    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**")
//                .addResourceLocations("/templates/");
//    }

    //        try {
////            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
////            sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
////            // 위 부분을 application.yml에 처리하면 아래 부분과 mybatisConfig() 작성함
////            // sqlSessionFactoryBean.setConfiguration(mybatisConfig());
////            return sqlSessionFactory;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;

    //    @Bean
//    @ConfigurationProperties(prefix = "mybatis.configuration")
//    public org.apache.ibatis.session.Configuration mybatisConfig() {
//        return new org.apache.ibatis.session.Configuration();
//    }




    // SqlSessionFactoryBean을 생성하고, 데이터 소스와 MyBatis 매퍼 파일 위치 설정
    // setDataSource : 데이터 베이스연결에 사용할 데이터 소스 설정
    // setMapperLocation : MyBatis 매퍼 파일의 위치 지정
    // setConfigLoacation : MyBatis의 설정 파일 위치 지정
    // setMapUnderscoreToCamelCase(true) : 데이터베이스의 언더 스코어 네이밍을 자바의 카멜 케이스로 매핑

}
