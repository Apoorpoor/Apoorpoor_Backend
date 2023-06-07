package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.config.FCMInit;
import com.example.apoorpoor_backend.dto.fcm.RequestDTO;
import com.example.apoorpoor_backend.service.FCMService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class FcmController {


    private final FCMInit init;
    private final FCMService fcmService;

    @PostMapping("/api/fcm")
    public ResponseEntity pushMessage(@RequestBody RequestDTO requestDTO) throws IOException {
        System.out.println(requestDTO.getTargetToken() + " "
                +requestDTO.getTitle() + " " + requestDTO.getBody());


        fcmService.sendMessageTo(
                requestDTO.getTargetToken(),
                requestDTO.getTitle(),
                requestDTO.getBody());
        return ResponseEntity.ok().build();
    }


    @GetMapping("/v1")
    public String v1(){
        init.init();
        return "firebase";
    }
}
