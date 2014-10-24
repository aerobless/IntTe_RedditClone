package ch.hsr.rubik.redditclone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import ch.hsr.rubik.redditclone.data.Comment;
import ch.hsr.rubik.redditclone.data.Submission;

@ManagedBean(name = "submissionBean")
@SessionScoped
public class SubmissionBean implements Serializable {
	private static final long serialVersionUID = 4096871059235510747L;
	private String url;
	private String title;
	private Submission submission;

	@ManagedProperty(value = "#{commentBean}")
	private CommentBean commentBean;

	@ManagedProperty(value = "#{serverManager}")
	private ServerManager manager;

	@ManagedProperty(value = "#{userBean}")
	private UserBean user;

	private List<Comment> comments;

	/* Beans need a public, no-argument constructor */
	public SubmissionBean() {
		this(null, null);
	}

	public SubmissionBean(final String anUrl, final String aTitle) {
		super();
		url = anUrl;
		title = aTitle;
		comments = new ArrayList<>();
	}

	public ServerManager getManager() {
		return manager;
	}

	public void setManager(final ServerManager manager) {
		this.manager = manager;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(final UserBean user) {
		this.user = user;
	}

	public CommentBean getCommentBean() {
		return commentBean;
	}

	public void setCommentBean(final CommentBean commentBean) {
		this.commentBean = commentBean;
	}

	public final String getUrl() {
		return url;
	}

	public final void setUrl(final String aUrl) {
		url = aUrl;
	}

	public final String getTitle() {
		return title;
	}

	public final void setTitle(final String aTitle) {
		title = aTitle;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(final List<Comment> comments) {
		this.comments = comments;
	}

	private void updateUserReference() {
		user = (UserBean) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("userBean");
	}

	public void addNewSubmission(final AjaxBehaviorEvent event) {
		updateUserReference();
		if (verifySubmission()) {
			String goodURL = url;
			if (!(url.contains("http://") || url.contains("https://"))) {
				goodURL = "http://" + url;
			}
			submission = new Submission(title, goodURL, user.getUsername());
			manager.addSubmission(submission);
			setUrl(null);
			setTitle(null);
			user.setShowWelcome();
		}
	}

	public boolean verifySubmission() {
		if (url.length() < 3) {
			displayUserWarning("Invalid URL");
			return false;
		} else if (title.length() == 0) {
			displayUserWarning("Please add a title");
			return false;
		}
		return true;
	}

	public void displayUserWarning(final String errorMessage) {
		System.out.println("user warnung");
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(errorMessage));
	}

	public boolean showCommentsOfSubmission(final Submission aSubmission) {
		submission = aSubmission;
		submission.sortComments();
		submission.setExpandComments(true);
		commentBean.setSubmission(aSubmission);
		hideAllCommentsOnOtherPosts(aSubmission);
		return submission.isExpandComments();
	}

	private void hideAllCommentsOnOtherPosts(final Submission aSubmission) {
		for (Submission post : manager.getSubmissions()) {
			if (!post.equals(aSubmission)) {
				post.setExpandComments(false);
			}
		}
	}

}
