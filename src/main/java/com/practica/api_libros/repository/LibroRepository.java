package com.practica.api_libros.repository;

import com.practica.api_libros.model.Autor;
import com.practica.api_libros.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    @Query("SELECT a FROM Autor a WHERE a.nombre = :nombre")
    Optional<Autor> findAutorByNombre(@Param("nombre") String nombre);

    @Query("SELECT l FROM Libro l WHERE l.title = :title")
    Optional<Libro> findLibroByTitle(@Param("title") String title);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO autores (nombre, nacimiento, fallecimiento) VALUES (:nombre, :nacimiento, :fallecimiento)", nativeQuery = true)
    int saveAutor(@Param("nombre") String nombre, @Param("nacimiento") int nacimiento, @Param("fallecimiento") int fallecimiento);

    @Query("SELECT a FROM Autor a WHERE a.nombre = :nombre AND a.nacimiento = :nacimiento AND a.fallecimiento = :fallecimiento")
    Optional<Autor> findAutorByDetails(@Param("nombre") String nombre, @Param("nacimiento") int nacimiento, @Param("fallecimiento") int fallecimiento);

    @Query("SELECT a FROM Autor a")
    List<Autor> llamarAutoresRegistrados();

    @Query("SELECT l FROM Libro l WHERE l.idioma = :idioma ")
    List<Libro> librosPorIdioima(String idioma);
}
