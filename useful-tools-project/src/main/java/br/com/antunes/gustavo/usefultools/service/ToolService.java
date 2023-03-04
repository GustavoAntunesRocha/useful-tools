package br.com.antunes.gustavo.usefultools.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.antunes.gustavo.usefultools.dto.ToolDTO;
import br.com.antunes.gustavo.usefultools.exception.ToolException;
import br.com.antunes.gustavo.usefultools.model.Tag;
import br.com.antunes.gustavo.usefultools.model.Tool;
import br.com.antunes.gustavo.usefultools.repository.TagRepository;
import br.com.antunes.gustavo.usefultools.repository.ToolRepository;
import jakarta.transaction.Transactional;

@Service
public class ToolService {
    @Autowired
    private ToolRepository toolRepository;

    @Autowired
    private TagRepository tagRepository;

    public Tool create(ToolDTO toolDTO) {
        Tool tool = new Tool();
        tool.setTitle(toolDTO.getTitle());
        tool.setDescription(toolDTO.getDescription());
        tool.setLink(toolDTO.getLink());
        tool.setTags(mapTagNamesToEntities(toolDTO.getTagNames()));
        toolRepository.save(tool);
        return tool;
    }

    @Transactional
    public Tool update(ToolDTO toolDTO) {
        Optional<Tool> toolOptional = toolRepository.findById(toolDTO.getId());
        if(toolOptional.isEmpty()) {
        	throw new ToolException("Tool with the id '" + toolDTO.getId() +"' not found in the database!");
        }
        Tool tool = toolOptional.get();
        tool.setTitle(toolDTO.getTitle());
        tool.setDescription(toolDTO.getDescription());
        tool.setLink(toolDTO.getLink());
        tool.setTags(mapTagNamesToEntities(toolDTO.getTagNames()));
        toolRepository.save(tool);
        return tool;
    }

    public Tool delete(int id) {
    	Optional<Tool> toolOptional = toolRepository.findById(id);
    	if(toolOptional.isEmpty()) {
        	throw new ToolException("Tool with the id '" + id +"' not found in the database!");
        }
        toolRepository.deleteById(id);
        return toolOptional.get();
    }

    private Set<Tag> mapTagNamesToEntities(List<String> tagNames) {
        Set<Tag> tags = new HashSet<>();
        for (String tagName : tagNames) {
            Tag tag = tagRepository.findByName(tagName);
            if (tag == null) {
                tag = new Tag();
                tag.setName(tagName);
                tagRepository.save(tag);
            }
            tags.add(tag);
        }
        return tags;
    }

	public ToolDTO getToolById(int id) {
		Optional<Tool> toolOptional = toolRepository.findById(id);
		if(toolOptional.isEmpty()) {
			throw new ToolException("Tool with the id '" + id +"' not found in the database!");
		}
        Tool tool = toolOptional.get();
		return mapToDTO(tool);
	}
	
	private List<String> mapTags(Set<Tag> tags) {
	    return tags.stream().map(Tag::getName).collect(Collectors.toList());
	}
	
	public ToolDTO mapToDTO(Tool tool) {
	    ToolDTO dto = new ToolDTO();
	    dto.setId(tool.getId());
	    dto.setTitle(tool.getTitle());
	    dto.setDescription(tool.getDescription());
	    dto.setLink(tool.getLink());
	    dto.setTagNames(mapTags(tool.getTags()));
	    return dto;
	}
	
	public String serializeToJson(ToolDTO toolDTO) {
	    try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	        return objectMapper.writeValueAsString(toolDTO);
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to serialize ToolDTO to JSON", e);
	    }
	}

}
