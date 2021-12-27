package com.castrohector.gl.test

import com.castrohector.gl.entity.User
import com.castrohector.gl.repository.UserRepository
import com.castrohector.gl.service.UserService
import com.castrohector.gl.service.UserServiceImpl
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime


class UserServiceSpec extends Specification {

    UserService userService
    UserRepository userRepository
    
    @Shared
    User user
    
    def setup() {
        userRepository = Mock()
        userService = new UserServiceImpl(userRepository)
        user = new User()
        user.with {
            id = "1-2-3"
            name = "Prueba1"
            email = "parueba1@mail.com"
            password = "wgfeR33dds"
            created = LocalDateTime.now()
            lastLogin = LocalDateTime.now()
            token = "token222333444555"
            isActive = Boolean.TRUE
            phones = new ArrayList<>()
        }
    }
    
    def "User Service call repository save when entity has valid values"() {
        when: "service create user is called"
        userService.createUser(user)
        
        then: "repository save method should be called once"
        1 * userRepository.save(user)
    }
    
    //studentRepository.findById(id)
    //studentService.getStudentById(id)
    def "Find a user by token"() {
        given: "a stubed respository and a service instance"
        userRepository = Stub()
        userService = new UserServiceImpl(userRepository)

        and: "setup the return when call the findByToken method with token token222333444555"
        userRepository.findByToken("token222333444555") >> new User().with {
            id = "1-2-3"
            name = "Prueba1"
            email = "parueba1@mail.com"
            password = "wgfeR33dds"
            created = LocalDateTime.now()
            lastLogin = LocalDateTime.now()
            token = "token222333444555"
            isActive = Boolean.TRUE
            phones = new ArrayList<>()
        }

        when: "should return a predefined User"
        user = userService.getUserByToken("token222333444555")

        then: "the user should contain the expected values"
        user.with {
            id        == "1-2-3"
            name      == "Prueba1"
            email     == "parueba1@mail.com"
            password  == "wgfeR33dds"
            created   == LocalDateTime.now()
            lastLogin == LocalDateTime.now()
            token     == "token222333444555"
            isActive  == Boolean.TRUE
            phones    == new ArrayList<>()
        }
    }
    
}

