package lims.api.config;

import lims.api.common.interceptor.mybatis.UpdateDetectInterceptor;
import lims.api.common.service.impl.UpdateDetectExecutor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {

    public MyBatisConfig(SqlSessionFactory sqlSessionFactory, UpdateDetectExecutor executor) {
        sqlSessionFactory.getConfiguration().addInterceptor(new UpdateDetectInterceptor(executor));
    }

}