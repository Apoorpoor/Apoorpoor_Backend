package com.example.apoorpoor_backend.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "image")
@Getter
@NoArgsConstructor
public class Image extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
