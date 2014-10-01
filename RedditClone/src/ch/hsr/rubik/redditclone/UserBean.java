package ch.hsr.rubik.redditclone;
import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="userBean")
@SessionScoped
public class UserBean {
	private String username;
	private String password;
	
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
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("logout.xhtml");
			} catch (IOException anEx) {
				// TODO Auto-generated catch block
				anEx.printStackTrace();
			}
		} else {
			System.out.println("user not found");
		}
		return "ok";
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
	

}
