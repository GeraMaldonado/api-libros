package com.practica.api_libros.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private int nacimiento;
    private int fallecimiento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Libro> libros;

    public Autor() {}

    public Autor(String nombre, int nacimiento, int fallecimiento) {
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.fallecimiento = fallecimiento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(int nacimiento) {
        this.nacimiento = nacimiento;
    }

    public int getFallecimiento() {
        return fallecimiento;
    }

    public void setFallecimiento(int fallecimiento) {
        this.fallecimiento = fallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        StringBuilder librosStr = new StringBuilder();
        librosStr.append("[");
        for (int i = 0; i < libros.size(); i++) {
            librosStr.append("\"").append(libros.get(i).getTitle()).append("\"");
            if (i < libros.size() - 1) {
                librosStr.append(", ");
            }
        }
        librosStr.append("]");
        return "\n------Autor--------" +
                "\nNombre: " + nombre +
                "\nNacimiento: " + nacimiento +
                "\nFallecimiento: " + fallecimiento +
                "\nLibros: " + librosStr.toString() +
                "\n------------------";
    }

}
