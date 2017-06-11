package it.polito.ai.project.repo.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity(name="users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String email;
	private String nickname;
	private String password;
	@ManyToOne
	@JoinColumn(name="status_id", insertable = false)
	private Status status;
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional=true)
	private UserProfile profile;
	
	public User() {
	}
		
	public User(String nickname, String email, String password) {
		this.nickname = nickname;
		this.email = email;
		this.password = password;
	}
	
	public long getId() {
		return id;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public UserProfile getProfile() {
		return profile;
	}
	
	public void setProfile(UserProfile profile) {
		this.profile = profile;
		profile.setUser(this);
		profile.setId(this.id);
	}
}
