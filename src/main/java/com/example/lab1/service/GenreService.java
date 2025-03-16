package com.example.lab1.service;

import com.example.lab1.model.Genre;
import com.example.lab1.repository.GenreRepository;
import com.example.lab1.service.customException.DuplicateResourceException;
import com.example.lab1.service.customException.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public Genre findByName(String name) throws ResourceNotFoundException {
        if(genreRepository.findByName(name) == null) {
            throw new ResourceNotFoundException("Genre with name " + name + " not found");
        }
        return genreRepository.findByName(name);
    }

    public Genre save(Genre genre) throws DuplicateResourceException {
        if(genreRepository.findByName(genre.getName()) != null) {
            throw new DuplicateResourceException("Genre with name " + genre.getName() + " already exists");
        }
        return genreRepository.save(genre);
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        if(!genreRepository.existsById(id)) {
            throw new ResourceNotFoundException("Genre with id " + id + " not found");
        }
        genreRepository.deleteById(id);
    }
}