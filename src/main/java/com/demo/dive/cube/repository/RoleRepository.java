package com.demo.dive.cube.repository;

import com.demo.dive.cube.enums.UserType;
import com.demo.dive.cube.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByRoleName(UserType userType);
}
