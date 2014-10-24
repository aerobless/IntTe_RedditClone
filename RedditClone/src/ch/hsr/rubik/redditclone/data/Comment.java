package ch.hsr.rubik.redditclone.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.hsr.rubik.redditclone.DefaultVoteBehavior;

public class Comment extends DefaultVoteBehavior implements Serializable,
Comparable<Comment> {

	private static final long serialVersionUID = 2L;
	private String commentContent;
	private List<Comment> comments;
	private String commentedByUser;
	private Date commentDate;
	private int votes;

	public Comment(final String commentContent, final String commentedByUser) {
		super(new ArrayList<String>(), new ArrayList<String>());
		this.commentContent = commentContent;
		this.commentedByUser = commentedByUser;
		commentDate = new Date();
		comments = new ArrayList<>();
		votes = 0;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(final String commentContent) {
		this.commentContent = commentContent;
	}

	public void addSubcomment(final Comment subcomment) {
		comments.add(subcomment);
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(final List<Comment> comments) {
		this.comments = comments;
	}

	public String getCommentedByUser() {
		return commentedByUser;
	}

	public void setCommentedByUser(final String commentedByUser) {
		this.commentedByUser = commentedByUser;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(final Date commentDate) {
		this.commentDate = commentDate;
	}

	@Override
	public int compareTo(final Comment comment) {
		System.out.println("compare");
		return comment.votes - this.votes;
	}
}
