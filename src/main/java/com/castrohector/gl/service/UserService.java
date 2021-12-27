package com.castrohector.gl.service;

import com.castrohector.gl.entity.User;


public interface UserService {

    public User createUser(User user);
    public User updateUser(User user);

    public User getUser(String id);
    public User getUserByEmail(String email);
    public User getUserByToken(String token);

}
