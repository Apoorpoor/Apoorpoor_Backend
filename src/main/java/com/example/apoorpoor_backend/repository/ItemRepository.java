package com.example.apoorpoor_backend.repository;

import com.example.apoorpoor_backend.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findItemByBeggar_IdAndItemNum(Long beggarId, Long itemNum);
}
