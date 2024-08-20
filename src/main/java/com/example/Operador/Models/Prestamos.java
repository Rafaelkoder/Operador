package com.example.Operador.Models;

import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="Prestamos")
public class Prestamos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String ISBN;
    @NotEmpty
    private String estado;
    @Transient
    private Libro libro;
    @NotEmpty
    private Integer dias;
    @NotEmpty
    private Double cuota;
    @NotEmpty
    private Date fechaPrestamo;
    @NotEmpty
    private Date[] fechas;
    


    public Prestamos() {
    	this.fechas = new Date[100];
    }
    

	/**
	 * @return the fechaPrestamo
	 */
	public Date getFechaPrestamo() {
		return fechaPrestamo;
	}



	/**
	 * @param fechaPrestamo the fechaPrestamo to set
	 */
	public void setFechaPrestamo(Date fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}



	/**
	 * @return the fechas
	 */
	public Date[] getFechas() {
		return fechas;
	}



	/**
	 * @param fechas the fechas to set
	 */
	public void setFechas(Date[] fechas) {
		this.fechas = fechas; 
	}

	public void actualizarFechas(int lugar) {
		this.fechas[lugar]= (Date) Calendar.getInstance().getTime(); 
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}



	/**
	 * @return the nombre
	 */
	public String getISBN() {
		return ISBN;
	}



	/**
	 * @param nombre the nombre to set
	 */
	public void setISBN(String isbn) {
		this.ISBN = isbn;
	}



	/**
	 * @return the apellido
	 */
	public String getEstado() {
		return estado;
	}



	/**
	 * @param apellido the apellido to set
	 */
	public void setEstado(String status) {
		this.estado = status;
	}



	/**
	 * @return the libro
	 */
	public Libro getLibro() {
		return libro;
	}



	/**
	 * @param libro the libro to set
	 */
	public void setLibros(Libro libros) {
		this.libro = libros;
	}



	/**
	 * @return the fechaP
	 */
	public Integer getDias() {
		return dias ;
	}



	/**
	 * @param fechaP the fechaP to set
	 */
	public void setDias(Integer fechaP) {
		this.dias = fechaP;
	}

	/**
	 * @return the cuota
	 */
	public Double getCuota() {
		return cuota;
	}

	/**
	 * @param cuota the cuota to set
	 */
	public void setCuota(Double cuota) {
		this.cuota = cuota;
	}
	
	
    
    
}
