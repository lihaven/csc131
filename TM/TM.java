/* Program by Lindsay Haven, for CSC131, CSUS.
 *
 * This application was designed to keep track of the time spent on named tasks. 
 * It also allows the user to describe a task.  This impliemtation of the program 
 * was meant to be the first of many sprints.
 * 
 * The program allows a user to start, stop, describe, and print summaries of tasks. 
 * Although there will be more itterations on this program, good user facing messages
 * are lacking, even for a first sprint.
 */ 


import java.time.*;
import java.util.ArrayList;
import java.io.Serializable;
import java.io.*; //is it good practice to import the whole thing?

public class TM{
	
	public void appMain(String args[]){
		String cmd = null, name = null, details = null, details2 = null;
	
		if(args.length == 1){
				cmd = args[0];
		}else if(args.length == 2){
				cmd = args[0];
				name = args[1];
		}else if(args.length == 3){
				cmd = args[0];
				name = args[1];
				details = args[2];
		}else if(args.length == 4){
				cmd = args[0];
				name = args[1];
				details = args[2];
				details2 = args[3];
		}else {
				printUsage();
				System.exit(0);
		}

		String output = "";
		ArrayList<Task> taskList = new ArrayList<Task>();
		String fileName = "TM.log";
		
		taskList = getData(fileName);

		int taskIndex = -1;
		boolean successStatus = false; 
			//Note: successStatus not used to manage success/failure output to user 
			//at this time. Maybe in the next update of this program.
		switch (cmd){
        	case "start":
				taskIndex = findIndex(taskList, name);
				if(taskIndex >= 0){
					successStatus = cmdStart(taskList.get(taskIndex));
				}else{
					taskList.add(new Task(name)); //add to the end of the list
					successStatus = cmdStart(taskList.get(taskList.size()-1));
				}
				break;
			case "stop":  		
				taskIndex = findIndex(taskList, name);
				if(taskIndex >= 0){
					successStatus = cmdStop(taskList.get(taskIndex));
				}else{
					successStatus = false;
			    }
				break;
			case "describe": 
				taskIndex = findIndex(taskList, name);
				if(taskIndex >= 0) {
					successStatus = cmdDescribe(taskList.get(taskIndex), details);
					if(args.length == 4){
						cmdSize(taskList.get(taskIndex), details2);
					}
				} else {
					output = "Task doesn't exist.";
				}		
			    break;
			case "summary":
				if(name != null){
					taskIndex = findIndex(taskList, name);
					if(taskIndex >= 0){
						cmdSummaryTask(taskList.get(taskIndex));
					}
				} else {
					Duration totalTimeSpent = Duration.ofSeconds(0);
					for(Task element : taskList){
						cmdSummaryTask(element);
						totalTimeSpent = totalTimeSpent.plus(element.getTotalTimeSpent());
						System.out.println();
					}
					System.out.println("Time spent on all tasks:\t" + totalTimeSpent);
				}
				break;
			case "size":
				if(name != null){
					taskIndex = findIndex(taskList, name);
					if(taskIndex >= 0) {
						cmdSize(taskList.get(taskIndex), details);
					} else {
						output = "Task doesn't exist.";
					}		
				}
				break;
			default:  
				break;
		}

		saveData(fileName, taskList);
		
		//printResults(cmd, name, successStatus);

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

	public static void main(String args[]){
		TM tm = new TM();
		tm.appMain(args);
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

	public void cmdSummaryTask(Task task){
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
		task.setSize(details);
		return true;
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

//Duration oneDay = Duration.between(today, yesterday);
//LocalDateTime timePoint = LocalDateTime.now();

class Task implements Serializable{
	String name;
	String description;
	String size;
	ArrayList<LocalDateTime> startTimes = new ArrayList<LocalDateTime>(); 
	ArrayList<LocalDateTime> stopTimes 	= new ArrayList<LocalDateTime>();
	Duration timeSpent;

	Task(String name){
		this.name = name;
		timeSpent = Duration.ofSeconds(0);
	}

	Task(String name, String description){
		this(name);
		this.description = description;
	}

	Task(String name, String description, String size){
		this(name, description);
		this.size = size;
	}

	String getName(){
		return name;
	}

	void setDescription(String description){
		this.description = description;
	}
	
	String getDescription(){
		return description;
	}
	
	LocalDateTime getStart(int index){
		return startTimes.get(index);
	}

	LocalDateTime getStop(int index){
		return stopTimes.get(index);
	}

	Duration getTotalTimeSpent(){
		return timeSpent;
	}

	void setSize(String size){
		this.size = size;
	}
	
	String getSize(){
		return size;
	}
	boolean inProgress(){
		if (startTimes.size() != stopTimes.size()) return true;
		return false;
	}

	int numSessions(){
		return stopTimes.size();
	}

	boolean start(){
		if(!this.inProgress()){
			startTimes.add(LocalDateTime.now());
			return true;
		}
		return false;
	}

	boolean stop(){
		if(this.inProgress()){
			stopTimes.add(LocalDateTime.now());
			int i = stopTimes.size()-1;
			Duration newDuration = Duration.between(stopTimes.get(i),startTimes.get(i));
			timeSpent = timeSpent.plus(newDuration);
			return true;
		}
		return false;
	}
}
