package com.demo.dive.cube.service;

import com.demo.dive.cube.config.exception.BadRequestException;
import com.demo.dive.cube.config.exception.RecordNotFoundException;
import com.demo.dive.cube.config.exception.ServiceException;
import com.demo.dive.cube.dto.AuthenticationRequestDto;
import com.demo.dive.cube.dto.UserDto;
import com.demo.dive.cube.model.Item;
import com.demo.dive.cube.model.User;
import com.demo.dive.cube.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ItemService itemService;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    private UserRepository userRepository;

    public void saveNupdateUser(UserDto userDto) {
        try {
            User user = new User();
            if(userDto.getId() != null) {
                user = userRepository.findById(userDto.getId()).get();
                if(user==null)
                    throw new RecordNotFoundException("User not found");
                userDto.setPassword(user.getPassword());

            }
            else{
                user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            }
            BeanUtils.copyProperties(userDto,user);
            user.setEnabled(true);
            userRepository.save(user);
        }
        catch (Exception e){
            throw new ServiceException(e.getMessage(),e);
        }
    }

    public Boolean authenticate(AuthenticationRequestDto authenticationDto) {
        Boolean flag = false;
        try {
            if(authenticationDto != null) {

                User user = findUserByUsername(authenticationDto.getUsername());

                if (bCryptPasswordEncoder.matches(authenticationDto.getPassword(), user.getPassword())) {

                    List<GrantedAuthority> grantedAuthorities = convertList(new ArrayList<String>() {{
                        add("ROLE_ADMIN");
                    }}, role -> new SimpleGrantedAuthority(role));

                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            user.getEmail(), null, grantedAuthorities);

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    flag = true;
                }
            }
            return flag;
        }
        catch (Exception e){
            throw new AuthenticationServiceException("Authentication error");
        }

    }

    public ModelAndView checkUserAuthenticate(ModelAndView modelAndView) {
        try {
            Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
            if(!authentication.getPrincipal().toString().equalsIgnoreCase("anonymoususer")) {
                modelAndView.addObject("item", new Item());
                modelAndView.addObject("items", itemService.findAll());
                modelAndView.setViewName("item");
            } else{
                modelAndView.setViewName("login");
                modelAndView.addObject("authenticationRequest",new AuthenticationRequestDto());
            }
            return modelAndView;
        }
        catch (Exception e){
            throw new ServiceException(e.getMessage(),e);
        }

        }


    public static <T, U> List<U> convertList(List<T> from, Function<T, U> func) {
        return from.stream().map(func).collect(Collectors.toList());
    }

    public User findUserByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    public UserDto getUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setAddress(user.getAddress());
        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
        userDto.setId(user.getId());
        userDto.setPhoneNumber(user.getPhoneNumber());
        return userDto;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findOne(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUser(Long id){
        User user = userRepository.findById(id).orElse(null);
        if(user!=null){
            userRepository.delete(user);
        }
    }
}
