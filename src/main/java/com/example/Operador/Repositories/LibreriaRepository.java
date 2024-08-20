package com.example.Operador.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.Operador.Models.Prestamos;

import feign.Param;

public interface LibreriaRepository extends JpaRepository<Prestamos, Long> {
	
	@Query("SELECT P FROM Prestamos P WHERE P.ISBN = :ISBN")
	Prestamos findByISBN(@Param("ISBN") String ISBN);
	
}
