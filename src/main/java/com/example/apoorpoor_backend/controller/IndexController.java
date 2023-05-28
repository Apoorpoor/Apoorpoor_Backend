package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.auth.PrincipalDetails;
import com.example.apoorpoor_backend.model.User;
import com.example.apoorpoor_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //View를 return
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/login")
    public @ResponseBody String testLogin(
            Authentication authentication,
            @AuthenticationPrincipal PrincipalDetails userDetails) { //DI의존성 주입
        System.out.println("/test/login =====================");
        //Object타입을 다운캐스팅하여 정보를 가져온다.
        PrincipalDetails principalsDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("authentication" + principalsDetails.getUser());

        //@AuthenticationPrincipal을 통해서 getUser를 할수 있다.
        System.out.println("userDetails = " + userDetails.getUser());

        return "세션 정보 확인하기";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOauthLogin(
            Authentication authentication,
            @AuthenticationPrincipal OAuth2User oauth) { //DI의존성 주입
        System.out.println("/test/oauth/login =====================");
        //Object타입을 다운캐스팅하여 정보를 가져온다.
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("authentication" + oAuth2User.getAttributes());
        System.out.println("OAuth2User : " + oauth.getAttributes());

        return "Oauth 세션 정보 확인하기";
    }

    //localhost:8080/
    //localhost:8080
    @GetMapping({"","/"})
    public String index() {
        // 머스테치 기본폴더 src/main/resources/
        // 뷰리졸버 설정 : templates (prefix), .mustache (suffix) 생략가능
        return "index"; // src/main/resources/templates/index.mustache
        //return "redirect:http://localhost:3000/nickname";

    }

    //OAuth 로그인을 해도 PrincipalDetails
    //일반 로그인을 해도 PrincipalDetails
    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("principalDetails : " + principalDetails.getUser());
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    //SecurityConfig 를 생성후 자동으로 SpringSecurity에 의해 나오던 로그인 화면이 나오지 않게 된다.
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        user.setRole("ROLE_USER");
        System.out.println(user);

        /* 회원가입은 잘되지만 비밀번호는 1234
        => 시큐리티로 로그인 할 수 없다.
        이유는 패스워드가 암호화가 안되었기 때문이다.
         */
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);

        userRepository.save(user);
        return "redirect:/login";
        //redirect: 를 붙이면 /loginForm이라는 함수를 호출한다.
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") //method가 실행되기 전에 수행
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "데이터정보";
    }

    @GetMapping("login/oauth2/code/kakao")
    public void kakaoLogin(@RequestParam String code) {
        System.out.println("code : " + code);
        //return kakaoUserService.kakaoLogin(code, response);
    }


}
