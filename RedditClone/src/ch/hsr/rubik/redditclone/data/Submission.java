package ch.hsr.rubik.redditclone.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import ch.hsr.rubik.redditclone.CommentBean;
import ch.hsr.rubik.redditclone.UserBean;

public class Submission implements Serializable {
    // FIXME change serialVersionUID, cannot generate it since Eclipse does not
    // find my class file x.x
    private static final long serialVersionUID = 1L;
    private String url;
    private String title;
    private Date submissionDate;
    private String submittedByUser;
    private int votes;

    private List<String> userUpvotes;
    private List<String> userDownvotes;
    private List<CommentBean> comments;

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
        //@formatter:off
        /* I know this is really ugly but I don't want duplicated code or just a function for that
         * {{}} is double brace initialization which creates an anonymous inner class with
         * just an instance initializer in it and any method from ArrayList can be used.
         */
        this(title, url, submittedByUser, submissionDate,
                new ArrayList<String>() {{add(submittedByUser);}}, 
                new ArrayList<String>(), new ArrayList<CommentBean>());
        //@formatter:on
    }

    public Submission(final String title, final String url,
            final String submittedByUser, final Date submissionDate,
            final List<String> userUpvotes, final List<String> userDownvotes,
            final List<CommentBean> comments) {
        super();
        this.title = title;
        this.url = url;
        this.submissionDate = submissionDate;
        this.submittedByUser = submittedByUser;
        this.userUpvotes = userUpvotes;
        this.userDownvotes = userDownvotes;
        this.comments = comments;

        recalculateVotes();
    }

    public void upvote(final AjaxBehaviorEvent event) {
        // TODO: prevent votes from going over int limit, maybe even use double
        // instead of int
        // TODO: color arrow differently when a user has voted

        UserBean user = (UserBean) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("userBean");
        final String username = user.getUsername();

        if (userCanVote(user)) {
            // user has not voted up yet
            if (canUpvote(user)) {
                // remove the downvote if he has one
                if (!canDownvote(user)) {
                    userDownvotes.remove(username);
                    incVotes();
                }
                userUpvotes.add(username);
                incVotes();
            } else {
                userUpvotes.remove(username);
                decVotes();
            }
        } else {
            // TODO: show error message that user isn't logged in.
            System.out.println("User tried to vote but isn't logged in");
        }
    }

    /** Sets the votes to {@code upvotes - downvotes}. */
    public void recalculateVotes() {
        votes = userUpvotes.size() - userDownvotes.size();
    }

    public void downvote(final AjaxBehaviorEvent event) {
        // TODO: color arrow differently when a user has voted

        UserBean user = (UserBean) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("userBean");
        final String username = user.getUsername();

        if (userCanVote(user)) {
            // user has not voted down yet
            if (canDownvote(user)) {
                // remove the upvote if he has one
                if (!canUpvote(user)) {
                    userUpvotes.remove(username);
                    decVotes();
                }
                userDownvotes.add(username);
                decVotes();
            }
            // user has already downvoted, remove the downvote
            else {
                userDownvotes.remove(username);
                incVotes();
            }
        } else {
            // TODO: show error message that user isn't logged in.
            System.out.println("User tried to vote but isn't logged in");
        }
    }

    private static boolean userCanVote(final UserBean user) {
        return user != null && user.isLoggedIn();
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

    public int getVotes() {
        return votes;
    }

    public void setVotes(final int votes) {
        this.votes = votes;
    }

    public List<String> getUserUpvotes() {
        return userUpvotes;
    }

    public void setUserUpvotes(final List<String> userUpvotes) {
        this.userUpvotes = userUpvotes;
    }

    public List<String> getUserDownvotes() {
        return userDownvotes;
    }

    public void setUserDownvotes(final List<String> userDownvotes) {
        this.userDownvotes = userDownvotes;
    }

    public List<CommentBean> getComments() {
        return comments;
    }

    public void setComments(final List<CommentBean> comments) {
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

    public final void incVotes() {
        ++votes;
    }

    public final void decVotes() {
        --votes;
    }

    private boolean canDownvote(final UserBean user) {
        return !userDownvotes.contains(user.getUsername());
    }

    private boolean canUpvote(final UserBean user) {
        return !userUpvotes.contains(user.getUsername());
    }

}
