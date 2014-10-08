package ch.hsr.rubik.redditclone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean(name="submissionBean")
@SessionScoped
public class SubmissionBean implements Serializable {
	private static final long serialVersionUID = 4096871059235510747L;
	private String url;
	private String title;
	private Date submissionDate;
	private String submittedByUser;
	private int votes;
	
	private ArrayList<String> userUpvotes;
	private ArrayList<String> userDownvotes;
	private ArrayList<CommentBean> comments;
	
	@ManagedProperty(value="#{serverManager}")
	private ServerManager manager;
	
	@ManagedProperty(value="#{userBean}")
	private UserBean user;

	public SubmissionBean() {
		votes = 1;
		userUpvotes = new ArrayList<String>();
		userDownvotes  = new ArrayList<String>();
		comments = new ArrayList<CommentBean>();
	}

	public ServerManager getManager() {
		return manager;
	}

	public void setManager(ServerManager manager) {
		this.manager = manager;
	}
	
	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	/**
	 * Creates a new instance of this class.
	 *
	 * @param aUrl
	 * @param aTitle
	 * @param aSubmissionDate
	 * @param aSubmittedByUser
	 */
	public SubmissionBean(String aUrl, String aTitle, Date aSubmissionDate,
			String aSubmittedByUser) {
		super();
		url = aUrl;
		title = aTitle;
		submissionDate = aSubmissionDate;
		submittedByUser = aSubmittedByUser;
		votes = 1;
		userUpvotes = new ArrayList<String>();
		userDownvotes  = new ArrayList<String>();
		comments = new ArrayList<CommentBean>();
	}

	/**
	 * @return the url
	 */
	public final String getUrl() {
		return url;
	}

	/**
	 * @param aUrl the url to set
	 */
	public final void setUrl(String aUrl) {
		url = aUrl;
	}

	/**
	 * @return the title
	 */
	public final String getTitle() {
		return title;
	}

	/**
	 * @param aTitle the title to set
	 */
	public final void setTitle(String aTitle) {
		title = aTitle;
	}

	/**
	 * @return the submissionDate
	 */
	public final Date getSubmissionDate() {
		return submissionDate;
	}

	/**
	 * @param aSubmissionDate the submissionDate to set
	 */
	public final void setSubmissionDate(Date aSubmissionDate) {
		submissionDate = aSubmissionDate;
	}

	/**
	 * @return the submittedByUser
	 */
	public final String getSubmittedByUser() {
		return submittedByUser;
	}

	/**
	 * @param aSubmittedByUser the submittedByUser to set
	 */
	public final void setSubmittedByUser(String aSubmittedByUser) {
		submittedByUser = aSubmittedByUser;
	}

	/**
	 * @return the votes
	 */
	public final int getVotes() {
		return votes;
	}

	/**
	 * @param aVotes the votes to set
	 */
	public final void setVotes(int aVotes) {
		votes = aVotes;
	}

	/**
	 * @return the comments
	 */
	public final ArrayList<CommentBean> getComments() {
		return comments;
	}

	/**
	 * @param aComments the comments to set
	 */
	public final void setComments(ArrayList<CommentBean> aComments) {
		comments = aComments;
	}
	
	public void upvote(AjaxBehaviorEvent event) {
		//TODO: prevent votes from going over int limit, maybe even use double instead of int
		//TODO: color arrow differently when a user has voted
		updateUserReference();		
		if(user!= null && user.isLoggedIn() && !userUpvotes.contains(user.getUsername())){
			//Remove user from Downvotes because he changed to upvote
			if(userDownvotes.contains(user.getUsername())){
				userDownvotes.remove(user.getUsername());
			} else {
				userUpvotes.add(user.getUsername());
			}
			setVotes(votes+1);
			//TODO: add warning that user tried to upvote more then once.
		} else {
			//TODO: show error message that user isn't logged in.
			System.out.println("User tried to vote but isn't logged in");
		}
	}

	private void updateUserReference() {
		user = (UserBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userBean");
	}
	
	public void downvote(AjaxBehaviorEvent event) {
		//TODO: prevent votes from going over int limit, maybe even use double instead of int
		//TODO: color arrow differently when a user has voted
		updateUserReference();	
		if(user!= null && user.isLoggedIn() && !userDownvotes.contains(user.getUsername())){  	
			//Remove user from Upvotes because he changed to downvote
			if(userUpvotes.contains(user.getUsername())){
				userUpvotes.remove(user.getUsername());
			} else {
				userDownvotes.add(user.getUsername());
			}
			setVotes(votes-1);
			//TODO: add warning that user tried to downvote more then once.
		} else {
			//TODO: show error message that user isn't logged in.
			System.out.println("User tried to vote but isn't logged in");
		}
	}
	
	public void addNewSubmission(AjaxBehaviorEvent event){
		System.out.println("submission added");
		setSubmittedByUser(user.getUsername());
		setSubmissionDate(new Date());
		manager.addSubmission(this);
		user.setShowWelcome();
		System.out.println("all done");
	}
}
