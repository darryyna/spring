package com.example.lab1.controller;

import com.example.lab1.DTO.GenreDTO;
import com.example.lab1.mapper.GenreMapper;
import com.example.lab1.service.GenreService;
import com.example.lab1.service.customException.DuplicateResourceException;
import com.example.lab1.service.customException.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;
    private final GenreMapper genreMapper;

    @Autowired
    public GenreController(GenreService genreService, GenreMapper genreMapper) {
        this.genreService = genreService;
        this.genreMapper = genreMapper;
    }

    @GetMapping
    public ResponseEntity<List<GenreDTO>> getAllGenres() {
        return ResponseEntity.ok(genreMapper.toDTOList(genreService.findAll()));
    }

    @GetMapping("/{name}")
    public ResponseEntity<GenreDTO> getGenreByName(@PathVariable String name) throws ResourceNotFoundException {
        var genre = genreService.findByName(name);
        return genre != null ?
                ResponseEntity.ok(genreMapper.toDTO(genre)) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<GenreDTO> createGenre(@RequestBody GenreDTO genreDTO) throws DuplicateResourceException {
        var savedGenre = genreService.save(genreMapper.toEntity(genreDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(genreMapper.toDTO(savedGenre));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) throws ResourceNotFoundException {
        genreService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}