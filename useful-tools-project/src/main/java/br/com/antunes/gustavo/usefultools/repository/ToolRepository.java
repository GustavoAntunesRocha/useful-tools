package br.com.antunes.gustavo.usefultools.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.antunes.gustavo.usefultools.model.Tool;

public interface ToolRepository extends JpaRepository<Tool, Integer>{
	
	Set<Tool> findByTagsName(String name);

}
