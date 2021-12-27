package com.castrohector.gl.dto;

import com.castrohector.gl.customannotation.EmailConstraint;
import com.castrohector.gl.customannotation.PasswordConstraint;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;


@Data
@Builder
public class UserSignupDTO {

    private String name;

    @EmailConstraint
    private @NonNull String email;

    @PasswordConstraint
    private @NonNull String password;

    private List<PhoneDTO> phones;

}
