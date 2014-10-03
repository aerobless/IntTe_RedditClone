package ch.hsr.rubik.redditclone;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/*
 * This bean is accessible throughout the entire application (see UserBean login method for an example).
 * By setting eager=true we're making sure that this been gets initialized and user-data gets loaded
 * before the first users tries to visit our page. 
 */
@ManagedBean(name="serverManager", eager=true)
@ApplicationScoped
public class ServerManager {
	ArrayList<UserBean> users = new ArrayList<UserBean>();
	ArrayList<SubmissionBean> submissions = new ArrayList<SubmissionBean>();

	public ServerManager() {
		super();
		System.out.println("Loading users from disk..");
		//TODO: actual save-to-xml / load-from-xml methods and demo data.
		loadDemoData();
	}

	private void loadDemoData() {
		users.add(new UserBean("theo", "123456"));
		users.add(new UserBean("marco", "123456"));
		users.add(new UserBean("daniela", "123456"));
		
		submissions.add(new SubmissionBean("http://www.google.com","A cool new search engine",new Date(), "theo"));
		submissions.add(new SubmissionBean("http://www.apple.com","A company that sells awesome computers",new Date(), "marco"));
		submissions.add(new SubmissionBean("http://www.reddit.com","Like RedditClone but not as good looking..",new Date(), "theo"));
	}
	
	public boolean containsUser(UserBean user){
		return users.contains(user);
	}
	
	public void addUser(UserBean user){
		users.add(user);
		System.out.println("Added a new user, we're now counting "+users.size()+" in our database.");
	}
}
 