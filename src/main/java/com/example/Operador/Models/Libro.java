package com.example.Operador.Models;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Libro {
 
    private Long id;
    
    private String title;
    
    private String author;
    
    private String cover;
    
    private String genero;
    
    private String isbn;
    
    private String sinopsis;
    
    
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return the cover
	 */
	public String getCover() {
		return cover;
	}
	/**
	 * @param cover the cover to set
	 */
	public void setCover(String cover) {
		this.cover = cover;
	}
	/**
	 * @return the genero
	 */
	public String getGenero() {
		return genero;
	}
	/**
	 * @param genero the genero to set
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}
	/**
	 * @return the disponible
	 */
	public String getISBN() {
		return isbn;
	}
	/**
	 * @param disponible the disponible to set
	 */
	public void setISBN(String disponible) {
		this.isbn = disponible;
	}
	/**
	 * @return the sinopsis
	 */
	public String getSinopsis() {
		return sinopsis;
	}
	/**
	 * @param sinopsis the sinopsis to set
	 */
	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}
    
    
    
}
