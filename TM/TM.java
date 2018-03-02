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

import java.util.Arrays;
import java.util.ArrayList;
import java.io.Serializable;
import java.io.*; //is it good practice to import the whole thing?
import java.time.*;

public class TM{
	
	public static void main(String args[]){
		TM tm = new TM();
		tm.appMain(args);
	}
	
	public void appMain(String args[]){
		ITMModel taskManager = new TMModel();
		
		//args[0] is the case command
		//args[1] is a name of the task to edit
		//args[2] is the new description or size
		//args[3] is an optional size update for a task
		
		switch(args[0]){
			
			case "start": 		 taskManager.startTask(args[1]); break;
			
			case "stop": 		 taskManager.stopTask(args[1]); break;
						
			case "describe": 	 taskManager.describeTask(args[1],args[2]); break;
						
			case "size":		 taskManager.sizeTask(args[1],args[2]); break;
			
			case "delete":     taskManager.deleteTask(args[1]); break;
				
			case "rename":     taskManager.renameTask(args[1],args[2]); break;
			
			case "summary":    System.out.println("Summary to come later.");  break;
				//TODO				
				//the task list isn't here any more, but how do I have this
				//class manage the display?
		}		

	}

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

	void summarizeTask(Task task){
	}
	
	void summarizeAllTasks(){
		//taskNames //a set of task names
		//for each item in the arrayList
			//call summarizeTask
		//taskSizes //a set of task sizes
			//print statistics for all tasks and task sizes
	}
	
	

	public void summaryOfTask(Task task){
		System.out.println("Task name: \t" + task.getName());
		System.out.println("Task size: \t" + task.getSize());
		System.out.println("Time spent:\t" + task.getTotalTimeSpent());
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


