package ch.hsr.rubik.redditclone;

import java.util.ArrayList;

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

	public ServerManager() {
		super();
		System.out.println("Loading users from disk..");
		//TODO: actual save-to-xml / load-from-xml methods and demo data.
		users.add(new UserBean("theo", "123456"));
		users.add(new UserBean("marco", "123456"));
		users.add(new UserBean("daniela", "123456"));
	}
	
	public boolean containsUser(UserBean user){
	 return users.contains(user);
	}
}
 