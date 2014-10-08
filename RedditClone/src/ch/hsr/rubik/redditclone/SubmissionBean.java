package ch.hsr.rubik.redditclone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean(name = "submissionBean")
@SessionScoped
public class SubmissionBean implements Serializable {
    private static final long serialVersionUID = 4096871059235510747L;
    private String url;
    private String title;
    private Date submissionDate;
    private String submittedByUser;
    private int votes;

    private final ArrayList<String> userUpvotes;
    private final ArrayList<String> userDownvotes;
    private ArrayList<CommentBean> comments;

    @ManagedProperty(value = "#{serverManager}")
    private ServerManager manager;

    @ManagedProperty(value = "#{userBean}")
    private UserBean user;

    // FIXME delete me
    public SubmissionBean() {
        votes = 1;
        userUpvotes = new ArrayList<String>();
        userDownvotes = new ArrayList<String>();
        comments = new ArrayList<CommentBean>();
    }

    public SubmissionBean(final String anUrl, final String aTitle,
            final Date aSubmissionDate, final String aSubmittedByUser) {
        super();
        url = anUrl;
        title = aTitle;
        submissionDate = aSubmissionDate;
        submittedByUser = aSubmittedByUser;
        votes = 1;
        userUpvotes = new ArrayList<String>();
        userDownvotes = new ArrayList<String>();
        comments = new ArrayList<CommentBean>();
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

    /** @return the url */
    public final String getUrl() {
        return url;
    }

    /** @param aUrl the url to set */
    public final void setUrl(final String aUrl) {
        url = aUrl;
    }

    /** @return the title */
    public final String getTitle() {
        return title;
    }

    /** @param aTitle the title to set */
    public final void setTitle(final String aTitle) {
        title = aTitle;
    }

    /** @return the submissionDate */
    public final Date getSubmissionDate() {
        return submissionDate;
    }

    /** @param aSubmissionDate the submissionDate to set */
    public final void setSubmissionDate(final Date aSubmissionDate) {
        submissionDate = aSubmissionDate;
    }

    /** @return the submittedByUser */
    public final String getSubmittedByUser() {
        return submittedByUser;
    }

    /** @param aSubmittedByUser the submittedByUser to set */
    public final void setSubmittedByUser(final String aSubmittedByUser) {
        submittedByUser = aSubmittedByUser;
    }

    /** @return the votes */
    public final int getVotes() {
        return votes;
    }

    /** @param aVotes the votes to set */
    public final void setVotes(final int aVotes) {
        votes = aVotes;
    }

    public final void incVotes() {
        ++votes;
    }

    public final void decVotes() {
        --votes;
    }

    /** @return the comments */
    public final ArrayList<CommentBean> getComments() {
        return comments;
    }

    /** @param aComments the comments to set */
    public final void setComments(final ArrayList<CommentBean> aComments) {
        comments = aComments;
    }

    private boolean userCanVote() {
        return user != null && user.isLoggedIn();
    }

    private void updateUserReference() {
        user = (UserBean) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("userBean");
    }

    public void upvote(final AjaxBehaviorEvent event) {
        // TODO: prevent votes from going over int limit, maybe even use double
        // instead of int
        // TODO: color arrow differently when a user has voted

        updateUserReference();
        final String username = user.getUsername();

        if (userCanVote()) {
            if (canUpvote(username)) {
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

    public void downvote(final AjaxBehaviorEvent event) {
        // TODO: prevent votes from going over int limit, maybe even use double
        // instead of int
        // TODO: color arrow differently when a user has voted

        updateUserReference();
        final String username = user.getUsername();

        if (userCanVote()) {
            if (canDownvote(username)) {
                userDownvotes.add(username);
                decVotes();
            } else {
                userDownvotes.remove(username);
                incVotes();
            }
        } else {
            // TODO: show error message that user isn't logged in.
            System.out.println("User tried to vote but isn't logged in");
        }
    }

    private boolean canDownvote(final String username) {
        return !userDownvotes.contains(username);
    }

    private boolean canUpvote(final String username) {
        return !userUpvotes.contains(username);
    }

    public void addNewSubmission(final AjaxBehaviorEvent event) {
        System.out.println("submission added");
        setSubmittedByUser(user.getUsername());
        setSubmissionDate(new Date());
        manager.addSubmission(this);
        user.setShowWelcome();
        System.out.println("all done");
    }
}
