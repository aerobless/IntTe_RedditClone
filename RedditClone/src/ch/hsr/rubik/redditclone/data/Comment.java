package ch.hsr.rubik.redditclone.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Comment implements Serializable{

	private static final long serialVersionUID = 2L;
	private String commentContent;
	private List<Comment> comments;
	private String commentedByUser;
	private Date commentDate;
	
	public Comment(final String commentContent, final String commentedByUser){
		this.commentContent = commentContent;
		this.commentedByUser = commentedByUser;
		commentDate = new Date();
		comments = new ArrayList<>();
	}
	
	public String getCommentContent() {
		return commentContent;
	}
	
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getCommentedByUser() {
		return commentedByUser;
	}

	public void setCommentedByUser(String commentedByUser) {
		this.commentedByUser = commentedByUser;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	
}
