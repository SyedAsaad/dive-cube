package com.demo.dive.cube.repository;

import com.demo.dive.cube.enums.UserType;
import com.demo.dive.cube.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

    List<User> findAllByRole_RoleName(UserType userType);
}
