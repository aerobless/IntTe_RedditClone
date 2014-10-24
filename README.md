RedditClone
=================
RedditClone is a project for the class InternetTechnology at [HSR](http://www.hsr.ch). It's the goal of this project to create a clone of the popular social link sharing site [Reddit](http://reddit.com) using [Java Server Faces technology](https://javaserverfaces.java.net/). Please be aware that RedditClone is not intended for use on a publicly accessible server, since we did not take any precautions regarding security.

<img src="https://raw.githubusercontent.com/aerobless/IntTe_RedditClone/master/screenshots/scr02.png" alt="Screenshot">

##Features
 + Register new users
 + Users can login with a username & password
 + Users can submit links (called submissions)
 + Upvote or downvote submissions
 + Users are limited to one up- or downvote per submission, they're allowed to change their vote at any time.
 + Add comments to submissions
 + The number of upvotes is displayed for each submission
 + The server application is capable of starting a separate thread to backup its data as XML file to disk and to load that data on the next restart.
 + RedditClone utilises Ajax wherever possible to reduce user-wait-time to a minimum. (Examples of Ajax are: Up/Downvotes, the submission panel & the login-action)

##Time Tracking
This is a rudimentary time tracking. Please see the [commit-history](https://github.com/aerobless/IntTe_RedditClone/commits/master) for more details.

|Date | Time spent | Name | Task |
|-----|------------|------|------|
|01.10.2014 | 2h  | Daniela, Marco, Theo  | Project Setup, Git Repo, Discussion: Course of action  |
|01.10.2014 | 4h  | Theo  | Login/logout, UserBean, ServerManager, basic user authentication |
|02.10.2014 | 2h  | Theo  | Added bootstrap, basic design |
|03.10.2014 | 5h  | Theo  | Changed login to use Ajax, added page body, added SubmissionBean, changed site layout to use template |
|07.10.2014 | 5h  | Theo  | Improved bootstrap design, iterating through posts as a list, server is capable to store submissions/user as xml file, added upvote/downvote arrows |
|08.10.2014 | 3h  | Theo  | Upvotes/Downvotes initial logic, storing server data in separate Thread |
|08.10.2014 | 4h  | Marco | Correct Upvote/Downvote behaviour, added data object for submissions |
|08.10.2014 | 4h  | Daniela | Added Data Class for comments, changings on site.xhtml to show comments |
|09.10.2014 | 3h  | Marco | Improved submission data object, data object is now used instead of bean in ServerManager |
|10.10.2014 | 3h  | Marco | Upvotes/Downvotes are coloured differently when a user voted |
|12.10.2014 | 5h  | Daniela | Started to implement the logic to add Comments to a Submission |
|15.10.2014 | 2h  | Theo  | Added user data object to store user data instead of userBean, added error messages (e.g. when entering the wrong password)|
|15.10.2014 | 4h  | Daniela | Several changings on CommentBean, SubmissionBean, Submission and xhtml files to improve initial solution |
|15.10.2014 | 2h  | Marco | Added internationalisation (translations) |
|22.10.2014 | 2h  | Theo, Daniela | Posting a link or a comment and the up-/downvoting is only possible when logged in, correct number of comments is shown, added a sorting algorithm |
|24.10.2014 | 2h  | Marco   | Extracted interface/abstract superclass for duplicated code (voting on things), general code cleanup |
|24.10.2014 | 0.5h| Marco | Further i18n for frontpage |
|24.10.2014 | 6h  | Marco   | Functionality for hierarchic comments  |
|   |   |    |   |

