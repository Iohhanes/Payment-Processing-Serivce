package com.smartym.testproject.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

import static org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType.BEARER;

@Component
@RequiredArgsConstructor
public class SecurityUtils {
    private final OAuth2AuthorizedClientService authorizedClientService;

    public String getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        oauthToken.getAuthorizedClientRegistrationId(),
                        oauthToken.getName());
        return BEARER.getValue() + " " + client.getAccessToken().getTokenValue();
    }
}
