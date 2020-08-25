package com.demo.dive.cube;

import com.demo.dive.cube.enums.UserType;
import com.demo.dive.cube.model.Role;
import com.demo.dive.cube.model.User;
import com.demo.dive.cube.repository.RoleRepository;
import com.demo.dive.cube.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import javax.servlet.MultipartConfigElement;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CubeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CubeApplication.class, args);
    }

    private static final List<Role> roleList = new ArrayList<Role>(){{add(new Role(UserType.ADMIN));
    add(new Role(UserType.EMPLOYEE)); add(new Role(UserType.INSTRUCTOR));}};

    public static final User adminUser = new User("Admin","admin@cube.com","XYZ Street Bermuda","12345789",Boolean.TRUE,"$2a$10$a0QchRi7NRb39axj2eVVOumPRSkoHB92Isqb3t/7NKtIpNgdf1iSK");

    @Bean
    public CommandLineRunner addRoles(RoleRepository repo,UserRepository userRepository) {
        if(repo.findAll().size()<roleList.size()) {
            repo.deleteAll();
            repo.saveAll(roleList);
        }
        if(userRepository.findAllByRole_RoleName(UserType.ADMIN).size()<=0){
            adminUser.setRole(repo.findByRoleName(UserType.ADMIN));
            userRepository.save(adminUser);
        }
        return null;
    }
    @Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }

}
