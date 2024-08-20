package com.example.Operador.Clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.Operador.Models.Libro;

@FeignClient(name = "Buscador", url = "http://laptop-kmaikrbo:8081/buscador")
public interface LibroClientRest {
	
	@GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Libro> listar();

    @GetMapping("/{id}")
    public Libro detalleId(@PathVariable Long id);
    
    @GetMapping("/titulo")
    @ResponseStatus(HttpStatus.OK)
    public List<Libro> detalleTitulo(@RequestParam(value = "titulo", defaultValue = "") String titulo);
    
    @GetMapping("/isbn")
    public Libro detalleISBN(@RequestParam(value = "isbn", defaultValue = "") String isbn);
    
    @GetMapping("/autor")
    @ResponseStatus(HttpStatus.OK)
    public List<Libro> detalleAutor(@RequestParam(value = "autor", defaultValue = "") String autor);
    
    @GetMapping("/genero")
    @ResponseStatus(HttpStatus.OK)
    public List<Libro> detalleGenero(@RequestParam(value = "genero", defaultValue = "") String genero);
    
    @PostMapping("/crearlibro")
    public Libro crear(@RequestBody Libro libro);
    
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id);
}