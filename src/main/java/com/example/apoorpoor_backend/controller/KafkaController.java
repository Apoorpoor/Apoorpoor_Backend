package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.config.KafkaConstants;
import com.example.apoorpoor_backend.dto.ChattingMessage;
import com.example.apoorpoor_backend.repository.ChatMessageHistoryRepository;
import com.example.apoorpoor_backend.service.kafka.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/kafka")
@Slf4j
public class KafkaController {
    private final ChatMessageHistoryRepository chatMessageHistoryRepository;
    private final KafkaTemplate<String, ChattingMessage> kafkaTemplate;
    private final KafkaProducer kafkaProducer;

    @Autowired
    public KafkaController(ChatMessageHistoryRepository chatMessageHistoryRepository,
                           KafkaTemplate<String, ChattingMessage> kafkaTemplate,
                           KafkaProducer kafkaProducer) {
        this.chatMessageHistoryRepository = chatMessageHistoryRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping
    @ResponseBody
    public void message(@RequestBody ChattingMessage message) {
        log.info(message.getMessage());
        chatMessageHistoryRepository.save(message);
        kafkaProducer.send(KafkaConstants.KAFKA_TOPIC, message);
        log.info("message : {}", message);
    }
}