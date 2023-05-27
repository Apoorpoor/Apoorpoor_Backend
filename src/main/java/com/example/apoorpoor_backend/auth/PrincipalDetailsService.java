package com.example.apoorpoor_backend.auth;

import com.example.apoorpoor_backend.model.User;
import com.example.apoorpoor_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
시큐리티 설정에서 loginProcessingUrl("/login");
/login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는
loadUserByUsername 함수가 실행된다.
 */

@Service
public class PrincipalDetailsService implements UserDetailsService {
    /*
    로그인form의 로그인 버튼을 클릭하면 -> "/login" 호출 -> 스프링은 IoC에서 UserDetailsService로 등록되어있는 타입을 찾는다.
    -> 따라서 스프링은 PrincipalDetailsService를 찾게됨 -> 찾아지면 바로 loadUserByUsername 함수를 호출한다. -> 그때 form에 넣은 parameter인 username을 가져온다.
     */

    @Autowired
    private UserRepository userRepository;

    //시큐리티 session => Authentication => Userdetails
    //시큐리티 session => Authentication(내부 UserDetails)
    //시큐리티 session(내부 Authentication(내부 UserDetails)
    //함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //User userEntity = userRepository.findByUsername(username);
        Optional<User> findUser = userRepository.findByUsername(username);
       // User user = null;
        if(findUser.isPresent()) {
            //findUser.get()
            return new PrincipalDetails(findUser.get());
        }
        return null;
    }
}