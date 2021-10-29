package com.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long>{

	//Spring JPA já oferece a implementação
	
}
