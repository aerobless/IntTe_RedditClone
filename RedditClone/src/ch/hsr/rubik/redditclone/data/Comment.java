package ch.hsr.rubik.redditclone.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import ch.hsr.rubik.redditclone.UserBean;

public class Comment implements Serializable{

	private static final long serialVersionUID = 2L;
	private String commentContent;
	private List<Comment> comments;
	private String commentedByUser;
	private Date commentDate;
    private int votes;
    
	private List<String> userUpvotes;
    private List<String> userDownvotes;
	
	public Comment(final String commentContent, final String commentedByUser){
		this.commentContent = commentContent;
		this.commentedByUser = commentedByUser;
		commentDate = new Date();
		comments = new ArrayList<>();
		votes = 0;
		userUpvotes = new ArrayList<String>();
		userDownvotes = new ArrayList<String>();
	}
	
	  private static boolean userCanVote(final UserBean user) {
	        return user != null && user.isLoggedIn();
	    }
	  
    public boolean userHasUpvoted(final UserBean user) {
        return !canUpvote(user);
    }

    public boolean userHasDownvoted(final UserBean user) {
        return !canDownvote(user);
    }

    private boolean canDownvote(final UserBean user) {
        return !userDownvotes.contains(user.getUsername());
    }

    private boolean canUpvote(final UserBean user) {
        return !userUpvotes.contains(user.getUsername());
    }

    public final void incVotes() {
        ++votes;
    }

    public final void decVotes() {
        --votes;
    }
	
	 public void upvote(final AjaxBehaviorEvent event) {
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

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}
	
	
	
}
