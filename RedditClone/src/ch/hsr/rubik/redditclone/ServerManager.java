package ch.hsr.rubik.redditclone;

import java.util.ArrayList;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="serverManager")
@ApplicationScoped
public class ServerManager {
	ArrayList<UserBean> users = new ArrayList<UserBean>();

	public ServerManager() {
		super();
		users.add(new UserBean("theo", "123456"));
		users.add(new UserBean("marco", "123456"));
		users.add(new UserBean("daniela", "123456"));
	}
	
	public boolean containsUser(UserBean user){
	 return users.contains(user);
	}
}
 