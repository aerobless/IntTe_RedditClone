package ch.hsr.rubik.redditclone.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.catalina.User;

import ch.hsr.rubik.redditclone.CommentBean;

public class Submission implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String url;
    private final String title;
    private final Date date;
    private final String user;
    private int votes;

    private final List<String> userUpvotes;
    private final List<String> userDownvotes;
    private final List<CommentBean> comments;

    public Submission(final String url, final String title,
            final Date submissionDate, final String submittedByUser) {
        this(url, title, submissionDate, submittedByUser, 1, new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
    }

    public Submission(final String url, final String title,
            final Date submissionDate, final String submittedByUser,
            final int votes, final List<String> userUpvotes,
            final List<String> userDownvotes, final List<CommentBean> comments) {
        super();
        this.url = url;
        this.title = title;
        this.date = submissionDate;
        this.user = submittedByUser;
        this.votes = votes;
        this.userUpvotes = userUpvotes;
        this.userDownvotes = userDownvotes;
        this.comments = comments;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public String getUser() {
        return user;
    }

    public final void setVotes(final int aVotes) {
        votes = aVotes;
    }

    public final void incVotes(final User user) {
        ++votes;
    }

    public final void decVotes(final User user) {
        --votes;
    }

    private boolean canDownvote(final String username) {
        return !userDownvotes.contains(username);
    }

    private boolean canUpvote(final String username) {
        return !userUpvotes.contains(username);
    }

    public int getVotes() {
        return votes;
    }

}
