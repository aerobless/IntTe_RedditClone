package ch.hsr.rubik.redditclone;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

public abstract class DefaultVoteBehavior implements IVotable {
	protected List<String> userUpvotes;
	protected List<String> userDownvotes;
	protected int votes;

	public DefaultVoteBehavior(final List<String> userUpvotes,
			final List<String> userDownvotes) {
		super();
		this.userUpvotes = userUpvotes;
		this.userDownvotes = userDownvotes;
		recalculateVotes();
	}

	protected static boolean userCanVote(final UserBean user) {
		return user != null && user.isLoggedIn();
	}

	@Override
	public boolean userHasUpvoted(final UserBean user) {
		return !canUpvote(user);
	}

	@Override
	public boolean userHasDownvoted(final UserBean user) {
		return !canDownvote(user);
	}

	protected boolean canDownvote(final UserBean user) {
		return !userDownvotes.contains(user.getUsername());
	}

	protected boolean canUpvote(final UserBean user) {
		return !userUpvotes.contains(user.getUsername());
	}

	@Override
	public final void incVotes() {
		++votes;
	}

	@Override
	public final void decVotes() {
		--votes;
	}

	@Override
	public void upvote(final AjaxBehaviorEvent event) {
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
	@Override
	public void recalculateVotes() {
		votes = userUpvotes.size() - userDownvotes.size();
	}

	@Override
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

	@Override
	public int getVotes() {
		return votes;
	}

	@Override
	public void setVotes(final int votes) {
		this.votes = votes;
	}
}
