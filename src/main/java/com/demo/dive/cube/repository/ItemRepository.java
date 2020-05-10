package com.demo.dive.cube.repository;

import com.demo.dive.cube.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    public Item findOneByIdAndIsDeletedFalse(Long id);
    public List<Item> findAllByIsDeletedFalse();

    @Query(value = "SELECT MAX(seq.next_val) FROM hibernate_sequence seq",nativeQuery = true)
    Long getHighestId();
}
