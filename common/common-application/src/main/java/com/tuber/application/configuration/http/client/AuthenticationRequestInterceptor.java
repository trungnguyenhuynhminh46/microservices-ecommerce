package com.tuber.application.configuration.http.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
public class AuthenticationRequestInterceptor implements RequestInterceptor {
    private String getAuthenticationHeader() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        return requestAttributes.getRequest().getHeader("Authorization");
    }

    @Override
    public void apply(RequestTemplate template) {
        String authHeader = getAuthenticationHeader();
        if (StringUtils.hasText(authHeader))
            template.header("Authorization", authHeader);
    }
}
