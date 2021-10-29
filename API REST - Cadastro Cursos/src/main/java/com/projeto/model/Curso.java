package com.projeto.model;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;



@Data
@Entity
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String nome;
	private byte maxAlunos;
	private int alunosMatriculados;
	private Date inicioCurso;
	private Date fimCurso;
	private String resumoCurso;
	
}