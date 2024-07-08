package cl.ucm.coffee.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cl.ucm.coffee.persitence.entity.UserEntity;
import cl.ucm.coffee.persitence.repository.UserRepository;
import cl.ucm.coffee.service.dto.UserDto;

@RestController
@RequestMapping("/api/usuario")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
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

    @GetMapping("/prueba")
    public String prueba() {
        return new String("hola");
    }
    

}
