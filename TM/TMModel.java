import java.io.*;
import java.time.*;
import java.util.*;

public class TMModel implements ITMModel {
	 Encoder dataStore;
	 ArrayList<Task> taskList;

	 
	 // constructor & deconstructor
	 //
	 TMModel(){		 
			this.dataStore = new Encoder();
	 		this.taskList = dataStore.read();
	 }	
	 
	 public void close(){
			dataStore.write(taskList);	 
	 }
	
    // set information in our model
    //
    public boolean startTask(String name) { 
    	return findTask(name).start(); }
    public boolean stopTask(String name) { 
    	return findTask(name).stop(); }
    public boolean describeTask(String name, String description) { 
    	return findTask(name).setDescription(description);}
    public boolean sizeTask(String name, String size) {return true;}
    public boolean deleteTask(String name) {return true;}
    public boolean renameTask(String oldName, String newName) {return true;}

    // return information about our tasks
    //
    public String taskElapsedTime(String name) {return "";}
    public String taskSize(String name) {return "";}
    public String taskDescription(String name) {return "";}

    // return information about some tasks
    //
    public String minTimeForSize(String size) {return "";}
    public String maxTimeForSize(String size) {return "";}
    public String avgTimeForSize(String size) {return "";}

    public Set<String> taskNamesForSize(String size) {Set<String> set = new TreeSet<String>(); return set;}

    // return information about all tasks
    //
    public String elapsedTimeForAllTasks() {return "";}
    public Set<String> taskNames(){ 
    	Set<String> set = new TreeSet<String>(); 
		for(Task task: taskList){
			set.add(task.getName());		
		}
		return set;
    }
    public Set<String> taskSizes(){Set<String> set = new TreeSet<String>(); return set;}
    
    public Task findTask(String name){
    		for(Task task: taskList){
					if(task.getName() == name)
					return task;
			}
			Task newTask = new Task(name);
			taskList.add(newTask);
			return newTask;
    }
}

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

	Boolean setDescription(String description){
		if(this.description == null){
			this.description = description;
		} else {
			this.description = this.description + " " + description;
		}
		return true;
	}
	
	String getDescription(){
		return description;
	}
	
	int numStartEntries(){
		return startTimes.size();
	}
	
	int numStopEntries(){
		return stopTimes.size();
	}
	
	LocalDateTime getStart(int index){
		return startTimes.get(index);
	}

	LocalDateTime getStop(int index){
		return stopTimes.get(index);
	}

	Duration getDuration(){
		return timeSpent;
	}
	
	void setDuration(Duration time){
		this.timeSpent = time;
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

class TaskDuration{
	
	TaskDuration(){
	}
	//list of start times
	//list of end times
	//
}

class Encoder{
	String fileName;
	String stringList = "";
	String objDelim = "_,_";
	String lstDelim = "_,_";
	
	Encoder(){
		this.fileName = "TMLog.log";
	}
	
	ArrayList<Task> read(){
		ArrayList<Task> list = new ArrayList<Task>();
		try {
			File file = new File(fileName);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				list.add(parseLinetoTask(line));
			}
			fileReader.close();
			return list;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	boolean write(ArrayList<Task> list){
		try {		
			File file = new File(fileName);
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		
			for(Task task: list){ //TODO convert a bunch of these to string!
				//All items followed by delimiter to stringList
				stringList += task.getName() + objDelim;//add string name				
				stringList += task.getSize() + objDelim;//size
				stringList += task.getDescription() + objDelim;//description
				stringList += task.getDuration().toString() + objDelim;//total duration
				/*for(int i=0; i < task.numStartEntries(); i++){ //series of start and end times
					stringList +=task.getStart(i) + lstDelim;
					if(i < task.numStopEntries()){
						stringList += task.getStop(i) + lstDelim;			
					}
				}*/
				//stringList += "\n";
				
				
				bufferedWriter.write(stringList);
				bufferedWriter.newLine();
				bufferedWriter.flush();
				stringList = "";
			} 
			
			System.out.println("\n\n");
			System.out.println(stringList); //TEST LINE
			bufferedWriter.write(stringList);
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
			
		return true;
	}
	
	//create a function that takes a line from a file and parses it into a task, returns the task 
	Task parseLinetoTask(String line){
		String[] tokens = line.split("_,_");
		Task task = new Task(tokens[0], tokens[2], tokens[1]);
		System.out.println(tokens[3]);
		task.setDuration(Duration.parse(tokens[3]));
		return task;	
			
	}
}