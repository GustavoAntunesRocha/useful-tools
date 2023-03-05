package br.com.antunes.gustavo.usefultools.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.antunes.gustavo.usefultools.dto.ToolDTO;
import br.com.antunes.gustavo.usefultools.exception.ApiErrorResponse;
import br.com.antunes.gustavo.usefultools.exception.ToolException;
import br.com.antunes.gustavo.usefultools.model.Tool;
import br.com.antunes.gustavo.usefultools.service.ToolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tools")
public class ToolController {

	private final ToolService toolService;

	public ToolController(ToolService toolService) {
		this.toolService = toolService;
	}

	@Operation(description = "Get a tool object passing its ID in the URL", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved a tool!", content = @Content(mediaType = "application/json")) })
	@GetMapping("/{id}")
	public ResponseEntity<?> getToolById(@PathVariable int id) {
		ToolDTO toolDTO;
		try {
			toolDTO = toolService.getToolById(id);
		} catch (ToolException e) {
			// TODO Auto-generated catch block
			return handleCustomException(e);
		}
		return ResponseEntity.ok(toolDTO);
	}

	@Operation(description = "Create Tool object", responses = {
			@ApiResponse(responseCode = "201", description = "Successfully created tool!", content = @Content(mediaType = "application/json")) })
	@PostMapping
	public ResponseEntity<?> createTool(@Valid @RequestBody ToolDTO toolDTO) {
		ToolDTO createdToolDTO = toolService.mapToDTO(toolService.create(toolDTO));
		return new ResponseEntity<String>(toolService.serializeToJson(createdToolDTO), HttpStatus.CREATED);
	}

	@Operation(description = "Update Tool object", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully updated the tool!", content = @Content(mediaType = "application/json")) })
	@PutMapping
	public ResponseEntity<?> updateTool(@RequestBody ToolDTO toolDTO) {
		ToolDTO createdToolDTO;
		try {
			createdToolDTO = toolService.mapToDTO(toolService.update(toolDTO));
			return ResponseEntity.ok(toolService.serializeToJson(createdToolDTO));
		} catch (ToolException e) {
			// TODO Auto-generated catch block
			return handleCustomException(e);
		}
		
	}

	@Operation(description = "Delete Tool object passing its ID in the URL", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully deleted the tool!", content = @Content(mediaType = "application/json")) })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTool(@PathVariable int id) {
		ToolDTO createdToolDTO;
		try {
			createdToolDTO = toolService.mapToDTO(toolService.delete(id));
			return ResponseEntity.ok(toolService.serializeToJson(createdToolDTO));
		} catch (ToolException e) {
			// TODO Auto-generated catch block
			return handleCustomException(e);
		}
	}
	
	@ExceptionHandler(ToolException.class)
	public ResponseEntity<ApiErrorResponse> handleCustomException(ToolException e) {
		LocalDateTime timestamp = LocalDateTime.now();
		ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND.toString(),
				DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(timestamp), e.getMessage());
		return ResponseEntity.badRequest().body(errorResponse);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponse> handleException(MethodArgumentNotValidException ex) {
	    LocalDateTime timestamp = LocalDateTime.now();
	    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

	    String message = "Invalid parameters: ";
	    for (FieldError fieldError : fieldErrors) {
	        message += "{" + fieldError.getField() + "=" + fieldError.getRejectedValue() + "}";
	    }

	    ApiErrorResponse errorResponse = new ApiErrorResponse(
	        HttpStatus.BAD_REQUEST.toString(),
	        DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(timestamp),
	        message
	    );

	    return ResponseEntity.badRequest().body(errorResponse);
	}
	
	@Operation(description = "Get a list of tools given a tag name", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved tools!", content = @Content(mediaType = "application/json")) })
	@GetMapping("/tag/{tagName}")
	public ResponseEntity<?> getToolsByTag(@PathVariable String tagName) {
		List<ToolDTO> toolDTOList = new ArrayList<>();
		try {
			Set<Tool> tools = toolService.getByTag(tagName);
			for (Tool tool : tools) {
				toolDTOList.add(toolService.mapToDTO(tool));
			}
		} catch (ToolException e) {
			// TODO Auto-generated catch block
			return handleCustomException(e);
		}
		return ResponseEntity.ok(toolDTOList);
	}
	
	@Operation(description = "Get a list of tools given a tag name", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved tools!", content = @Content(mediaType = "application/json")) })
	@GetMapping
	public ResponseEntity<?> getAll() {
		List<ToolDTO> toolDTOList = new ArrayList<>();
		try {
			List<Tool> tools = toolService.getAllTools();
			for (Tool tool : tools) {
				toolDTOList.add(toolService.mapToDTO(tool));
			}
		} catch (ToolException e) {
			// TODO Auto-generated catch block
			return handleCustomException(e);
		}
		return ResponseEntity.ok(toolDTOList);
	}

}
