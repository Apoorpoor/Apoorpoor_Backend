package com.example.apoorpoor_backend.config.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements Oauth2UserInfo{

    private String id;
    private Map<String, Object> attributes; //getAttributes()

    public KakaoUserInfo(Map<String, Object> attributes, String id) {
        this.attributes = attributes;
        this.id = id;
    }
    @Override
    public String getProviderId() {
        return id;
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
        return (String) attributes.get("profile");
    }
}
