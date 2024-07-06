package com.practica.api_libros.principal;

import com.practica.api_libros.model.ApiResponse;
import com.practica.api_libros.model.DatosLibro;
import com.practica.api_libros.service.ConsumoAPI;
import com.practica.api_libros.service.ConvierteDatos;

import java.util.List;

public class Principal {
    private ConsumoAPI consumoAPI = new ConsumoAPI();
//    private final String URL = "https://gutendex.com/books";
    private final String URL ="https://gutendex.com/books/?search=Hyde%20Stevenson";
    private ConvierteDatos conversor = new ConvierteDatos();

    public void prueba(){
        var json = consumoAPI.obtenerDatos(URL);


        var apiResponse = conversor.obtenerDatos(json, ApiResponse.class);
        DatosLibro primerLibro = apiResponse.results().get(0);

        System.out.println(primerLibro);

    }
}
