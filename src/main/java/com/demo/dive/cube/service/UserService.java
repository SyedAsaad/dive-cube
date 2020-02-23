package com.demo.dive.cube.service;

import com.demo.dive.cube.config.exception.BadRequestException;
import com.demo.dive.cube.config.exception.RecordNotFoundException;
import com.demo.dive.cube.config.exception.ServiceException;
import com.demo.dive.cube.dto.AuthenticationRequestDto;
import com.demo.dive.cube.dto.UserDto;
import com.demo.dive.cube.model.User;
import com.demo.dive.cube.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    private UserRepository userRepository;

    public void saveUser(UserDto userDto) {
        try {
            User user = new User();
            if(userDto.getId() != null) {
                user = userRepository.findById(userDto.getId()).get();
                if(user==null)
                    throw new RecordNotFoundException("User not found");

            }
            BeanUtils.copyProperties(userDto,user);
            user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            user.setEnabled(true);
            userRepository.save(user);
        }
        catch (Exception e){
            throw new ServiceException(e.getMessage(),e);
        }


    }

    public void authenticate(AuthenticationRequestDto authenticationDto) {
        if(authenticationDto != null) {

//			Map <String,String> credentials = (Map<String, String>) authentication.getCredentials();
            User user = findUserByUsername(authenticationDto.getUsername());

            if (bCryptPasswordEncoder.matches(authenticationDto.getPassword(),user.getPassword())) {


                List<GrantedAuthority> grantedAuthorities = convertList(new ArrayList<String>(){{add("ROLE_ADMIN");}}, role -> new SimpleGrantedAuthority(role));

                Authentication authentication =  new UsernamePasswordAuthenticationToken(
                        user.getEmail(), null,grantedAuthorities );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new BadRequestException("User is invalid");
            }
        }else {
            throw new UsernameNotFoundException(String.format("No appUser found with username '%s'.", ""));
        }
    }

    public static <T, U> List<U> convertList(List<T> from, Function<T, U> func) {
        return from.stream().map(func).collect(Collectors.toList());
    }

    public User findUserByUsername(String email) {
        return userRepository.findByEmail(email);
    }
}
