/* Program by Lindsay Haven, for CSC131, CSUS.
 *
 * This application was designed to keep track of the time spent on named tasks. 
 * It also allows the user to describe a task. This implementation of the program 
 * was meant to be the first of many sprints.
 * 
 * The program allows a user to start, stop, describe, and print summaries of 
 * tasks.Although there will be more iterations on this program, good user 
 * facing messages are lacking.
 */ 

import java.util.*;
import java.io.*; 
import java.time.*;

public class TM{
	
	public static void main(String args[]){
		TM tm = new TM();
		tm.appMain(args);
	}
	
	public void appMain(String args[]){
		new LogEntry(args);		
		ITMModel taskManager = new TMModel();
						
		//args[0] is the case command
		//args[1] is a name of the task to edit
		//args[2] is the new description or size
		//args[3] is an optional size update for a task
				
		switch(args[0]){
			
			case "start": 		taskManager.startTask(args[1]); break;
			
			case "stop": 		taskManager.stopTask(args[1]); break;
						
			case "describe": 	taskManager.describeTask(args[1],args[2]); break;
						
			case "size":		taskManager.sizeTask(args[1],args[2]); break;
			
			case "delete":    taskManager.deleteTask(args[1]); break;
				
			case "rename":    taskManager.renameTask(args[1],args[2]); break;
			
			case "summary":   if(args.length == 1){
										Set set = taskManager.taskNames();
										Iterator<String> itr = set.iterator();
        								while(itr.hasNext()){
            							summarizeTask(taskManager.findTask(itr.next()));
        								}
									} else {
										summarizeTask(taskManager.findTask(args[1]));		
									}
									break;
		}	
		
		taskManager.close();
		
	}




	public void printUsage(){
			System.out.println("\tSomething went wrong.");//replace with usage description
	}
	
	public boolean cmdStart(Task task){ 
		if(!task.start()) {
			return true;
		}
		return false;
	}

	public boolean cmdStop(Task task){ //probably need to roll this into the Task class
		if(!task.stop()){
			return true;
		}
		return false;
	}

	void summarizeAllTasks(){
		//taskNames //a set of task names
		//for each item in the arrayList
			//call summarizeTask
		//taskSizes //a set of task sizes
			//print statistics for all tasks and task sizes
	}
	
	public void summarizeTask(Task task){
		//Task task = taskManager.findTask(name);
		System.out.println("Task name: \t" + task.getName());
		System.out.println("Task size: \t" + task.getSize());
		System.out.println("Time spent:\t" + task.getDuration());
		for(int i = 0; i < task.numSessions(); i++){
			System.out.print("Session " + i + ":\t");
			System.out.println(task.getStart(i) + " to " + task.getStop(i));
		}
		System.out.println("Description:\t" + task.getDescription());
	}

	public boolean cmdDescribe(Task task, String details){
		task.setDescription(details);
		return true;
	}

	public boolean cmdSize(Task task, String details){
		ArrayList<String> sizes = new ArrayList<String>();
		String[] temp = new String[] {"XS", "S", "M", "L", "XL"};
		sizes.addAll(Arrays.asList(temp));

		if (sizes.contains(details)) {
			task.setSize(details);
			System.out.println(true);
			return true;
		} else {
			System.out.println(false);
			return false;
		}
	}
	
	public boolean saveData(String fileName, ArrayList<Task> list){
		try {
			FileOutputStream file = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(list);
			out.close();
			file.close();
			return true;
		} catch (IOException ex) {
			return false;
		}
	}

	public ArrayList<Task> getData(String filename) {
		try {
			File file = new File(filename);
			if (file.exists()) {
				FileInputStream is = new FileInputStream(file);
				ObjectInputStream in = new ObjectInputStream(is);
				ArrayList<Task> list = (ArrayList<Task>)in.readObject();
				in.close();
				is.close();
				return list;
			} else {
				ArrayList<Task> list = new ArrayList<Task>();
				saveData(filename, list);
				return  list;
			}
		} catch(IOException ex){
			System.out.println(ex);
			System.exit(0);
			return null;
		} catch(ClassNotFoundException ex){
			System.out.println(ex);
			System.exit(0);
			return null;
		}
	}

	public int findIndex(ArrayList<Task> list, String data){
		int dataIndex = -1;
		for(int i=0; i < list.size(); i++){
			if(list.get(i).getName().equals(data)){
				dataIndex = i;
			}
		}
		return dataIndex;
	}

}

class LogEntry{
	LogEntry(String args[]){
		//new string
		String entry = LocalDate.now() + " " + LocalTime.now() + " ";
		//for each command, append to string
		for(String cmd: args){
			entry = entry + " " + cmd + "\n";		
		}
		//write string
		write(entry);
	}	
	
	
	public void write(String toWrite) {
		//https://stackoverflow.com/questions/10667734/java-file-open-a-file-and-write-to-it
   	File file = new File("TMCmd.log");
    	FileWriter writer;
    	try {
    			writer = new FileWriter(file, true);
        		PrintWriter printer = new PrintWriter(writer);
        		printer.append(toWrite);
        		printer.close();    		
    	} catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
     	}
    }	
}

/* Not used right now
	void printResults(String cmd, String name, boolean success){
		switch (cmd) {
			case "start":
				if(success){
					System.out.println("\t" + name + " is now in progress.");
				} else {
					System.out.println("\t" + name + " could not be started.");
				}
			case "stop":
				if(success){
					System.out.println("\t" + name + " is no longer in progress.");
				} else {
					System.out.println("\t" + name + " could not be found.");
				}
			case "describe":
			case "summary":
			default:
		}
	}


*/