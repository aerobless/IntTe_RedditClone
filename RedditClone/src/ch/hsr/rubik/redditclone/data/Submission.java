package ch.hsr.rubik.redditclone.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ch.hsr.rubik.redditclone.DefaultVoteBehavior;

public class Submission extends DefaultVoteBehavior implements Serializable,
Comparable<Submission> {
	private static final long serialVersionUID = -5870875309252757156L;
	private String url;
	private String title;
	private Date submissionDate;
	private String submittedByUser;
	private boolean expandComments = false;
	private List<Comment> comments;

	/**
	 * Constructor for creating new submissions without any comments and
	 * up-/downvotes yet. Submissiondate will be {@code new Date()} and the
	 * submission has one upvote from the submitter.
	 */
	public Submission(final String title, final String url,
			final String submittedByUser) {
		this(title, url, submittedByUser, new Date());
	}

	/**
	 * Constructor for creating new submissions without any comments and
	 * up-/downvotes yet. The submission has one upvote from the submitter.
	 */
	public Submission(final String title, final String url,
			final String submittedByUser, final Date submissionDate) {
		// @formatter:off
		/*
		 * I know this is really ugly but I don't want duplicated code or just a
		 * function for that. {{}} is double brace initialization which creates
		 * an anonymous inner class with just an instance initializer in it and
		 * any method from ArrayList can be used.
		 */
		this(title, url, submittedByUser, submissionDate,
				new ArrayList<String>() {{add(submittedByUser);}}, 
				new ArrayList<String>(), new ArrayList<Comment>());
		// @formatter:on
	}

	public Submission(final String title, final String url,
			final String submittedByUser, final Date submissionDate,
			final List<String> userUpvotes, final List<String> userDownvotes,
			final List<Comment> comments) {
		super(userUpvotes, userDownvotes);
		this.title = title;
		this.url = url;
		this.submissionDate = submissionDate;
		this.submittedByUser = submittedByUser;
		this.comments = comments;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getUser() {
		return submittedByUser;
	}

	public void setUser(final String user) {
		this.submittedByUser = user;
	}

	public List<Comment> getComments() {
		return comments;
	}

	/**
	 * Return the whole tree SINCE FUCKING JSF HAS NO EASY COMPONENT FOR TREES
	 * OR USING RECURSION.
	 * 
	 * Each comment will know its hierarchy level.
	 */
	public List<Comment> getCommentTree() {
		List<Comment> commentTreeAsList = new ArrayList<>();
		for (Comment tempComment : comments) {
			commentTreeAsList.add(tempComment);
			commentTreeAsList.addAll(tempComment.getAllChildren());
		}
		return commentTreeAsList;
	}

	public void setComments(final List<Comment> comments) {
		this.comments = comments;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(final Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public String getSubmittedByUser() {
		return submittedByUser;
	}

	public void setSubmittedByUser(final String submittedByUser) {
		this.submittedByUser = submittedByUser;
	}

	public int getNumberOfComments() {
		return comments.size();
	}

	public boolean isExpandComments() {
		return expandComments;
	}

	public void setExpandComments(final boolean expandComments) {
		this.expandComments = expandComments;
	}

	@Override
	public int compareTo(final Submission submissions) {
		return submissions.votes - this.votes;
	}

	public void sortComments() {
		Collections.sort(comments);
	}

	public void addComment(final Comment comment) {
		comments.add(comment);
	}
}
