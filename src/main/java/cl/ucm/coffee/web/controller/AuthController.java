package cl.ucm.coffee.web.controller;

import cl.ucm.coffee.persitence.entity.UserEntity;
import cl.ucm.coffee.persitence.entity.UserRoleEntity;
import cl.ucm.coffee.persitence.repository.RolRepository;
import cl.ucm.coffee.persitence.repository.UserRepository;
import cl.ucm.coffee.service.dto.LoginDto;
import cl.ucm.coffee.service.dto.UserDto;
import cl.ucm.coffee.web.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RolRepository rolRepository;

    // @Autowired
    // public AuthController(AuthenticationManager authenticationManager, JwtUtil
    // jwtUtil) {
    // this.authenticationManager = authenticationManager;
    // this.jwtUtil = jwtUtil;
    // }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        System.out.println(loginDto.getUsername());
        String username = loginDto.getUsername();

        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(username);

        if (!userEntityOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
        }
        
        UserEntity userEntity = userEntityOptional.get();

        if (userEntity.getDisabled()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("El usuario está deshabilitado");
        }

        List<String> roles = userEntity.getRoles().stream().map(UserRoleEntity::getRole).toList();

        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
                loginDto.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(login);

        // System.out.println(authentication.isAuthenticated());
        // System.out.println(authentication.getPrincipal());

        String jwt = this.jwtUtil.create(loginDto.getUsername(), roles.get(0));
        Map map = new HashMap<>();
        map.put("token", jwt);
        return ResponseEntity.ok(map);
        // return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
    }

    @PostMapping("/registro")
    public ResponseEntity<String> registro(@RequestBody UserDto userDto) {

        if (userRepository.existsByUsername(userDto.getUsername())) {
            return ResponseEntity.badRequest().body("Nombre de usuario ya existe");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setEmail(userDto.getEmail());
        userEntity.setDisabled(false);
        userEntity.setLocked(false);

        userRepository.save(userEntity);

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUsername(userDto.getUsername());
        userRoleEntity.setRole("CUSTOMER");
        userRoleEntity.setGrantedDate(LocalDateTime.now());

        rolRepository.save(userRoleEntity);

        return ResponseEntity.ok("Usuario registrado correctamente");
    }

    @PutMapping("/{username}/{action}")
    public ResponseEntity<String> updateUserDisabledStatus(@PathVariable String username, @PathVariable String action) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(username);
        
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            if ("disable".equalsIgnoreCase(action)) {
                userEntity.setDisabled(true);
                userRepository.save(userEntity);
                return ResponseEntity.ok("Usuario deshabilitado correctamente");
            } else if ("enable".equalsIgnoreCase(action)) {
                userEntity.setDisabled(false);
                userRepository.save(userEntity);
                return ResponseEntity.ok("Usuario habilitado correctamente");
            } else {
                return ResponseEntity.badRequest().body("Acción no válida. Use 'disable' o 'enable'");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }
}
