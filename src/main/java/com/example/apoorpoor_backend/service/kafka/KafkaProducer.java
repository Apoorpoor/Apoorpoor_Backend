package com.example.apoorpoor_backend.service.kafka;

import com.example.apoorpoor_backend.dto.ChattingMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, ChattingMessage> kafkaTemplate;

    public void send(String topic, ChattingMessage chattingMessage) {
        log.info("topic : " + topic);
        log.info("send Message : " + chattingMessage.getMessage());
        kafkaTemplate.send(topic, chattingMessage);
    }
}