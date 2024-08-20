package com.example.Operador.Controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.Operador.Models.Libro;
import com.example.Operador.Models.Prestamos;
import com.example.Operador.Models.Tabla;
import com.example.Operador.Services.ILibreriaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/operador")
public class LibreriaController {
	
	@Autowired
	ILibreriaService iLibreriaService;
	
	 @GetMapping
	    @ResponseStatus(HttpStatus.OK)
	    public ResponseEntity<List<Prestamos>> listar(){
		  //iLibreriaService.actualizarFechas(iLibreriaService.findAll());
	        return ResponseEntity.ok(iLibreriaService.findAll());
	    }
	 
	 @GetMapping("/genero")
	    @ResponseStatus(HttpStatus.OK)
	    public ResponseEntity<List<Tabla>> listarGenero(@RequestParam(value = "genero", defaultValue = "") String genero){
	        return ResponseEntity.ok(iLibreriaService.tablaGen(genero));
	    }
	 
	 @GetMapping("/autor")
	    @ResponseStatus(HttpStatus.OK)
	    public ResponseEntity<List<Tabla>> listarAutor(@RequestParam(value = "autor", defaultValue = "") String autor){
	        return ResponseEntity.ok(iLibreriaService.tablaAutor(autor));
	    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalleId(@PathVariable Long id){
        Optional<Prestamos> prestamo = iLibreriaService.findById(id);
        if (prestamo.isPresent()) {
            return ResponseEntity.ok(prestamo.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/crearprestamo")
    public ResponseEntity<?> crear(@Valid @RequestBody Prestamos prestamo, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        Prestamos prestamoDB = iLibreriaService.save(prestamo);
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamoDB);
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/pedirPres")
    public ResponseEntity<?> pedir(@Valid @RequestBody Libro libro, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        Prestamos prestamoDB = iLibreriaService.nuevoPrestamo(libro);
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamoDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Prestamos libro, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validar(result);
        }
        Optional<Prestamos> o = iLibreriaService.findById(id);
        if (o.isPresent()) {
            Prestamos prestamoDB = o.get();
            prestamoDB.setISBN(libro.getISBN());
            prestamoDB.setEstado(libro.getEstado());
            prestamoDB.setLibros((Objects.nonNull(libro.getLibro()))? libro.getLibro(): null);
            prestamoDB.setCuota(libro.getCuota());
            return ResponseEntity.status(HttpStatus.CREATED).body(iLibreriaService.save(prestamoDB));
        }
        return ResponseEntity.notFound().build();
    }
    

    @PutMapping("/renovar")
    public ResponseEntity<?> renovarPrestamo(@Valid @RequestBody Libro libro, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        Prestamos o = iLibreriaService.findByIsbn(libro.getISBN());
        if (Objects.nonNull(o)) {
    		o.setFechaPrestamo(Calendar.getInstance().getTime());
    		o.setCuota(0.0);
    		o.setEstado("Renovado");
            return ResponseEntity.status(HttpStatus.CREATED).body(iLibreriaService.save(o));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Prestamos> o = iLibreriaService.findById(id);
        if (o.isPresent()) {
            iLibreriaService.eliminarById(o.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/regresar")
    public ResponseEntity<?> terminarPrestamo(@Valid @RequestBody Libro libro, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        Prestamos o = iLibreriaService.findByIsbn(libro.getISBN());
        if (Objects.nonNull(o)) {
    		o.setFechaPrestamo(Calendar.getInstance().getTime());
    		o.setCuota(0.0);
    		o.setEstado("Libre");
    		o.setFechas(new Date[100]);
    		o.setDias(0);
            return ResponseEntity.status(HttpStatus.CREATED).body(iLibreriaService.save(o));
        }
        return ResponseEntity.notFound().build();
    }
    
   
    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
