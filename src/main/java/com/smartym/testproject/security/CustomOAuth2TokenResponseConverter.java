package com.smartym.testproject.security;

import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
public final class CustomOAuth2TokenResponseConverter implements Converter<Map<String, Object>, OAuth2AccessTokenResponse> {
    public static final String SCOPE_DELIMITER = ",";

    private static final String ACCESS_TOKEN_PARAMETER_KEY = "accessToken";
    private static final String TOKEN_TYPE_PARAMETER_KEY = "tokenType";
    private static final String EXPIRES_IN_PARAMETER_KEY = "expiresIn";
    private static final String REFRESH_TOKEN_PARAMETER_KEY = "refreshToken";


    @Override
    public OAuth2AccessTokenResponse convert(Map<String, Object> tokenResponseParameters) {
        String accessToken = getStringParameterValue(tokenResponseParameters, ACCESS_TOKEN_PARAMETER_KEY);
        OAuth2AccessToken.TokenType accessTokenType = getAccessTokenType(tokenResponseParameters);
        long expiresIn = getExpiresIn(tokenResponseParameters);
        String refreshToken = getStringParameterValue(tokenResponseParameters, REFRESH_TOKEN_PARAMETER_KEY);
        Set<String> scopes = getScopes(tokenResponseParameters);

        return OAuth2AccessTokenResponse.withToken(accessToken)
                .tokenType(accessTokenType)
                .expiresIn(expiresIn)
                .scopes(scopes)
                .refreshToken(refreshToken)
                .build();
    }

    private static String getStringParameterValue(Map<String, Object> tokenResponseParameters, String parameterKey) {
        return Optional.ofNullable(tokenResponseParameters)
                .map(params -> params.get(parameterKey))
                .map(Objects::toString)
                .orElse(null);
    }

    private static OAuth2AccessToken.TokenType getAccessTokenType(Map<String, Object> tokenResponseParameters) {
        return OAuth2AccessToken.TokenType.BEARER.getValue().equalsIgnoreCase(
                getStringParameterValue(tokenResponseParameters, TOKEN_TYPE_PARAMETER_KEY))
                ? OAuth2AccessToken.TokenType.BEARER
                : null;
    }

    private static long getExpiresIn(Map<String, Object> tokenResponseParameters) {
        String expiresInStringValue = getStringParameterValue(tokenResponseParameters, EXPIRES_IN_PARAMETER_KEY);
        return expiresInStringValue == null ? 0L : Long.parseLong(expiresInStringValue);
    }

    private static Set<String> getScopes(Map<String, Object> tokenResponseParameters) {
        String scopesStringValue = getStringParameterValue(tokenResponseParameters, OAuth2ParameterNames.SCOPE);
        if (scopesStringValue != null) {
            return Arrays.stream(StringUtils.delimitedListToStringArray(scopesStringValue, SCOPE_DELIMITER))
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }
}
