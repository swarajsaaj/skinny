=======
Skinny
======

A local subversioning command line tool, written for those who find git troublesome and difficult to use for simple backups and snapshots of projects.
>The name is derived form being a small tool than git (which is really muscular), but skinny provide you easy to learn four commands and only used for local source management, commit log init restore ..thats it four commands no big deal. But its power lies in its simplicity formanaging Directory and Project States by making Snaposhots using a command line.

>TODO List of Upcoming features
1.Add diff command to see difference
2.See status of changed files
3.Add Distributed pushing and pulling

>If you would like to get in Development or give reviews mail me at swarajsaaj@gmail.com

Installation
--------

###For Windows
1. Download the winDownload folder
2. Rename it to skinny and place it in <code>C:\</code>
3. Add this (<code>C:\skinny\ </code> ) to your system Variables 
	1.To do this run <code>sysdm.cpl</code> (without quotes) from your Start Button Search Box
	2.Go to Advanced Tab
	3.Go to Environment Variables
	4.In System Variables Edit the PATH
	5.In the end append "<code>;C:\skinny</code>" (without quotes) and save
4. You can now use skinny using skn command in command line (cmd.exe)
(or if you are an advanced user , just add skn.bat to environment variables PATH where you have placed it)

###For Linux
1. Download the linuxDownload folder
2. Rename it to skinny and place it in  Root folder (/) 
3. Add this (<code>/skinny/ </code> ) to System Path Variables or alias it
	1.To make an alias use "<code>alias skn='java -jar /skinny/skn.sh</code>'.
	2.Or add "<code>alias skn='java -jar /skinny/skn.sh</code>" to your <code>~/.bashrc file</code>.
	
4. You can now use skinny using <code>skn</code> command in terminal


###Usage

* skn init
> 
>To initialize a repository in the current folder or directory you are working in 
>	e.g.  <code>C:\work\>skn init</code>
>		This will make work folder a skn repository
* skn commit <message>
> 
>To make the snapshot of current state (known as a commit)
>	e.g. <code>C:\work\>skn commit "First state"</code>
* skn restore <commitNumber>

>To restore state of working directory to a previous state with reference to a commit number (from log)
>	e.g.<code>C:\work\>skn restore 14 </code>

* skn log
> 
>To see the log history of previously committed snapshots with date and commit numbers for reference
?	e.g.<code>C:\work\>skn log</code>

* skn help
> 
>To see all this help there

