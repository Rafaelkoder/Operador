package com.example.Operador.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.Operador.Clients.LibroClientRest;
import com.example.Operador.Models.Libro;
import com.example.Operador.Models.Prestamos;
import com.example.Operador.Models.Tabla;
import com.example.Operador.Repositories.LibreriaRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibreriaImpl implements ILibreriaService{

	@Autowired
	LibreriaRepository libreriaRepository;
	@Autowired
	LibroClientRest libroClient;
	
	@Override
	public Optional<Prestamos> findById(Long id) {
		Prestamos p = libreriaRepository.findById(id).get();
		p.setLibros(libroClient.detalleISBN(p.getISBN()));
		return Optional.of(p) ;
	}

	@Override
	public List<Prestamos> findAll() {
		List<Prestamos> lista = libreriaRepository.findAll();
		for(Prestamos p : lista) {
			p.setLibros(libroClient.detalleISBN(p.getISBN()));
		}
		return lista;
	}

	@Override
	public Prestamos save(Prestamos libro) {
		return libreriaRepository.save(libro);
	}

	@Override
	public void eliminarById(Long id) {
		libreriaRepository.delete(libreriaRepository.findById(id).get());
	}

	@Override
	public List<Tabla> tablaGen(String genero) {
		List<Libro> libros = libroClient.detalleGenero(genero);
		List<Tabla> tablaLlena = new ArrayList<>();
		List<Libro> librosTodos = libroClient.listar();
		List<String> generos = new ArrayList<>();
		
		if(genero.equals("todos")) {
			for(Libro libro : librosTodos) {
				generos.add(libro.getGenero());
			}
			generos = generos.stream().distinct().collect(Collectors.toList());
			
			for (int i = 0; i<generos.size(); i++) {
				List<Libro> libroG = libroClient.detalleGenero(generos.get(i));
				Tabla tab = new Tabla();
				tab.setId(Long.valueOf(i));
				tab.setTipo(generos.get(i));
				tab.setCantidad(Double.valueOf(libroG.size()));
				tablaLlena.add(tab);
			}
		} 
		else {
		Tabla tab = new Tabla();
		tab.setId(Long.valueOf(1));
		tab.setTipo(genero);
		tab.setCantidad(Double.valueOf(libros.size()));
		tablaLlena.add(tab);
	}
		return tablaLlena;
	}

	@Override
	public List<Tabla> tablaAutor(String Autor) {
		List<Libro> libros = libroClient.detalleAutor(Autor);
		List<Tabla> tablaLlena = new ArrayList<>();
		List<Libro> librosTodos = libroClient.listar();
		List<String> autores = new ArrayList<>();
		
		if(Autor.equals("todos")) {
			for(Libro libro : librosTodos) {
				autores.add(libro.getAuthor());
			}
			autores = autores.stream().distinct().collect(Collectors.toList());
			
			for (int i = 0; i<autores.size(); i++) {
				List<Libro> libroG = libroClient.detalleAutor(autores.get(i));
				Tabla tab = new Tabla();
				tab.setId(Long.valueOf(i));
				tab.setTipo(autores.get(i));
				tab.setCantidad(Double.valueOf(libroG.size()));
				tablaLlena.add(tab);
			}
		} 
		
		else {
		Tabla tab = new Tabla();
		tab.setId(Long.valueOf(1));
		tab.setTipo(Autor);
		tab.setCantidad(Double.valueOf(libros.size()));
		tablaLlena.add(tab);
	}
		return tablaLlena;
	}

	@Override
	public Prestamos nuevoPrestamo(Libro libro) {
		Prestamos prestamo = new Prestamos();
		List<Prestamos> p = (List <Prestamos>) libreriaRepository.findAll();
		
		prestamo.setId(Long.valueOf(p.size()+1));
		prestamo.setISBN(libro.getISBN());
		prestamo.setFechaPrestamo(Calendar.getInstance().getTime());
		prestamo.setCuota(0.0);
		prestamo.setEstado("Prestado");
		prestamo.setDias(1);
		
		Date[] fechas = prestamo.getFechas();
		fechas[0] = Calendar.getInstance().getTime();
		prestamo.setFechas(fechas);
		
		Prestamos p2 = libreriaRepository.save(prestamo);
		p2.setLibros(libro);
		return p2;
	}

	@Override
	public void eliminarPrestamo(Prestamos prestamo) {
		libroClient.crear(prestamo.getLibro());
		libreriaRepository.delete(prestamo);
	}

	@Override
	public void generarCuota(Prestamos prestamo) {
		int ultimo = prestamo.getDias();
		if(ultimo >= 15) {
			prestamo.setCuota(prestamo.getCuota() + 15);
		}
	}

	@Override
	public Integer numeroLibros(String titulo) {
		return libroClient.detalleTitulo(titulo).size();
	}

	@Override
	public Prestamos findByIsbn(String isbn) {
		return libreriaRepository.findByISBN(isbn);
	}

	@Override
	public void actualizarFechas(List<Prestamos> prestamos) {
		for(Prestamos p : prestamos) {
			
			SimpleDateFormat dia = new SimpleDateFormat("dd-MM-yy");
			
			if(!p.getEstado().equalsIgnoreCase("Libre")) {
			
			String f2 = dia.format(Calendar.getInstance().getTime());	
			List<Date> fechas = Arrays.asList(p.getFechas());
			boolean cambiarF = false;
			int i = 0;
			
			for(Date d : fechas) {
			   if(Objects.nonNull(d)) {
				 String f1 = dia.format(d);
				 i = fechas.indexOf(d);
				 if(!f1.equalsIgnoreCase(f2)) {
					cambiarF = true;
					i = i + 1;
					p.setDias(p.getDias()+1); }
				 }
			   }
			if (cambiarF) {
			Date [] fN = p.getFechas();
			fN[i] = (Date) Calendar.getInstance().getTime();
			p.setFechas(fN);}
			generarCuota(p); 
			}
			libreriaRepository.save(p);
		}
	}
	//SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
	//Date fecha1 = formato.parse(prestamo.getFechas().get(ultimo - 1).toString());
    //Date fecha2 = formato.parse(prestamo.getFechaPrestamo().toString());

}
