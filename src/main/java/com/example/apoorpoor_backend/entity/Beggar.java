package com.example.apoorpoor_backend.beggar.entity;

import com.example.apoorpoor_backend.entity.Timestamped;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity(name = "TB_BEGGAR")
@Getter
public class Beggar extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;


}
