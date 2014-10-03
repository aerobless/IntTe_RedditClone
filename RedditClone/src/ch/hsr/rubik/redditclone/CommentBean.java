package ch.hsr.rubik.redditclone;

import java.util.Date;

//TODO: Idk if this will really be a bean yet
public class CommentBean {
	private String commentContent;
	private String commentedByUser;
	private Date commentDate;
	
	public CommentBean() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the commentContent
	 */
	public final String getCommentContent() {
		return commentContent;
	}

	/**
	 * @param aCommentContent the commentContent to set
	 */
	public final void setCommentContent(String aCommentContent) {
		commentContent = aCommentContent;
	}

	/**
	 * @return the commentedByUser
	 */
	public final String getCommentedByUser() {
		return commentedByUser;
	}

	/**
	 * @param aCommentedByUser the commentedByUser to set
	 */
	public final void setCommentedByUser(String aCommentedByUser) {
		commentedByUser = aCommentedByUser;
	}

	/**
	 * @return the commentDate
	 */
	public final Date getCommentDate() {
		return commentDate;
	}

	/**
	 * @param aCommentDate the commentDate to set
	 */
	public final void setCommentDate(Date aCommentDate) {
		commentDate = aCommentDate;
	}
}
