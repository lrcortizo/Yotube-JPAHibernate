package samuandluis.siyoutube.persistence;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.GenerationType;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	private String nickname;
	private String password;
	private Date birthday = new Date(0);
	
	@OneToOne (mappedBy="user",cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
	private Channel channel = new Channel();
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
	private List<Comment> comments;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
	private List<Like> likes;
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getBirthday(){
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel c) {
		this.channel = c;
	}
	
}
