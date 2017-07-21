package it.polito.ai.project.repo.entities;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;

import it.polito.ai.project.rest.controllers.ImagesController;

@Entity(name = "images")
@Component
public class Image {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private byte[] value;
	
	@Transient
	private static Environment environment;
	
	@PostConstruct
	public void init() {
	}
	
	@SuppressWarnings("static-access")
	@Autowired
	public void setEnvironment(@Autowired Environment environment) {
	    this.environment = environment;
	}

	public Image() {
	}

	public Image(byte[] value) {
		this.value = value;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getValue() {
		return value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}
	
	public String getUrl() {
		if (id != null) {
			try {
				Link selfLink = ControllerLinkBuilder
						.linkTo(ControllerLinkBuilder.methodOn(ImagesController.class).getImageResource(id))
						.withSelfRel();
				Link rawLink = new Link(selfLink.getHref() + "/raw", "raw");
				return rawLink.getHref();
			} catch (IllegalStateException e) {
				// we are in the websocket environment, no way to use HATEOAS
				// // https://stackoverflow.com/questions/27802786/spring-boot-java-lang-illegalstateexception-when-calling-controllerlinkbuilder
				String port = environment.getProperty("server.port");
				String address = environment.getProperty("server.myaddress");
				return "http://" + address + ":" + port + "/images/" + id + "/raw";
			}
		} else {
			return null;
		}
		
	}

}
