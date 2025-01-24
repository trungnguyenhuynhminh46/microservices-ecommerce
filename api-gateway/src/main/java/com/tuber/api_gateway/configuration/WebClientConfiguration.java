package com.tuber.api_gateway.configuration;

import com.tuber.api_gateway.repository.IdentityClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfiguration {
    @Value("${services.base_url.identity.url}")
    private String identityServiceBaseUrl;

    @Value("${services.base_url.profile.url}")
    private String profileServiceBaseUrl;

    private WebClient buildWebClient(String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    IdentityClient identityClient() {
        WebClient webClient = buildWebClient(identityServiceBaseUrl);
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.
                        builderFor(WebClientAdapter.create((webClient)))
                        .build();

        return httpServiceProxyFactory.createClient(IdentityClient.class);
    }
}
