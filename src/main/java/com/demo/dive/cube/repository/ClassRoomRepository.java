package com.demo.dive.cube.repository;

import com.demo.dive.cube.enums.ClassSection;
import com.demo.dive.cube.model.BaseEntity;
import com.demo.dive.cube.model.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom,Long> {
    public ClassRoom findOneByIdAndIsDeletedFalse(Long id);
    public List<ClassRoom> findAllByIsDeletedFalse();
}
