<<<<<<< HEAD
//package com.example.apoorpoor_backend.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//
//@Configuration
//public class RedisConfig {
//
//    @Value("${spring.data.redis.host}")
//    private String host;
//
//    @Value("${spring.data.redis.port}")
//    private int port;
//
//
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory(
//                new RedisStandaloneConfiguration(host, port)
//        );
//    }
//}
=======
// package com.example.apoorpoor_backend.config;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.redis.connection.RedisConnectionFactory;
// import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
// import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

// @Configuration
// public class RedisConfig {

//     @Value("${spring.data.redis.host}")
//     private String host;

//     @Value("${spring.data.redis.port}")
//     private int port;



//     @Bean
//     public RedisConnectionFactory redisConnectionFactory() {
//         return new LettuceConnectionFactory(
//                 new RedisStandaloneConfiguration(host, port)
//         );
//     }
// }
>>>>>>> fda81ff2a614e17863b85aa4657a2a91a1b11775
