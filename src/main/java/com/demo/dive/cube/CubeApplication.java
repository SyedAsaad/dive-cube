package com.demo.dive.cube;

import com.demo.dive.cube.enums.UserType;
import com.demo.dive.cube.model.Role;
import com.demo.dive.cube.repository.RoleRepository;
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

    @Bean
    public CommandLineRunner addRoles(RoleRepository repo) {
        if(repo.findAll().size()<roleList.size()) {
            repo.deleteAll();
            repo.saveAll(roleList);
        }
        return null;
    }
    @Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }

}
