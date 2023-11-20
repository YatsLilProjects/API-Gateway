package org.springframework.cloud.springcloudstartergateway.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.springcloudstartergateway.model.AuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @GetMapping("/login")
    public ResponseEntity<AuthResponse> login(@RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient oAuth2AuthorizedClient,
                                              @AuthenticationPrincipal OidcUser user,
                                              Model model) {
        log.info("User email id: {}", user.getEmail());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setUserId(user.getEmail());
        authResponse.setAccessToken(oAuth2AuthorizedClient.getAccessToken().getTokenValue());
        authResponse.setRefreshToken(oAuth2AuthorizedClient.getAccessToken().getTokenValue());
        authResponse.setExpireAt(Objects.requireNonNull(oAuth2AuthorizedClient.getAccessToken().getExpiresAt()).getEpochSecond());
        List<String> authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        authResponse.setAuthorities(authorities);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

}
