package com.example.apoorpoor_backend.entity;

import com.example.apoorpoor_backend.entity.Timestamped;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity(name = "TB_BEGGAR")
@Getter
public class Beggar extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;


}