package org.springframework.cloud.springcloudstartergateway.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Data
@Getter
@Setter
public class AuthResponse {
    private String userId;
    private String accessToken;
    private String refreshToken;
    private long expireAt;
    private Collection<String> authorities;
}
