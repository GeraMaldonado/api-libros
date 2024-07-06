package com.practica.api_libros.principal;

import com.practica.api_libros.service.ConsumoAPI;

public class Principal {
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL = "https://gutendex.com/books";

    public void prueba(){
        var json = consumoAPI.obtenerDatos(URL);
        System.out.println(json);
    }
}
