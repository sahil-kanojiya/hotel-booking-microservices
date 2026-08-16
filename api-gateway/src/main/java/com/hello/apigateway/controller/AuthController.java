package com.hello.apigateway.controller;

import com.hello.apigateway.payload.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @GetMapping("/login")
    public ResponseEntity<ApiResponse> login(
            @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client,
            @AuthenticationPrincipal OidcUser oidcUser
    ) {
        log.info("user email id :{}", oidcUser.getEmail());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setUserId(oidcUser.getEmail());
        apiResponse.setAccessToken(client.getAccessToken().getTokenValue());

        if (client.getRefreshToken() != null) {
            apiResponse.setRefreshToken(client.getRefreshToken().getTokenValue());
        }

        if (client.getAccessToken().getExpiresAt() != null) {
            apiResponse.setExpireAt(client.getAccessToken().getExpiresAt().getEpochSecond());
        }

        List<String> authorities = oidcUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        apiResponse.setAuthorities(authorities);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
