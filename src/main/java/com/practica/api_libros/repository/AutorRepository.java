package com.practica.api_libros.repository;

import com.practica.api_libros.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.libros")
    List<Autor> findAllWithLibros();

    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.libros WHERE fallecimiento < :fecha")
    List<Autor> findAllByDate(int fecha);
}
