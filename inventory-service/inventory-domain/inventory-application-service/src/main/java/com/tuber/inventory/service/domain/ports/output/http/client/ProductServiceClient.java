package com.tuber.inventory.service.domain.ports.output.http.client;

import com.tuber.application.configuration.http.client.AuthenticationRequestConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "product-service", url = "${product-service.url}", configuration = {AuthenticationRequestConfiguration.class})
public interface ProductServiceClient {

}
