package com.example.lab1.controller;

import com.example.lab1.DTO.GenreDTO;
import com.example.lab1.DTO.MovieGenreDTO;
import com.example.lab1.mapper.MovieGenreMapper;
import com.example.lab1.model.Genre;
import com.example.lab1.model.Movie;
import com.example.lab1.model.MovieGenre;
import com.example.lab1.service.GenreService;
import com.example.lab1.service.MovieGenreService;
import com.example.lab1.service.MovieService;
import com.example.lab1.service.customException.DuplicateResourceException;
import com.example.lab1.service.customException.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/movieGenres")
public class MovieGenreController {
    private final MovieGenreService movieGenreService;
    private final MovieGenreMapper movieGenreMapper;
    private final MovieService movieService;
    private final GenreService genreService;

    @Autowired
    public MovieGenreController(MovieGenreService movieGenreService, MovieGenreMapper movieGenreMapper,
                                MovieService movieService, GenreService genreService) {
        this.movieGenreService = movieGenreService;
        this.movieGenreMapper = movieGenreMapper;
        this.movieService = movieService;
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<MovieGenreDTO>> getAllMovieGenres() {
        List<MovieGenre> movieGenres = movieGenreService.findAll();

        List<MovieGenreDTO> movieGenreDTOs = new ArrayList<>();

        for (MovieGenre movieGenre : movieGenres) {
            try {
                Movie movie = movieGenre.getMovie();
                List<Genre> genres = movieGenreService.findGenresByMovieId(movie.getMovieId());
                movieGenreDTOs.add(movieGenreMapper.toDTO(movie, genres));
            } catch (ResourceNotFoundException e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.ok(movieGenreDTOs);
    }

    @GetMapping("/movie/{movieId}/details")
    public ResponseEntity<MovieGenreDTO> getMovieWithGenres(@PathVariable Long movieId) throws ResourceNotFoundException {
        Movie movie = movieGenreService.findMovieById(movieId);
        List<Genre> genres = movieGenreService.findGenresByMovieId(movieId);
        MovieGenreDTO dto = movieGenreMapper.toDTO(movie, genres);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/movie/{title}")
    public ResponseEntity<MovieGenreDTO> getMovieWithGenresByTitle(@PathVariable String title) throws ResourceNotFoundException {
        Movie movie = movieGenreService.findMovieByTitle(title);
        List<Genre> genres = movieGenreService.findGenresByMovieId(movie.getMovieId());
        MovieGenreDTO dto = movieGenreMapper.toDTO(movie, genres);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<List<MovieGenreDTO>> createMovieGenre(@RequestBody MovieGenreDTO movieGenreDTO) throws DuplicateResourceException {
        List<MovieGenreDTO> createdDTOs = new ArrayList<>();
        List<GenreDTO> genreDTOs = new ArrayList<>(movieGenreDTO.getGenres());

        for (GenreDTO genreDTO : genreDTOs) {
            MovieGenreDTO singleGenreDTO = new MovieGenreDTO();
            singleGenreDTO.setMovieTitle(movieGenreDTO.getMovieTitle());
            singleGenreDTO.setGenres(List.of(genreDTO));

            MovieGenre movieGenre = movieGenreMapper.toEntity(singleGenreDTO, movieService, genreService);
            MovieGenre createdMovieGenre = movieGenreService.createMovieGenre(movieGenre);

            Movie movie = createdMovieGenre.getMovie();
            List<Genre> genres = List.of(createdMovieGenre.getGenre());
            MovieGenreDTO createdDTO = movieGenreMapper.toDTO(movie, genres);
            createdDTOs.add(createdDTO);
        }

        return new ResponseEntity<>(createdDTOs, HttpStatus.CREATED);
    }

    @PutMapping("/{movieGenreId}")
    public ResponseEntity<MovieGenre> updateMovieGenre(@PathVariable Long movieGenreId, @RequestBody MovieGenre movieGenre) throws ResourceNotFoundException {
        MovieGenre updatedMovieGenre = movieGenreService.updateMovieGenre(movieGenreId, movieGenre);
        return ResponseEntity.ok(updatedMovieGenre);
    }
}

