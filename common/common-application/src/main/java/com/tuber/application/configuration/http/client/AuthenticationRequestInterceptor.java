package com.tuber.application.configuration.http.client;

import com.tuber.application.helper.CommonHelper;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@Slf4j
public class AuthenticationRequestInterceptor implements RequestInterceptor {
    @Autowired
    CommonHelper commonHelper;

    @Override
    public void apply(RequestTemplate template) {
        String authHeader = commonHelper.getHeaderValue("Authorization");
        if (StringUtils.hasText(authHeader))
            template.header("Authorization", authHeader);
    }
}
