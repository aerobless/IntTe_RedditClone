package ch.hsr.rubik.redditclone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import ch.hsr.rubik.redditclone.data.Comment;
import ch.hsr.rubik.redditclone.data.Submission;

@ManagedBean(name = "submissionBean")
@SessionScoped
public class SubmissionBean implements Serializable {
    private static final long serialVersionUID = 4096871059235510747L;
    private String url;
    private String title;
    private Submission submission;

    @ManagedProperty(value = "#{commentBean}")
    private CommentBean commentBean;
    
	@ManagedProperty(value = "#{serverManager}")
    private ServerManager manager;

    @ManagedProperty(value = "#{userBean}")
    private UserBean user;
    
	private List<Comment> comments;

	/* Beans need a public, no-argument constructor */
    public SubmissionBean() {
        this(null, null);
    }

    public SubmissionBean(final String anUrl, final String aTitle) {
        super();
        url = anUrl;
        title = aTitle;
        comments = new ArrayList<>();
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
    
    public CommentBean getCommentBean() {
		return commentBean;
	}

	public void setCommentBean(CommentBean commentBean) {
		this.commentBean = commentBean;
	}

    public final String getUrl() {
        return url;
    }

    public final void setUrl(final String aUrl) {
        url = aUrl;
    }

    public final String getTitle() {
        return title;
    }

    public final void setTitle(final String aTitle) {
        title = aTitle;
    }
	
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

    private void updateUserReference() {
        user = (UserBean) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("userBean");
    }

    public void addNewSubmission(final AjaxBehaviorEvent event) {
        System.out.println("about to add submission");

        updateUserReference();
        submission = new Submission(url, title, user.getUsername());
        
        manager.addSubmission(submission);
        
        setUrl(null);
        setTitle(null);

        user.setShowWelcome();
        System.out.println("all done");
    }
    
    public boolean showCommentsOfSubmission(Submission aSubmission){
    	submission = aSubmission;
    	submission.setExpandComments(true);
    	commentBean.setSubmission(aSubmission);
    	hideAllCommentsOnOtherPosts(aSubmission);
    	return submission.isExpandComments();
    }
    
    private void hideAllCommentsOnOtherPosts(Submission aSubmission){
    	for(Submission post : manager.getSubmissions()){
    		if(!post.equals(aSubmission)){
    			post.setExpandComments(false);
    		}
    	}
    }
    
}
