package ch.hsr.rubik.redditclone;

import javax.faces.event.AjaxBehaviorEvent;

public interface IVotable {

	boolean userHasUpvoted(final UserBean user);

	boolean userHasDownvoted(final UserBean user);

	void incVotes();

	void decVotes();

	void upvote(final AjaxBehaviorEvent event);

	void downvote(final AjaxBehaviorEvent event);

	void recalculateVotes();

	public int getVotes();

	public void setVotes(final int votes);

}
