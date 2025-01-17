package com.practica.api_libros.principal;

import com.practica.api_libros.model.ApiResponse;
import com.practica.api_libros.model.Autor;
import com.practica.api_libros.model.DatosAutor;
import com.practica.api_libros.model.DatosLibro;
import com.practica.api_libros.model.Libro;
import com.practica.api_libros.repository.AutorRepository;
import com.practica.api_libros.repository.LibroRepository;
import com.practica.api_libros.service.ConsumoAPI;
import com.practica.api_libros.service.ConvierteDatos;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {
    private Scanner escaner = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository librosRepositorio;
    private AutorRepository autorRepositorio;
    private List<Libro> libros;
    private List<Autor> autores;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.librosRepositorio = libroRepository;
        this.autorRepositorio = autorRepository;
    }

    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \n----------------
                    Elija la opción a través de su número:
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
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    autoresVivosAntesDeFecha();
                    break;
                case 5:
                    listaDeLibrosPorIdioma();
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
        try {
            System.out.println("Escribe el título del libro que deseas buscar");
            var nombreLibro = escaner.nextLine();
            var json = consumoAPI.obtenerDatos(URL + nombreLibro.replace(" ", "%20"));
            var apiResponse = conversor.obtenerDatos(json, ApiResponse.class);
            DatosLibro libro = apiResponse.results().get(0);
            Optional<Libro> libroExistente = librosRepositorio.findLibroByTitle(libro.titulo());
            if (libroExistente.isPresent()) {
                System.out.println("El libro ya existe en la base de datos y no se puede ingresar dos veces.");
                return null;
            }
            return libro;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Libro no encontrado");
            return null;
        }
    }

    private void buscarLibroWeb() {
        DatosLibro datos = getDatosLibro();
        if (datos == null) {
            return;
        }
        DatosAutor datosAutor = datos.autorList().get(0);
        Optional<Autor> autorExistente = librosRepositorio.findAutorByNombre(datosAutor.nombre());
        Autor autor;

        if (autorExistente.isPresent()) {
            autor = autorExistente.get();
        } else {
            librosRepositorio.saveAutor(datosAutor.nombre(), datosAutor.nacimiento(), datosAutor.fallecimiento());
            autor = librosRepositorio.findAutorByDetails(datosAutor.nombre(), datosAutor.nacimiento(), datosAutor.fallecimiento()).get();
        }
        Libro libro = new Libro(datos);
        libro.setAutor(autor);
        librosRepositorio.save(libro);
        System.out.println(libro);
    }

    public void listarLibrosRegistrados() {
        libros = librosRepositorio.findAll();
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros registrados.");
        } else {
            libros.forEach(System.out::println);
        }
    }

    public void listarAutoresRegistrados() {
        autores = autorRepositorio.findAllWithLibros();
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores registrados.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    public void autoresVivosAntesDeFecha() {
        System.out.println("Ingrese el año: ");
        var fecha = escaner.nextInt();
        autores = autorRepositorio.findAllByDate(fecha);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año especificado.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    public void listaDeLibrosPorIdioma() {
        var idiomasTexto = """
                es - Español
                en - Inglés
                fr - Francés
                pt - Portugués
                """;
        System.out.println("Ingrese el idioma para buscar los libros: \n" + idiomasTexto);
        var idioma = escaner.nextLine();
        libros = librosRepositorio.librosPorIdioima(idioma);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma especificado.");
        } else {
            libros.forEach(System.out::println);
        }
    }
}
