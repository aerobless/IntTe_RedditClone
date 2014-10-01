package ch.hsr.rubik.redditclone;
import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name="userBean")
@SessionScoped
public class UserBean {
	private String username; //req. for equal
	private String email;
	private String password; //req. for equal
	private String passwordConfirm;
	
	@ManagedProperty(value="#{serverManager}")
	private ServerManager manager;
	
	public final void setManager(ServerManager aManager) {
		manager = aManager;
	}
	public String getName(){
		return username;
	}
	public void setName(String name){
		this.username = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password){
		this.password = password;
	}

	public final String getEmail() {
		return email;
	}

	public final void setEmail(String aEmail) {
		email = aEmail;
	}

	public final String getPasswordConfirm() {
		return passwordConfirm;
	}

	public final void setPasswordConfirm(String aPasswordConfirm) {
		passwordConfirm = aPasswordConfirm;
	}
	
	public UserBean() {
		super();
	}

	public UserBean(String aName, String aPassword) {
		super();
		username = aName;
		password = aPassword;
	}
	
	public String login() {
		if(manager.containsUser(this)){
			System.out.println("user found");
			redirect("logout.xhtml");
		} else {
			System.out.println("user not found");
		}
		
		//Returning a string is required by methods called from a jsp.
		return "ok";
	}
	
	public String register(){
		if(verifyRegistrationInput()){
			manager.addUser(this);
			redirect("logout.xhtml");
			return "ok";
		}
		return "nok";
	}
	
	private boolean verifyRegistrationInput() {
		return password.equals(passwordConfirm) && email.contains("@");
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserBean other = (UserBean) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	private void redirect(String pagename){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(pagename);
		} catch (IOException anEx) {
			// TODO Auto-generated catch block
			anEx.printStackTrace();
		}
	}
	
	public String logout(){
		((HttpSession) FacesContext.getCurrentInstance()
				   .getExternalContext().getSession(false)).invalidate();
		redirect("login.xhtml");
		return "ok";
	}
}
