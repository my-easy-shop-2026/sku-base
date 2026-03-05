package com.linkpay.skuBase.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParameterConfig {

    @Value("${sql.limit-size:3000}")
    public Integer sqlLimitSize;

}
