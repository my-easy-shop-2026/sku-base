package com.linkpay.skuBase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan("com.linkpay")
public class SkuBaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(com.linkpay.skuBase.SkuBaseApplication.class, args);
	}

}
