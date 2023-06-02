//package com.example.apoorpoor_backend.service.kafka;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//
//@Service
//@Slf4j
//public class KafkaConsumer {
//
//    private final SimpMessagingTemplate Template;
//
//    @Autowired
//    public KafkaConsumer(SimpMessagingTemplate messagingTemplate) {
//        this.Template = messagingTemplate;
//    }
//
//    @KafkaListener(topics = "myapoorpoor", groupId = "foo")
//    public void consume(String message) throws IOException {
//        log.info("Consumed message : {}", message);
//        Template.convertAndSend("/topic", message);
//    }
//
//}
