package com.smartym.testproject.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("app")
public class AppProperties {
    private SmartymIntegrationProperties smartym;
    private SecurityProperties security;

    @Data
    public static class SmartymIntegrationProperties {
        private String baseUrl;
    }

    @Data
    public static class SecurityProperties {
        private String stubOAuth2UserName;
    }
}
