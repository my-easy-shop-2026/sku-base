package com.linkpay.skuBase.common.config;

import com.alibaba.cloud.nacos.discovery.NacosDiscoveryClient;
import com.linkpay.orderBase.api.OrderApi;
import com.linkpay.orderBase.model.*;

import javax.annotation.Resource;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@AutoConfigureAfter(WebConfig.class)
@Slf4j
public class OrderWebConfig {

  @Autowired
  private WebClient.Builder webClientBuilder;

  @Resource
  private NacosDiscoveryClient nacosDiscoveryClient;


  private class LazyCardOrderApi implements OrderApi {

    @SneakyThrows
    private OrderApi getOrderApi() {
      var instance = nacosDiscoveryClient.getInstances("linkpay-order-base");

      var count = 0;
      while (instance.size() == 0 && count < 120) {
        log.info("wait for service order-base ready...");
        Thread.sleep(1000);
        instance = nacosDiscoveryClient.getInstances("linkpay-order-base");
      }

      if (instance.size() == 0) {
        log.error("fail to start service due to order-base not ready");
        return null;
      }

      HttpServiceProxyFactory httpServiceProxyFactory =
          HttpServiceProxyFactory.builder(
                  WebClientAdapter.forClient(webClientBuilder.baseUrl(
                      "http://" + instance.get(0).getHost() + ":" + instance.get(0).getPort()).build()))
              .build();
      return httpServiceProxyFactory.createClient(OrderApi.class);
    }


    @Override
    public ResponseEntity<OrderAddResponse> orderAdd(Order cardOrder) {
      return getOrderApi().orderAdd(cardOrder);
    }

    @Override
    public ResponseEntity<OrderApplyResponse> orderBuy(
        OrderApplyRequest cardOrderApplyRequest) {
      return getOrderApi().orderBuy(cardOrderApplyRequest);
    }

//    @Override
//    public ResponseEntity<OrderCancelResponse> orderCancel(Long cardOrderId) {
//      return getOrderApi().orderCancel(cardOrderId);
//    }

    @Override
    public ResponseEntity<OrderGetResponse> orderGet(OrderQuery cardOrder) {
      return getOrderApi().orderGet(cardOrder);
    }

//    @Override
//    public ResponseEntity<OrderLatestExpiredGetResponse> orderLatestExpiredGet(
//        OrderLatestExpiredGetRequest orderLatestExpiredGetRequest) {
//      return getOrderApi().orderLatestExpiredGet(cardOrderLatestExpiredGetRequest);
//    }

    @Override
    public ResponseEntity<OrderPageResponse> orderPageGet(
        OrderPageRequest cardOrderPageRequest) {
      return getOrderApi().orderPageGet(cardOrderPageRequest);
    }

    @Override
    public ResponseEntity<OrderRepayResponse> orderRepay(Long cardOrderId,
        OrderRepayRequest cardOrderRepayRequest) {
      return getOrderApi().orderRepay(cardOrderId, cardOrderRepayRequest);
    }

    @Override
    public ResponseEntity<OrderUpdateResponse> orderUpdate(Long cardOrderId,
        OrderUpdateRequest cardOrder) {
      return getOrderApi().orderUpdate(cardOrderId, cardOrder);
    }
  }

  @SneakyThrows
  @Bean
  OrderApi orderApi() {
    return new LazyCardOrderApi();
  }
}
