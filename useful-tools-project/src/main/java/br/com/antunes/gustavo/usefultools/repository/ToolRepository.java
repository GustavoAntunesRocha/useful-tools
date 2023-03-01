package br.com.antunes.gustavo.usefultools.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.antunes.gustavo.usefultools.model.Tool;

public interface ToolRepository extends JpaRepository<Tool, Integer>{

}
