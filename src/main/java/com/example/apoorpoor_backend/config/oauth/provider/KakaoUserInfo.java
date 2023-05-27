package com.example.apoorpoor_backend.config.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements Oauth2UserInfo{

    private Map<String, Object> attributes; //getAttributes()

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
    @Override
    public String getProviderId() {
        return (String)attributes.get("id");
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}
