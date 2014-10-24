package ch.hsr.rubik.redditclone.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.hsr.rubik.redditclone.DefaultVoteBehavior;

public class Comment extends DefaultVoteBehavior implements Serializable,
		Comparable<Comment> {
	private static final long serialVersionUID = -5190257344022025352L;
	private String commentContent;
	private String commentedByUser;
	private Date commentDate;
	private boolean showReplyTextbox;

	private Comment parent;
	private List<Comment> children;
	private int hierarchyLevel = 0;

	public Comment(final String commentContent, final String commentedByUser,
			final Comment parent) {
		this(commentContent, commentedByUser, parent, new Date());
	}

	public Comment(final String commentContent, final String commentedByUser,
			final Comment parent, final Date commentDate) {
		//@formatter:off
		this(commentContent, commentedByUser, parent, commentDate,
				new ArrayList<String>() {{ add(commentedByUser); }},
				new ArrayList<String>());
		//@formatter:on
	}

	public Comment(final String commentContent, final String commentedByUser,
			final Comment parent, final Date commentDate,
			final List<String> userUpvotes, final List<String> userDownvotes) {
		super(userUpvotes, userDownvotes);
		this.commentContent = commentContent;
		this.commentedByUser = commentedByUser;
		this.commentDate = commentDate;
		children = new ArrayList<>();
		this.parent = parent;
		// level will still be 0 if this is a top level comment and has no
		// parent
		if (parent != null) {
			hierarchyLevel = parent.getHierarchyLevel() + 1;
		}
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(final String commentContent) {
		this.commentContent = commentContent;
	}

	public void addSubcomment(final Comment subcomment) {
		children.add(subcomment);
	}

	public List<Comment> getChildren() {
		return children;
	}

	public List<Comment> getAllChildren() {
		List<Comment> commentTreeAsList = new ArrayList<>();
		for (Comment childComment : getChildren()) {
			commentTreeAsList.add(childComment);
			commentTreeAsList.addAll(childComment.getAllChildren());
		}
		return commentTreeAsList;
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

	public boolean isShowReplyTextbox() {
		return showReplyTextbox;
	}

	public void setShowReplyTextbox(final boolean showReplyTextbox) {
		this.showReplyTextbox = showReplyTextbox;
	}

	public int getHierarchyLevel() {
		return hierarchyLevel;
	}

	@Override
	public String toString() {
		return commentContent + "[" + hierarchyLevel + "]";
	}

}
