package br.com.antunes.gustavo.usefultools.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.antunes.gustavo.usefultools.dto.ToolDTO;
import br.com.antunes.gustavo.usefultools.service.ToolService;

@RestController
@RequestMapping("/tools")
public class ToolController {

    private final ToolService toolService;

    public ToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToolDTO> getToolById(@PathVariable int id) {
        ToolDTO toolDTO = toolService.getToolById(id);
        if (toolDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toolDTO);
    }

    @PostMapping
    public ResponseEntity<ToolDTO> createTool(@RequestBody ToolDTO toolDTO) {
        ToolDTO createdToolDTO = toolService.mapToDTO(toolService.create(toolDTO));
        return ResponseEntity.ok(createdToolDTO);
    }

    @PutMapping
    public ResponseEntity<ToolDTO> updateTool(@RequestBody ToolDTO toolDTO) {
    	ToolDTO createdToolDTO = toolService.mapToDTO(toolService.update(toolDTO));
        return ResponseEntity.ok(createdToolDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTool(@PathVariable int id) {
        toolService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
