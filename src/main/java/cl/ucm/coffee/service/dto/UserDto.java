package cl.ucm.coffee.service.dto;

import java.util.List;

import cl.ucm.coffee.persitence.entity.UserRoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private String email;
    private  Boolean disabled;
    private String role;

}
