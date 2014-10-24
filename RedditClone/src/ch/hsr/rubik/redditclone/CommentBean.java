package ch.hsr.rubik.redditclone;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import ch.hsr.rubik.redditclone.data.Comment;
import ch.hsr.rubik.redditclone.data.Submission;

@ManagedBean(name = "commentBean")
@SessionScoped
public class CommentBean {
	private String commentContent;
	private String commentedByUser;
	private Date commentDate;
	private Submission submission;

	@ManagedProperty(value = "#{userBean}")
	private UserBean user;

	public CommentBean() {
		user = (UserBean) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("userBean");
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(final String commentContent) {
		this.commentContent = commentContent;
	}

	public final String getCommentedByUser() {
		return commentedByUser;
	}

	public final void setCommentedByUser(final String aCommentedByUser) {
		commentedByUser = aCommentedByUser;
	}

	public final Date getCommentDate() {
		return commentDate;
	}

	public final void setCommentDate(final Date aCommentDate) {
		commentDate = aCommentDate;
	}

	public Submission getSubmission() {
		return submission;
	}

	public void setSubmission(final Submission submission) {
		this.submission = submission;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(final UserBean user) {
		this.user = user;
	}

	public void addNewCommentToSubmission(final AjaxBehaviorEvent event) {
		Comment aComment = new Comment(commentContent, user.getUsername(), null);
		submission.getComments().add(aComment);
		setCommentContent("");
	}

	public void addNewCommentToComment(final Comment parentComment) {
		Comment subComment = new Comment(commentContent, user.getUsername(),
				parentComment);
		parentComment.addSubcomment(subComment);
		// submission.getComments().add(subComment);
		hideCommentBox(parentComment);
	}

	public void hideCommentBox(final Comment comment) {
		comment.setShowReplyTextbox(false);
		setCommentContent("");
	}

}
