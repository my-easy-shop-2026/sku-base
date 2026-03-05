package com.linkpay.skuBase.common.config;

import io.seata.spring.annotation.GlobalTransactionScanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeataConfig {

    /**
     * init global transaction scanner
     *
     * @Return: GlobalTransactionScanner
     */
    @Bean
    public GlobalTransactionScanner globalTransactionScanner() {
//        String applicationName = this.applicationContext.getEnvironment().getProperty("spring.application.name");
//        String txServiceGroup = this.seataProperties.getTxServiceGroup();
//        if (StringUtils.isEmpty(txServiceGroup)) {
//            txServiceGroup = applicationName + "-fescar-service-group";
//            this.seataProperties.setTxServiceGroup(txServiceGroup);
//        }
//
//        return new GlobalTransactionScanner(applicationName, txServiceGroup);
        return new GlobalTransactionScanner("linkpay-card-base", "my_test_tx_group");
    }
}
