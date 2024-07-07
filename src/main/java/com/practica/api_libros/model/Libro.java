package com.practica.api_libros.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;


    private String idioma;

    private Long descargas;

    public Libro() {}

    public Libro(DatosLibro datosLibro) {
        this.title = datosLibro.titulo();
        this.idioma = datosLibro.idioma().get(0);
        this.descargas = datosLibro.descargas();
    }

    public Libro(DatosLibro datosLibro, Autor autor) {
        this(datosLibro);
        this.autor = autor;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Long getDescargas() {
        return descargas;
    }

    public void setDescargas(Long descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return "\n------LIBRO--------"+
                "\nTitulo: " + title +
               "\nAutor: " + autor.getNombre() +
               "\nIdioma: " + idioma +
               "\ndescargas: " + descargas +
                "\n------------------";
    }
}
