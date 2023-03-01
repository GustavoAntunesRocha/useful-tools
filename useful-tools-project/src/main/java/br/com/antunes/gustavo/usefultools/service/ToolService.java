package br.com.antunes.gustavo.usefultools.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.antunes.gustavo.usefultools.dto.ToolDTO;
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

    public void create(ToolDTO toolDTO) {
        Tool tool = new Tool();
        tool.setTitle(toolDTO.getTitle());
        tool.setDescription(toolDTO.getDescription());
        tool.setLink(toolDTO.getLink());
        tool.setTags(mapTagNamesToEntities(toolDTO.getTagNames()));
        toolRepository.save(tool);
    }

    @Transactional
    public void update(ToolDTO toolDTO) {
        Optional<Tool> toolOptional = toolRepository.findById(toolDTO.getId());
        Tool tool = toolOptional.get();
        tool.setTitle(toolDTO.getTitle());
        tool.setDescription(toolDTO.getDescription());
        tool.setLink(toolDTO.getLink());
        tool.setTags(mapTagNamesToEntities(toolDTO.getTagNames()));
        toolRepository.save(tool);
    }

    public void delete(int id) {
        toolRepository.deleteById(id);
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
}
