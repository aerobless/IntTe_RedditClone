package ch.hsr.rubik.redditclone;

import java.io.IOException;

import javax.faces.context.FacesContext;

public class UserBean {
	private String name;
	private String password;
	
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password){
		this.password = password;
	}
	
	public void test() {
		System.out.println("Hello world");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
		} catch (IOException anEx) {
			// TODO Auto-generated catch block
			anEx.printStackTrace();
		}
	}

}
