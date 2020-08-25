package com.demo.dive.cube.service;

import com.demo.dive.cube.config.exception.BadRequestException;
import com.demo.dive.cube.config.exception.RecordNotFoundException;
import com.demo.dive.cube.config.exception.ServiceException;
import com.demo.dive.cube.dto.AuthenticationRequestDto;
import com.demo.dive.cube.dto.UserDto;
import com.demo.dive.cube.enums.UserType;
import com.demo.dive.cube.model.Item;
import com.demo.dive.cube.model.Role;
import com.demo.dive.cube.model.User;
import com.demo.dive.cube.repository.RoleRepository;
import com.demo.dive.cube.repository.UserRepository;
import org.modelmapper.internal.util.Lists;
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
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ItemService itemService;

    @Autowired
    private RoleRepository roleRepository;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    private UserRepository userRepository;

    public void saveNupdateUser(UserDto userDto, UserType userType) {
        try {
            User user = new User();
            if(userDto.getId() != null) {
                user = userRepository.findById(userDto.getId()).get();
                if(user==null)
                    throw new RecordNotFoundException("User not found");
                userDto.setPassword(user.getPassword());

            }
            else{
                userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            }
            BeanUtils.copyProperties(userDto,user);
            user.setRole(roleRepository.findByRoleName(userType));
            user.setShift(userDto.getShift());
            user.setEnabled(true);
            userRepository.save(user);
        }
        catch (Exception e){
            throw new ServiceException(e.getMessage(),e);
        }
    }

    public String login(AuthenticationRequestDto authenticationRequestDto, Model model) {
        if(authenticate(authenticationRequestDto)){
//            UserType role = findUserByUsername(authenticationRequestDto.getUsername()).getRole().getRoleId();
                return "redirect:/dashboard";
        }
        else{
            model.addAttribute("loginError", true);
            return "login.html";
        }

    }

    public Boolean authenticate(AuthenticationRequestDto authenticationDto) {
        Boolean flag = false;
        try {
            if(authenticationDto != null) {

                User user = findUserByUsername(authenticationDto.getUsername());

                if (bCryptPasswordEncoder.matches(authenticationDto.getPassword(), user.getPassword())) {

                    if(!user.getRole().getRoleName().equals(UserType.ADMIN)) {

                        LocalTime now = LocalTime.now();
                        LocalTime shiftStartTime = LocalTime.parse(user.getShift().getShiftStart());
                        LocalTime shiftEndTime = LocalTime.parse(user.getShift().getShiftEnd());
                        //Validating Shift of Employee & Instructor
                        if(!((now.equals(shiftStartTime)|| now.isAfter(shiftStartTime)) && (now.equals(shiftEndTime) || now.isBefore(shiftEndTime)))){
                            return false;
                        }
                    }

                    List<GrantedAuthority> grantedAuthorities = convertList(new ArrayList<String>() {{
                        add(user.getRole().getRoleName().name());
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

    public String checkUserAuthenticate(ModelAndView modelAndView) {
        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            if(!authentication.getPrincipal().toString().equalsIgnoreCase("anonymoususer")) {
                return "redirect:/";

            } else{
                return "login.html";

            }
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
        userDto.setName(user.getName());
        userDto.setAddress(user.getAddress());
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setShift(user.getShift());
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

    public List<User> findUserByRole(){

        return userRepository.findAllByRole_RoleName(UserType.ADMIN);
    }
}
