package br.com.antunes.gustavo.usefultools.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.antunes.gustavo.usefultools.model.Tag;
import br.com.antunes.gustavo.usefultools.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TagService {

	private final TagRepository tagRepository;

	public TagService(TagRepository tagRepository) {
		this.tagRepository = tagRepository;
	}

	public List<Tag> getAllTags() {
		return tagRepository.findAll();
	}

	public Tag getTagById(int id) {
		return tagRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Tag not found with id: " + id));
	}

	public Tag createTag(Tag tag) {
		return tagRepository.save(tag);
	}

	public Tag searchTagByName(String keyword) {
		return tagRepository.findByName(keyword);
	}
}
