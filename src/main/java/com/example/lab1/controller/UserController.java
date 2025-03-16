package com.example.lab1.controller;

import com.example.lab1.DTO.UserDTO;
import com.example.lab1.model.User;
import com.example.lab1.mapper.UserMapper;
import com.example.lab1.service.UserService;
import com.example.lab1.service.customException.DuplicateResourceException;
import com.example.lab1.service.customException.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userMapper.toDTOList(userService.findAll());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) throws ResourceNotFoundException {
        return userService.findByUsername(username)
                .map(user -> ResponseEntity.ok(userMapper.toDTO(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) throws DuplicateResourceException {
        User user = userMapper.toEntity(userDTO);
        User savedUser = userService.save(user);
        return ResponseEntity.ok(userMapper.toDTO(savedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws ResourceNotFoundException {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}