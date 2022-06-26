package com.smartym.testproject.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.MultiValueMap;

import static com.smartym.testproject.security.CustomOAuth2TokenResponseConverter.SCOPE_DELIMITER;

public final class CustomOAuth2RequestEntityConverter implements Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>> {

    private final OAuth2AuthorizationCodeGrantRequestEntityConverter defaultConverter;

    public CustomOAuth2RequestEntityConverter() {
        defaultConverter = new OAuth2AuthorizationCodeGrantRequestEntityConverter();
    }

    @SuppressWarnings("unchecked")
    @Override
    public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest request) {
        RequestEntity<?> entity = defaultConverter.convert(request);

        MultiValueMap<String, String> params = null;
        if (entity.getBody() != null) {
            params = (MultiValueMap<String, String>) entity.getBody();
            params.add(OAuth2ParameterNames.CLIENT_ID, request.getClientRegistration().getClientId());
            params.add(OAuth2ParameterNames.CLIENT_SECRET, request.getClientRegistration().getClientSecret());
            params.add(OAuth2ParameterNames.SCOPE, String.join(SCOPE_DELIMITER, request.getClientRegistration().getScopes()));
        }

        return new RequestEntity<>(params, entity.getHeaders(), entity.getMethod(), entity.getUrl());
    }
}
