IntTe_RedditClone
=================
RedditClone is a project for the class InternetTechnology at [HSR](http://www.hsr.ch). It's the goal of this project to create a [Reddit](http://reddit.com) clone using [Java Server Faces technology](https://javaserverfaces.java.net/). Please be aware that this project is not intended for use on a publicly accessible server, since we did not take any precautions regarding security.

<img src="https://raw.githubusercontent.com/aerobless/IntTe_RedditClone/master/screenshots/scr01.png" alt="Screenshot">

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
|Date | Time spent | Name | Task |
|-----|------------|------|------|
|01.10.2014 | 2h  | Daniela, Marco, Theo  | Project Setup, Git Repo, Discussion: Course of action  |
|01.10.2014 | 5h  | Theo  | Login/logout, UserBean, ServerManager, basic user authentication |
|02.10.2014 | 2h  | Theo  | Added bootstrap, basic design |
|03.10.2014 | 6h  | Theo  | Changed login to use Ajax, added page body, added SubmissionBean, changed site layout to use template |
|07.10.2014 | 5h  | Theo  | Improved bootstrap design, iterating through posts as a list, server is capable to store submissions/user as xml file, added upvote/downvote arrows |
|08.10.2014 | 3h  | Theo  | Upvotes/Downvotes work, storing server data in separate Thread |
| todo  | todo  | todo   | todo  |
|   |   |   |   |
|   |   |   |   |
|   |   |   |   |
