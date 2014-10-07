package ch.hsr.rubik.redditclone;
import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpSession;

@ManagedBean(name="userBean")
@SessionScoped
public class UserBean implements Serializable{
	private static final long serialVersionUID = 1691077872888494747L;
	private String username; //req. for equal
	private String email;
	private String password; //req. for equal
	private String passwordConfirm;
	
	//Switches
	private boolean loginRequired;
	private enum InfoState {WELCOME, REGISTER, SUBMITPOST};
	private InfoState infoBoxCurrentState;

	
	@ManagedProperty(value="#{serverManager}")
	private ServerManager manager;
	
	public final void setManager(ServerManager aManager) {
		manager = aManager;
	}
	public String getUsername(){
		return username;
	}
	public void setUsername(String name){
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
		loginRequired = true;
		infoBoxCurrentState = InfoState.WELCOME;
	}

	public UserBean(String aName, String aPassword) {
		super();
		username = aName;
		password = aPassword;
	}
	
	public void login(AjaxBehaviorEvent event) {
		System.out.println(username+" "+password);
		if(manager.containsUser(this)){
			System.out.println("user found");
			setLoginRequired(false);
			setShowWelcome();
		} else {
			System.out.println("user not found");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Username or password is incorrect"));
		}
	}
	
    public void handleEvent(AjaxBehaviorEvent event) {
        System.out.println("ajax call");
       
    }
    
    public void showRegisterPanel(AjaxBehaviorEvent event){
    	setShowRegister();
    }
    
    public void showWelcomePanel(AjaxBehaviorEvent event){
    	setShowWelcome();
    }
	
	public void addNewUser(AjaxBehaviorEvent event){
		System.out.println("user added");
		if(verifyRegistrationInput()){
			manager.addUser(this);
			setShowWelcome();
			setLoggedIn(true);
			System.out.println("all done");
		}
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
		redirect("site.xhtml");
		return "ok";
	}
	/**
	 * @return the loggedIn
	 */
	public boolean isLoginRequired() {
		return loginRequired;
	}
	/**
	 * @param aLoggedIn the loggedIn to set
	 */
	public void setLoginRequired(boolean aLoggedIn) {
		loginRequired = aLoggedIn;
	}
	/**
	 * @return the loggedIn
	 */
	public boolean isLoggedIn() {
		return !loginRequired;
	}
	/**
	 * @param aLoggedIn the loggedIn to set
	 */
	public void setLoggedIn(boolean aLoggedIn) {
		loginRequired = !aLoggedIn;
	}
	/**
	 * @return the showWelcome
	 */
	public boolean getShowWelcome() {
		return infoBoxCurrentState==InfoState.WELCOME;
	}
	/**
	 * @param aShowWelcome the showWelcome to set
	 */
	public void setShowWelcome() {
		infoBoxCurrentState = InfoState.WELCOME;
	}
	
	public boolean getShowRegister(){
		return infoBoxCurrentState==InfoState.REGISTER;
	}
	
	public void setShowRegister() {
		infoBoxCurrentState = InfoState.REGISTER;
	}
	
	public boolean getShowSubmitForm(){
		return infoBoxCurrentState==InfoState.SUBMITPOST;
	}
	
	public void setShowSubmitForm() {
		infoBoxCurrentState = InfoState.SUBMITPOST;
	}
}
