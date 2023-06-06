package com.example.apoorpoor_backend.repository.shop;

import com.example.apoorpoor_backend.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findItemByBeggar_IdAndItemNum(Long beggarId, Long itemNum);

    List<Item> findItemsByBeggar_Id(Long beggarId);

    boolean existsDistinctByBeggar_IdAndItemNum(Long beggarId, Long itemNum);
}
