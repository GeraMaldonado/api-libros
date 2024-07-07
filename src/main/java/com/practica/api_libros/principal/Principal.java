package com.practica.api_libros.principal;

import com.practica.api_libros.model.ApiResponse;
import com.practica.api_libros.model.DatosLibro;
import com.practica.api_libros.service.ConsumoAPI;
import com.practica.api_libros.service.ConvierteDatos;

import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner escaner = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraMenu(){
        var opcion = -1;
        while (opcion != 0){
            var menu = """
                    1 - Buscar libro por titulo
                    2 - Lista libros registrados
                    3 - Lista autores registrados
                    4 - Lista autores vivos en un determinado año
                    5 - Lista libros por idioma
                    
                    0 - salir
                    """;
            System.out.println(menu);
            opcion = escaner.nextInt();
            escaner.nextLine();

            switch (opcion){
                case 1:
                    buscarLibroWeb();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }









    private DatosLibro getDatosLibro(){
        System.out.println("Escribe el titulo del libro que deseas buscar");
        var nombreLibro = escaner.nextLine();
        var json = consumoAPI.obtenerDatos(URL + nombreLibro.replace(" ","%20"));
        var apiResponse = conversor.obtenerDatos(json, ApiResponse.class);
        DatosLibro libro = apiResponse.results().get(0);
        return libro;
    }

    private void buscarLibroWeb() {
        DatosLibro datos = getDatosLibro();
        System.out.println("Desde el metodo");
        System.out.println(datos);
    }
}
