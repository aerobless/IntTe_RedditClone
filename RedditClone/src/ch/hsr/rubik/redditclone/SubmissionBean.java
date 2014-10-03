package ch.hsr.rubik.redditclone;

import java.util.ArrayList;
import java.util.Date;

//TODO: Idk if this will really be a bean yet
public class SubmissionBean {
	private String url;
	private String title;
	private Date submissionDate;
	private String submittedByUser;
	private int votes;
	
	private ArrayList<CommentBean> comments;

	public SubmissionBean() {
		// TODO Auto-generated constructor stub
	}

}
