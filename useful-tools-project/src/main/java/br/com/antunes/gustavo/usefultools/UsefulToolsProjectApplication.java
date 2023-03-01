package br.com.antunes.gustavo.usefultools;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.antunes.gustavo.usefultools.model.Tag;
import br.com.antunes.gustavo.usefultools.model.Tool;
import br.com.antunes.gustavo.usefultools.repository.ToolRepository;
import jakarta.transaction.Transactional;

@SpringBootApplication
public class UsefulToolsProjectApplication implements CommandLineRunner {
	
	@Autowired
	private ToolRepository toolRepository;

	public static void main(String[] args) {
		SpringApplication.run(UsefulToolsProjectApplication.class, args);
	}
	
	@Override
	@Transactional
	public void run(String... args) {

		Set<Tag> tags = new HashSet<>();
		
		tags.add(new Tag("dev"));
		tags.add(new Tag("app"));
		tags.add(new Tag("website"));
		
		Tool tool = new Tool("Githhub", "https://github.com", "Platform to store projects", tags);
		
		toolRepository.save(tool);
		
		
	}

}
