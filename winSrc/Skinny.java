

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;


class skinnyDB implements Serializable{
   
    int latestCommitNo=0;
    
    ArrayList<String[]> commits=new ArrayList<>();
    
    public void getLog()
    {    
        System.out.println("Skinny Commit Log: \n---------------------------");
        for(String[] commit: commits)
        {
            if(commit[3].equals("commit"))
            {
               System.out.println("Commit No: "+commit[0]);
            }
            else if(commit[3].equals("restore"))
            {
                System.out.println("--Restore--");
            }
            System.out.println("Message: "+commit[1]);
            System.out.println("Date : "+commit[2]);
            System.out.println("---------------------------");
        }
    }
    public void addCommit(String message,String date)
    {
    
        String[] data={ ""+this.latestCommitNo,message,date,"commit"};
        commits.add(data);
        this.latestCommitNo+=1;
    }
     public void addRestore(String message,String date)
    {
    
        String[] data={ ""+this.latestCommitNo,message,date,"restore"};
        commits.add(data);
      //  this.latestCommitNo+=1;
    }
            
    
}
public class Skinny {

    static File[] trackedFiles;

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        
        String command=args[0];
       // String command="log";
        
     
      switch(command)
      {
       
          case "init":
              initialize();  //copy all files as per correct directory listings and add MD% checksums too.
              break;
//          case "status":
//             
//              checkStatusdummy(new File("./"));   // ,new File("./.skinny/init"));
//              System.out.println("Dirs: "+dirs.toString());
//              System.out.println("Files: "+files.toString());
//              break;
           case "restore":
               String restoreNumber=args[1];
               int restoreNo=Integer.parseInt(restoreNumber);
               restore(restoreNo);
               break;
           case "commit":
              //commit here in new directory
               String commitMessage=args[1];
               commit(commitMessage);
              break;
           case "log":
               skinnyDB db=getDB();
               db.getLog();
               break;
            case "help":
                System.out.println("Skinny Help :- \n Skinny is a local Source Management Tool for making snapshots and restoring them when required. ");
                System.out.println("You can use following commands:-");
                System.out.println("------------");
              System.out.println("skn init");
              System.out.println("\t To initialize a Skinny Repository inside the folder");
              
              
              System.out.println("------------");
              System.out.println("skn commit <message> ");
              System.out.println("\t To record the snapshot of your current folder state with a message(preffered withing quotes)");

              System.out.println("------------");
              System.out.println("skn restore <commitNumber> ");
              System.out.println("\t To restore the folder to a previous state using the commit Number");

              System.out.println("------------");
              System.out.println("skn log");
              System.out.println("\t To see the lof of previously made commit along with commitNumbers date and messages");
              
              
            break;              
              
          default:
              System.out.println("\t"+command+" not a valid command");
          
      }
      
      
        
    }
    
    public static void initialize() throws IOException
    {
        String pwd = System.getProperty("user.dir");

        
        //make the new skinny hidden repo
        File init = new File(".//.skinny");
        Runtime.getRuntime().exec("attrib +H .skinny");
        
        
        trackedFiles = (new File(".")).listFiles();

        if (trackedFiles[0].getName().equals(".skinny")) 
        {
            System.out.println("Skinny already Initialized");
            return;
        }
        
        if (init.mkdir())
        {
            System.out.println("Skinny initialized in " + pwd);
        }
        
          //copy files to .skinny/init
        File source=new File("./");
        File dest=new File("./.skinny/0");
        dest.mkdir();
        
        copyFolder(source,dest);
        
        System.out.println("Files being Tracked are:-");
        for (File indiFile : trackedFiles) 
        {
            if (indiFile.getName().equals(".skinny"))
            {
                continue;
            }
           
            if(indiFile.isDirectory())
            {
                System.out.println("\t [D] " + indiFile.getName());
            }
            else
            {
            System.out.println("\t " + indiFile.getName());
            }
        }
        
        DateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal=Calendar.getInstance();
        String date=df.format(cal.getTime());
        skinnyDB db=new skinnyDB();
        db.addCommit("Initial Stage",date);
        
        //serialize object db
        FileOutputStream fo=new FileOutputStream("./.skinny/db.ser");
        ObjectOutputStream o=new ObjectOutputStream(fo);
        o.writeObject(db);
        o.close();
        fo.close();
        
    }
    
    public static void copyFolder(File source,File destination) throws FileNotFoundException, IOException
    {
        if(source.getName().equals(".skinny"))
        {
            return;
        }
        if(source.isDirectory())
        {
            if(!destination.exists())
            {
                destination.mkdir();
            }
            
            String[] sourceFiles=source.list();
            for(String file: sourceFiles)
            {
                File srcFile=new File(source,file);
                File destFile=new File(destination,file);
                copyFolder(srcFile,destFile);
            }
        }
        else
        {
            InputStream in=new FileInputStream(source);
            OutputStream out=new FileOutputStream(destination);
            
            byte[] buffer=new byte[1024];
            int length;
            while((length=in.read(buffer))>0)
            {
                out.write(buffer,0,length);
            }
            in.close();
            out.close();
            
            
        }
    }
    
    @SuppressWarnings("empty-statement")
    public static String checkSum(String path) throws NoSuchAlgorithmException{
        String checksum = null;
        try {
            FileInputStream fis = new FileInputStream(path);
            MessageDigest md = MessageDigest.getInstance("MD5");
          
            //Using MessageDigest update() method to provide input
            byte[] buffer = new byte[8192];
            int numOfBytesRead;
            while( (numOfBytesRead = fis.read(buffer)) > 0){
                md.update(buffer, 0, numOfBytesRead);
            }
            byte[] hash = md.digest();
            checksum = new BigInteger(1, hash).toString(16); //don't use this, truncates leading zero
        } catch (Exception ex) {
            System.out.println("Exception");
            ex.printStackTrace();
        }
          
       return checksum;
    }


    
    public static void checkStatus(File source,File destination) throws NoSuchAlgorithmException  // for comparison
    {
        if(source.getName().equals(".skinny"))
        {
            return;
        }
        if(source.isDirectory())
        {
            //if it is a directory .. call checkstatus again to this folder
           System.out.println("Checking inside: "+source.getPath());
            String[] files=source.list();
            for(String file: files)
            {
                if(source.getName().equals(".skinny"))
                {
                    return;
                }
                File argSrc=new File(file);
                File argDest=new File("./.skinny/init/"+file);
                if(source.getName().equals("./"))
                {
                    argDest=new File("./.skinny/init/"+source.getName()+"/"+file);
                }
                System.out.println("\t Checking for "+argSrc.getPath()+" and "+argDest.getPath());
                checkStatus(argSrc,argDest);
            }
        }
        else
        {
            System.out.println("File Check:  "+source.getPath()+" & "+destination.getPath());
            String srcMD=checkSum(source.getPath());
            String destMD=checkSum(destination.getPath());
           if(!srcMD.equals(destMD))
            {
                System.out.println("Changed File :"+source.getPath()); 
                System.out.println("srcMd: "+srcMD);
                System.out.println("destMd: "+destMD);
            
            }
            else
            {
                System.out.println("No Change ");
            }
        }
    }
    
    public static skinnyDB getDB() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        FileInputStream fi = new FileInputStream("./.skinny/db.ser");
        ObjectInputStream oi = new ObjectInputStream(fi);

        skinnyDB dbInflated = (skinnyDB) oi.readObject();
        return dbInflated;

    }
    
    public static void packDB(skinnyDB db) throws FileNotFoundException, IOException
    {
         FileOutputStream fo=new FileOutputStream("./.skinny/db.ser");
        ObjectOutputStream o=new ObjectOutputStream(fo);
        o.writeObject(db);
        o.close();
        fo.close();
    }
    
    public static void commit(String message) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        
        skinnyDB db=getDB();
        try{
            File curr=new File("./");
            File newCommitFolder=new File("./.skinny/"+(db.latestCommitNo));
            copyFolder(curr,newCommitFolder);
        }
        catch(Exception e)
        {
            System.out.println("Commit not made");
            e.printStackTrace();
            return;
        }
        
        DateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal=Calendar.getInstance();
        String date=df.format(cal.getTime());
        db.addCommit(message, date);
        packDB(db);
        
        System.out.println("\tCommit Successfully made\n\tCommit No:"+(db.latestCommitNo-1));
    }

    public static void restore(int restoreNumber) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        
        skinnyDB db=getDB();
        DateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal=Calendar.getInstance();
        String date=df.format(cal.getTime());
        
        db.addRestore("Restore from commit no.: "+restoreNumber, date);
        packDB(db);
        
       try{
            File curr=new File("./");
            File oldCommitFolder=new File("./.skinny/"+restoreNumber);
            
            File[] files=curr.listFiles();
            
            for(File file:files)
            {
                if(file.getName().equals("./"))
                {
                    continue;
                }
                if(file.getName().equals(".skinny"))
                {
                    continue;
                }
                file.delete();
            }
            
            copyFolder(oldCommitFolder,curr);
        }
        catch(Exception e)
        {
            System.out.println("Restore not made");
            e.printStackTrace();
            return;
        }
       
        System.out.println("\n\n\tRestore Succesfully done!!");
       
    }
    

     
}

