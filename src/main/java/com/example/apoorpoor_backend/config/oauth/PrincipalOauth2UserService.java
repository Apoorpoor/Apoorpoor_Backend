package com.example.apoorpoor_backend.config.oauth;

import com.example.apoorpoor_backend.auth.PrincipalDetails;
import com.example.apoorpoor_backend.config.oauth.provider.*;
import com.example.apoorpoor_backend.model.User;
import com.example.apoorpoor_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    //구글로 부터 userRequest 데이터에 대한 후처리 되는 함수
    //함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration : " + userRequest.getClientRegistration());
        //registration으로 어떤 Oauth로 로그인했는지 확인가능
        System.out.println("getAccessToken : " + userRequest.getAccessToken().getTokenValue());

        /*
        구글 로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인을 완료 -> code를 리턴(Oauth-Clinet 라이브러리)
        -> AccessToken요청 -> userRequest 정보 -loadUser함수 호출
        -> 구글로부터 회원프로필 받아줌
         */

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("getAttributes : " + oAuth2User.getAttributes());

        Oauth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());

        } else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
            System.out.println("페이스북 로그인 요청");
            oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());

        } else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            System.out.println("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));

        } else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            System.out.println("카카오 로그인 요청");
            oAuth2UserInfo = new KakaoUserInfo((Map)oAuth2User.getAttributes().get("kakao_account"));

        }else {
            System.out.println("구글과 페이스북, 네이버 로그인만 가능합니다.");
        }

        String provider = oAuth2UserInfo.getProvider(); //google
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider+"_"+providerId; //google_000000000000000000
        String email =oAuth2UserInfo.getEmail();
        String role = "ROLE_USER";

        //User userEntity = userRepository.findByUsername(username);
        Optional<User> findUser = userRepository.findByUsername(username);
        User user =findUser.get();
        if(findUser.isEmpty()) {
            System.out.println("Oauth인이 최초입니다.");
//            userEntity = User.builder()
//                    .username(username)
//                    .email(email)
//                    .role(role)
//                    .provider(provider)
//                    .providerId(providerId)
//                    .build();

             user = User.builder()
                    .username(username)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();

            userRepository.save(user);
        } else {
            System.out.println("로그인을 이미 한 적이 있습니다.");
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}