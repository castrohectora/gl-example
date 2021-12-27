package com.castrohector.gl.repository;

import com.castrohector.gl.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    public User findByEmail(String email);
    public User findByToken(String token);

}
