package br.com.antunes.gustavo.usefultools.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.antunes.gustavo.usefultools.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer>{

	Tag findByName(String name);
	
}
