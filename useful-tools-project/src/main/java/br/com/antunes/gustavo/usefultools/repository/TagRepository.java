package br.com.antunes.gustavo.usefultools.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.antunes.gustavo.usefultools.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer>{

	Tag findByName(String name);

	List<Tag> findByNameContainingIgnoreCase(String keyword);
	
}
