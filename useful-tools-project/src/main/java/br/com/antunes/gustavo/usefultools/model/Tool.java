package br.com.antunes.gustavo.usefultools.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Tool {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	private String title;
	
	private String link;
	
	private String description;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = jakarta.persistence.CascadeType.ALL)
	private Set<Tag> tags;
	
	public Tool() {}

	public Tool(String title, String link, String description, Set<Tag> tags) {
		super();
		this.title = title;
		this.link = link;
		this.description = description;
		this.tags = tags;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
	
	

}
