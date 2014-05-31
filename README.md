skinny
======

A local subversioning command line tool, written for those who find git troublesome and difficult to use for simple backups and snapshots of projects.

Installation
--------

###For Windows
1. Download the winDownload folder
2. Rename it to skinny and place it in C:\
3. Add this (C:\skinny\) to your system Variables 
	1.To do this run sysdm.cpl (without quotes) from your Start Button Search Box
	2.Go to Advanced Tab
	3.Go to Environment Variables
	4.In System Variables Edit the PATH
	5.In the end append ";C:\skinny" (without quotes) and save
4. You can now use skinny using skn command in command line (cmd.exe)

###For Linux
1. Download the linuxDownload folder
2. Rename it to skinny and place it in  Root folder (/) 
3. Add this (/skinny/) to System Path Variables or alias it
	1.To make an alias use "alias skn='java -jar /skinny/skn.jar'
	2.Or add "alias skn='java -jar /skinny/skn.jar" to your ~/.bashrc file
	
4. You can now use skinny using skn command in terminal


###Usage

* skn init
>To initialize a repository in the current folder or directory you are working in 
	e.g.  <code>C:\work\>skn init</code>
		This will make work folder a skn repository
* skn commit <message>
>To make the snapshot of current state (known as a commit)
	e.g. <code>C:\work\>skn commit "First state"</code>
* skn restore <commitNumber>
>To restore state of working directory to a previous state with reference to a commit number (from log)
	e.g.<code>C:\work\>skn restore 14
* skn log
>To see the log history of previously committed snapshots with date and commit numbers for reference
	e.g.<code>C:\work\>skn log</code>