
package com.demo.dive.cube.security;

import com.demo.dive.cube.model.User;
import com.demo.dive.cube.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class CustomAuthenticationProvider  {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

//	@Override
//	public Authentication authenticate(Authentication authentication) {
//		if(null != authentication) {
//
////			Map <String,String> credentials = (Map<String, String>) authentication.getCredentials();
//			User user = findUserByUsername(authentication.getPrincipal().toString());
//
//			if (bCryptPasswordEncoder.matches(authentication.getCredentials().toString(),user.getPassword())) {
//
//
//                List<GrantedAuthority> grantedAuthorities = convertList(new ArrayList<String>(){{add("ROLE_ADMIN");}}, role -> new SimpleGrantedAuthority(role));
//
//				return new UsernamePasswordAuthenticationToken(
//						user.getEmail(), null,grantedAuthorities );
//			} else {
//				return null;
//			}
//		}else {
//			throw new UsernameNotFoundException(String.format("No appUser found with username '%s'.", ""));
//		}
//	}

//	@Override
//	public boolean supports(Class<?> authentication) {
//		return authentication.equals(
//				UsernamePasswordAuthenticationToken.class);
//	}

    public static <T, U> List<U> convertList(List<T> from, Function<T, U> func) {
        return from.stream().map(func).collect(Collectors.toList());
    }

	public User findUserByUsername(String email) {
		return userRepository.findByEmail(email);
	}
}

