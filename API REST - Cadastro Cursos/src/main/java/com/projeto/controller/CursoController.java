package com.projeto.controller;

import java.util.List;

import java.util.Optional;

import com.projeto.model.Curso;
import com.projeto.repository.CursoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/curso")
public class CursoController {

	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping
	public List<Curso> listar() {
		return cursoRepository.findAll();
	}

	@GetMapping("/{id}")
	public Optional<Curso> listarId(@PathVariable long id) {
		Optional<Curso> optional = cursoRepository.findById(id);
		if (optional.isEmpty())
			return null;
		return Optional.of(optional.get());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public String adicionar(@RequestBody Curso curso) {

		// Não permite cadastro de quantidade de aluno maior que a capacidade máxima
		if (curso.getMaxAlunos() < curso.getAlunosMatriculados()) {
			return "Falha - Quantidade de alunos maior que a capacidade máxima";
		} else if (!(curso.getInicioCurso().before(curso.getFimCurso()))) {
			return "Falha - Dada de inicio está após da data final";
		} // Não permite que a data de inicio seja maior que a data final

		cursoRepository.save(curso);

		return "Cadastrado com sucesso";
	}

	@PutMapping("/{id}")
	public Curso atualizar(@PathVariable long id, @RequestBody Curso curso) {
		Optional<Curso> optional = cursoRepository.findById(id);
		if (optional.isEmpty())
			return null;

		Curso cursoAtt = curso;
		cursoAtt.setId(id);

		return cursoRepository.save(cursoAtt);

	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		cursoRepository.deleteById(id);
	}

	
	

	
	

}
