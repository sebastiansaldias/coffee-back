package cl.ucm.coffee.web.controller;

import cl.ucm.coffee.persitence.entity.UserEntity;
import cl.ucm.coffee.persitence.repository.UserRepository;
import cl.ucm.coffee.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/obtener")
    public List<UserDto> getUsers() {

        List<UserEntity> users = userRepository.findAll();

        List<UserDto> usersDto = new ArrayList<>();

        for (UserEntity user : users) {
            usersDto.add(
                    new UserDto(
                            user.getUsername(),
                            user.getPassword(),
                            user.getEmail(),
                            user.getDisabled(),
                            user.getRoles().get(0).getRole()
                    )
            );
        }

        return usersDto;

    }

}
