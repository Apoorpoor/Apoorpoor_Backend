package com.example.apoorpoor_backend.auth;

//시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
//로그인 진행이 완료되면 시큐리티 session을 만들어준다. (Security ContextHolder)
//오브젝트 타입 => Authentication 타입 객체
//Authentication 안에 User정보가 있어야 됨.
//User오브젝트타입 => UserDetails 타입 객체


import com.example.apoorpoor_backend.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

//Security Session 영역 => Authentication => UserDetails
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {
        /*
    UserDetails를 implements 받아 PrincipalDetails는 Authentication안의 UserDetails 처럼
    이용할 수 있게 되었다.
    PrincipalDetails의 정보를 Authentication안에 넣을 수 있게 되었다.
     */

    private User user;  //컴포지션
    private Map<String, Object> attributes;

    //일반 로그인을 할때 사용하는 생성자
    public PrincipalDetails(User user) {
        this.user = user;
    }

    //Oauth 로그인을 할때 사용하는 생성자
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    //해당 User의 권한을 리턴하는 역할
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        //우리사이트에서 1년동안 회원이 로그인을 안하면 휴먼 계정으로 전환하기로 함.
        //현재시간 - 로그인시간 => 1년을 초과하면 return을 false 한다 등으로 설정 가능
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
