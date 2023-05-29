package com.example.apoorpoor_backend.config;

import com.example.apoorpoor_backend.config.oauth.PrincipalOauth2UserService;
import com.example.apoorpoor_backend.config.oauth.handler.OAuth2LoginFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.view.RedirectView;

@Configuration
@EnableWebSecurity  // 스프링 시큐리티 필터(SecurityConfig)가 스프링 필터체인에 등록이 된다.
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)  //secured 어노테이션 활성화, preAuthorize, postAuthorize 어노테이션 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;

    @Bean //해당 메서드의 리턴되는 오브젝트를 IoC로 등록해 준다.
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests()
                .requestMatchers("/user/**").authenticated()  //인증만 되면 들어갈 수 있는 주소
                .requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                //.usernameParameter("username2")   -> html의 login받는 form에서 username을 username2로 바꾸면 PrincipalDetailsService의 username과 매칭이 되지 않는다.
                //그럴때 usernameParameter() 를 이용하여 바꾸어 주면 된다.
                .loginProcessingUrl("/login-process")
        /*
        loginProcessingUrl() :
        "/login"이라는 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해준다.
        => "/login"을 만들지 않아도 된다. security가 대신 해주기 때문에
         */
                .defaultSuccessUrl("/")   //로그인이 완료되면 default인 메인페이지로 가게된다. 또한 특정페이지에서 로그인을 하면 그 특정페이지로 이동한다.
                .and()
                .oauth2Login()
                .loginPage("/login")
        /*
        구글 로그인이 완료된 뒤의 후처리가 필요함.
        1. 코드받기(인증)
        2. 엑세스토큰(권한)
        3. 사용자프로필 정보를 가져옴
        4. 그 정보를 토대로 회원가입을 자동으로 진행시키기도함
        5. 정보가 부족한 경우 ex) 쇼핑몰(이메일, 전화번호, 이름, 아이디)  -> (집주소), 백화점몰 -> (vip등급, 일반등급)
        6. 추가적인 정보가 필요한 경우가 아니라면 4번이 가능하다.
         */
                .failureHandler(oAuth2LoginFailureHandler)
                .userInfoEndpoint()
                .userService(principalOauth2UserService);

        return http.build();
    }
}
