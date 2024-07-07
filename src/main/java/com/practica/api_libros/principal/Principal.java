package com.practica.api_libros.principal;

import com.practica.api_libros.model.ApiResponse;
import com.practica.api_libros.model.Autor;
import com.practica.api_libros.model.DatosAutor;
import com.practica.api_libros.model.DatosLibro;
import com.practica.api_libros.model.Libro;
import com.practica.api_libros.repository.LibroRepository;
import com.practica.api_libros.service.ConsumoAPI;
import com.practica.api_libros.service.ConvierteDatos;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {
    private Scanner escaner = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositorio;

    public Principal(LibroRepository repository) {
        this.repositorio = repository;
    }

    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por título
                    2 - Lista libros registrados
                    3 - Lista autores registrados
                    4 - Lista autores vivos en un determinado año
                    5 - Lista libros por idioma
                    
                    0 - salir
                    """;
            System.out.println(menu);
            opcion = escaner.nextInt();
            escaner.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private DatosLibro getDatosLibro() {
        System.out.println("Escribe el título del libro que deseas buscar");
        var nombreLibro = escaner.nextLine();
        var json = consumoAPI.obtenerDatos(URL + nombreLibro.replace(" ", "%20"));
        var apiResponse = conversor.obtenerDatos(json, ApiResponse.class);
        if(apiResponse.results().isEmpty()){
            System.out.println("Libro no encontrado");
            muestraMenu();
        };
        DatosLibro libro = apiResponse.results().get(0);
        return libro;
    }

    private void buscarLibroWeb() {
        DatosLibro datos = getDatosLibro();
        if (datos != null) {
            DatosAutor datosAutor = datos.autorList().get(0);

            Optional<Autor> autorExistente = repositorio.findAutorByNombre(datosAutor.nombre());
            Autor autor;

            if (autorExistente.isPresent()) {
                autor = autorExistente.get();
            } else {
                repositorio.saveAutor(datosAutor.nombre(), datosAutor.nacimiento(), datosAutor.fallecimiento());
                autor = repositorio.findAutorByNombre(datosAutor.nombre()).get();
            }

            Libro libro = new Libro(datos);
            libro.setAutor(autor);
            repositorio.save(libro);
            System.out.println(libro);
        } else {
            System.out.println("No se encontró ningún libro.");
        }
    }
}
