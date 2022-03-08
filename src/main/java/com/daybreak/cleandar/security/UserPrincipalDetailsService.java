package com.daybreak.cleandar.security;

import com.daybreak.cleandar.domain.user.User;
import com.daybreak.cleandar.domain.user.UserDto;
import com.daybreak.cleandar.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public User create(UserDto.Request request) {
        return userRepository.save(User.builder()
                .email(request.getEmail())
                .password(new BCryptPasswordEncoder().encode(request.getPassword()))
                .name(request.getName())
                .build());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        return new UserPrincipal(user);
    }

}