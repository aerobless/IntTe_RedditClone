package ch.hsr.rubik.redditclone;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import ch.hsr.rubik.redditclone.data.Submission;

@ManagedBean(name = "submissionBean")
@SessionScoped
public class SubmissionBean implements Serializable {
    private static final long serialVersionUID = 4096871059235510747L;
    private String url;
    private String title;

    @ManagedProperty(value = "#{serverManager}")
    private ServerManager manager;

    @ManagedProperty(value = "#{userBean}")
    private UserBean user;

    /* Beans need a public, no-argument constructor */
    public SubmissionBean() {
        this(null, null);
    }

    public SubmissionBean(final String anUrl, final String aTitle) {
        super();
        url = anUrl;
        title = aTitle;
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

    private void updateUserReference() {
        user = (UserBean) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("userBean");
    }

    public void addNewSubmission(final AjaxBehaviorEvent event) {
        System.out.println("about to add submission");

        updateUserReference();
        Submission submission = new Submission(url, title, user.getUsername());
        manager.addSubmission(submission);

        setUrl(null);
        setTitle(null);

        user.setShowWelcome();
        System.out.println("all done");
    }
}
