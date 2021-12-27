package com.castrohector.gl.controller;

import com.castrohector.gl.dto.*;
import com.castrohector.gl.entity.Phone;
import com.castrohector.gl.entity.User;
import com.castrohector.gl.service.UserService;
import com.castrohector.gl.security.Constants;
import com.castrohector.gl.security.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/")
public class UserRest {

    @Autowired
    UserService userService;

    // -------------------Crea nuevo usuario-------------------------------------------
    @PostMapping(value = "sign-up")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserSignupDTO userSignup, BindingResult result) {
        // Valida Password y email
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    this.formatMessage(HttpStatus.BAD_REQUEST.value(), result));
        }

        // Se entiende que el email no se puede repetir en distinos usuarios.
        if (userService.getUserByEmail(userSignup.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    this.formatMessage(HttpStatus.CONFLICT.value(), "Usuario ya existe"));
        }

        User newUser = new User();
        // Java 8
        String encodedPassword = Base64.getEncoder().encodeToString(
                userSignup.getPassword().getBytes(StandardCharsets.UTF_8));

        newUser.setName(userSignup.getName());
        newUser.setEmail(userSignup.getEmail());
        newUser.setPassword(encodedPassword);

        // Java 8
        List<Phone> phones = userSignup.getPhones().stream()
                .map(phone ->
                        Phone.builder()
                                .user(newUser)
                                .number(phone.getNumber())
                                .cityCode(phone.getCitycode())
                                .contryCode(phone.getContrycode())
                                .build())
                .collect(Collectors.toList());

        newUser.setPhones(phones);
        newUser.setCreated(LocalDateTime.now()); // Java 8
        newUser.setLastLogin(null);
        newUser.setToken(JWTUtil.getJWTToken(userSignup.getEmail()));
        newUser.setIsActive(true);

        User userDB = userService.createUser(newUser);

        userDB.getPhones().forEach(phone -> {phone.setUser(newUser);});

        UserFirstLoginDTO response = new UserFirstLoginDTO();

        response.setId(userDB.getId());
        response.setCreated(userDB.getCreated());
        response.setLastLogin(userDB.getLastLogin());
        response.setToken(userDB.getToken());
        response.setIsActive(userDB.getIsActive());

        return  ResponseEntity.status( HttpStatus.CREATED).body(response);
    }

    // -------------------Retorna un usuario------------------------------------------
    @GetMapping(value = "login")
    public ResponseEntity<?> getUser(@RequestHeader HttpHeaders headers) {
        String value = headers.getFirst(HttpHeaders.AUTHORIZATION);
        value = value.replace(Constants.TOKEN_BEARER_PREFIX, "");
        User user  = userService.getUserByToken(value);

        if (null == user) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    this.formatMessage(HttpStatus.NOT_FOUND.value(), "Usuario no encontrado"));
        }

        user.setLastLogin(LocalDateTime.now());
        user.setToken(JWTUtil.getJWTToken(user.getEmail()));

        userService.updateUser(user);

        // TODO Modificar. Se muestra password decodificada
        user.setPassword(new String(Base64.getDecoder().decode(user.getPassword())));

        UserDTO resp = new UserDTO(
                user.getId(), user.getCreated(), user.getLastLogin(), user.getToken(),
                user.getIsActive(), user.getName(), user.getEmail(), user.getPassword(),
                user.getPhones().stream().map(phone ->
                        PhoneDTO.builder()
                                .number(phone.getNumber())
                                .citycode(phone.getCityCode())
                                .contrycode(phone.getContryCode())
                                .build())
                .collect(Collectors.toList()));

        return  ResponseEntity.ok(resp);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDTO> handleAllUncaughtException(
            Exception exception){
        log.error("Unknown error occurred", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                this.formatMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage()));
    }

    private ErrorDTO formatMessage(int codigo, BindingResult result) {
        // Java 8
        List<ErrorDetailDTO> errors = result.getFieldErrors().stream()
                .map(err -> {return ErrorDetailDTO.builder()
                        .timestamp(new Date().getTime())
                        .codigo(codigo)
                        .detail(err.getDefaultMessage())
                        .build();})
                .collect(Collectors.toList());

        ErrorDTO errorMessage = ErrorDTO.builder()
                .error(errors)
                .build();

        return errorMessage;
    }

    private ErrorDTO formatMessage(int codigo, String result) {
        ErrorDTO errorMessage = ErrorDTO.builder()
                .error(Collections.singletonList(ErrorDetailDTO.builder()
                        .timestamp(new Date().getTime())
                        .codigo(codigo)
                        .detail(result)
                        .build()))
                .build();

        return errorMessage;
    }

}
