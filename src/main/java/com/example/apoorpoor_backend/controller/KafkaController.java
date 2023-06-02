//package com.example.apoorpoor_backend.controller;
//
//import com.example.apoorpoor_backend.service.kafka.KafkaProducer;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping(value = "/kafka")
//@Slf4j
//public class KafkaController {
//
//    private final KafkaProducer producer;
//
//    @Autowired
//    KafkaController(KafkaProducer producer){
//        this.producer = producer;
//    }
//
//
//    @PostMapping
//    @ResponseBody
//    public String sendMessage(@RequestParam String message) {
//        log.info("message : {}", message);
//        this.producer.sendMessage(message);
//
//        return "success";
//    }
//}