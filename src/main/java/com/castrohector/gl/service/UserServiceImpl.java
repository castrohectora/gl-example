package com.castrohector.gl.service;

import com.castrohector.gl.entity.User;
import com.castrohector.gl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) { return userRepository.save(user); }

    @Override
    public User updateUser(User user) {
        User userDB = getUser(user.getId());

        if (userDB == null){
            return null;
        }
        userDB.setLastLogin(LocalDateTime.now());
        userDB.setToken(user.getToken());

        return userRepository.save(userDB);
    }

    @Override
    public User getUser(String id) { return userRepository.findById(id).orElse(null); }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserByToken(String token) {
        return userRepository.findByToken(token);
    }

}
