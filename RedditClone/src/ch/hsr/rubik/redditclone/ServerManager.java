package ch.hsr.rubik.redditclone;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/*
 * This bean is accessible throughout the entire application (see UserBean login method for an example).
 * By setting eager=true we're making sure that this been gets initialized and user-data gets loaded
 * before the first users tries to visit our page. 
 * 
 * Edit: Removed eager=true because it ended up starting this bean multiple times.. will have to see if
 * there's another way to get this to start up early.
 */
@ManagedBean(name="serverManager")
@ApplicationScoped
public class ServerManager {
	private ArrayList<UserBean> users;
	private ArrayList<SubmissionBean> submissions;
	
	private final String USER_FILE = getJarDirectory("reddit_clone_users.xml"); 
	private final String SUBMISSIONS_FILE = getJarDirectory("reddit_clone_submissions.xml"); 
	private final int AUTOMATIC_SAVE_TIME_SECONDS = 30;
	
	public ServerManager() {
		super();
		System.out.println("Loading users from disk..");
		System.out.println("Storage-Path: "+getJarDirectory(""));
		
		File persistanceFile = new File(USER_FILE);
		if(!persistanceFile.exists()){
			System.out.println("No existing data found, creating users.xml and submissions.xml");
			users = new ArrayList<UserBean>();
			submissions = new ArrayList<SubmissionBean>();
			loadDemoData();
			saveAll();
		} else {
			System.out.println("Existing data found, loading users.xml and submissions.xml");
			
			//TODO: make better
			/*
			Object loadingUsers = loadXMLFile(USER_FILE);
			
			if (loadingUsers instanceof ArrayList<?>) {
				if(((ArrayList<?>) loadingUsers).get(0) instanceof UserBean){
					users = (ArrayList<UserBean>) loadingUsers;
				}
			}*/
			
			users = (ArrayList<UserBean>) loadXMLFile(USER_FILE);
			submissions = (ArrayList<SubmissionBean>) loadXMLFile(SUBMISSIONS_FILE);
		}

		createScheduledSaveManagerThread();
	}

	private void createScheduledSaveManagerThread() {
		Thread scheduledSaveManager = new Thread(() -> {
		    while(true){
		    	try {
					Thread.sleep(AUTOMATIC_SAVE_TIME_SECONDS*1000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	System.out.println(new Date()+" ScheduledSaveManager saving to disk...");
		    	saveAll();
		    }
		});
		scheduledSaveManager.start();
	}

	private void saveAll() {
		saveToXMLFile(users, USER_FILE);
		saveToXMLFile(submissions, SUBMISSIONS_FILE);
	}

	private void loadDemoData() {
		users.add(new UserBean("theo", "123456"));
		users.add(new UserBean("marco", "123456"));
		users.add(new UserBean("daniela", "123456"));
		
		submissions.add(new SubmissionBean("http://www.google.com","A cool new search engine",new Date(), "theo"));
		submissions.add(new SubmissionBean("http://www.apple.com","A company that sells awesome computers",new Date(), "marco"));
		submissions.add(new SubmissionBean("http://www.reddit.com","Like RedditClone but not as good looking..",new Date(), "theo"));
		submissions.add(new SubmissionBean("http://www.reddit.com/r/talesfromtechsupport/comments/2idaug/you_unplugged_your_what/","You unplugged your what?",new Date(), "self.talesfromtechsupport"));
	}
	
	public boolean containsUser(UserBean user){
		return users.contains(user);
	}
	
	public void addUser(UserBean user){
		users.add(user);
		System.out.println("Added a new user, we're now counting "+users.size()+" in our database.");
	}
	
	public void addSubmission(SubmissionBean submission){
		submissions.add(submission);
		System.out.println("Added a new submission, we're now counting "+submissions.size()+" in our database.");
	}
	
	public List<SubmissionBean> getSubmissions(){
		return submissions;
	}

    /**
     * Save something as xml file.
     * @param inputObject
     * @param filename
     */
    public void saveToXMLFile(Object inputObject, String filename){
		FileOutputStream fos = null;
		try {
		    fos = new FileOutputStream(filename);
		    byte[] bytes = serializeToXML(inputObject).getBytes("UTF-8");
		    fos.write(bytes);

		} catch(Exception e) {
			System.out.println("Error while saving data to XML");
		} finally {
		    if(fos!=null) {
		        try{ 
		            fos.close();
		        } catch (IOException e) {
					System.out.println("Error while saving data to XML");
		        }
		    }
		}
	}
	
	private String serializeToXML(Object input){
		XStream saveXStream = new XStream(new StaxDriver());
		saveXStream.alias("user", UserBean.class);
		saveXStream.alias("submission", SubmissionBean.class);
		saveXStream.alias("comment", CommentBean.class);	
		return saveXStream.toXML(input);
	}
	
	/**
	 * Load something from a xml file.
	 * @param filename
	 * @return
	 */
	public Object loadXMLFile(String filename){
		File xmlFile = new File(filename);
		XStream loadXStream = new XStream(new StaxDriver());
		loadXStream.alias("user", UserBean.class);
		loadXStream.alias("submission", SubmissionBean.class);
		loadXStream.alias("comment", CommentBean.class);	
		Object loadedObject = loadXStream.fromXML(xmlFile);
		return loadedObject;
	}
	
	public String getJarDirectory(String filename){
		URL jarLocation = ServerManager.class.getProtectionDomain().getCodeSource().getLocation();
		URL dataXML = null;
		try {
			dataXML = new URL(jarLocation, filename);
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL in ServerManager. JarLoc: "+jarLocation+" Filename: "+filename);
		}
		String decodedPath = null;
		try {
			decodedPath = URLDecoder.decode(dataXML.getPath(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException in ServerManager. (UTF-8)");
		}
		return decodedPath;
	}
}
 