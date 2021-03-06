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

import ch.hsr.rubik.redditclone.data.User;

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
		loginRequired = true;
		username = aName;
		password = aPassword;
	}
	
	public void login(AjaxBehaviorEvent event) {
		System.out.println(username+" "+password);
		manager.sortSubmissions();
		if(manager.containsUser(username, password)){
			System.out.println("user found");
			setLoginRequired(false);
			setShowWelcome();
		} else {
			System.out.println("user not found");
			displayUserWarning("Username or Password is incorrect");
		}
	}
	public void displayUserWarning(String errorMessage) {
		System.out.println("user warnung");
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMessage));
	}
	
    public void handleEvent(AjaxBehaviorEvent event) {
        System.out.println("ajax call");
       
    }
    
    public void showRegisterPanel(AjaxBehaviorEvent event){
    	setShowRegister();
    }
    
    public void showSubmissionPanel(AjaxBehaviorEvent event){
    	setShowSubmitForm();
    }
    
    public void showWelcomePanel(AjaxBehaviorEvent event){
    	setShowWelcome();
    }
	
	public void addNewUser(AjaxBehaviorEvent event){
		System.out.println("user added");
		if(verifyRegistrationInput()){
			manager.addUser(new User(username, password, email));
			setShowWelcome();
			setLoggedIn(true);
			System.out.println("all done");
		}
	}
	
	private boolean verifyRegistrationInput() {
		if(manager.containsUser(username)){
			displayUserWarning("Username already taken, chose a different username.");
			return false;
		} else if (!password.equals(passwordConfirm)){
			displayUserWarning("Your passwords don't match.");
			return false;
		} else if(!(email.contains("@")&&email.contains("."))){
			displayUserWarning("Please enter a valid email adress.");
			return false;
		} else if(password.equals("")){
			displayUserWarning("Your password cannot be empty.");
			return false;
		} else if(password.length()<3){
			displayUserWarning("Your password must be at least 3 characters long.");
			return false;
		}
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
		manager.sortSubmissions();
		invalidateSession();
		redirect("site.xhtml");
		return "ok";
	}
	
	private void invalidateSession() {
		((HttpSession) FacesContext.getCurrentInstance()
				   .getExternalContext().getSession(false)).invalidate();
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
