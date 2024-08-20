package com.example.Operador.Services;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.validation.BindingResult;

import com.example.Operador.Models.Libro;
import com.example.Operador.Models.Prestamos;
import com.example.Operador.Models.Tabla;

public interface ILibreriaService {
	
    Optional<Prestamos> findById(Long id);
	List<Prestamos> findAll();
    Prestamos save (Prestamos libro);
    void eliminarById (Long id);
    
    Prestamos nuevoPrestamo (Libro libro);
    void eliminarPrestamo (Prestamos prestamo);
    void generarCuota(Prestamos prestamo);
    void actualizarFechas(List<Prestamos> prestamos);

    List<Tabla> tablaGen(String genero);
    List<Tabla> tablaAutor(String autor);
    Prestamos findByIsbn (String isbn);
    Integer numeroLibros(String titulo);

}
