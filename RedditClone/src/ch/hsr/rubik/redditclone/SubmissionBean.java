package ch.hsr.rubik.redditclone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
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
	
	private ArrayList<CommentBean> comments;
	
	@ManagedProperty(value="#{serverManager}")
	private ServerManager manager;
	
	@ManagedProperty(value="#{userBean}")
	private UserBean user;

	public SubmissionBean() {
		
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
		//TODO: remember which user voted so that we can prevent ppl from voting multiple times
		//TODO: prevent votes from going over int limit, maybe even use double instead of int
		//TODO: color arrow differently when a user has voted
		setVotes(votes+1);
		//manager.saveAll();
	}
	
	public void downvote(AjaxBehaviorEvent event) {
		//TODO: remember which user voted so that we can prevent ppl from voting multiple times
		//TODO: prevent votes from going over int limit, maybe even use double instead of int
		//TODO: color arrow differently when a user has voted
		setVotes(votes-1);
		//manager.saveAll();
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
