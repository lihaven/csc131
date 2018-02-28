import java.io.Serializable;
import java.io.*; //is it good practice to import the whole thing?
import java.time.*;
import java.util.*;

public class TMModel implements ITMModel {

	 // constructors
	 TMModel(){		 
	 }	
	
    // set information in our model
    //
    public boolean startTask(String name) {return true;}
    public boolean stopTask(String name) {return true;}
    public boolean describeTask(String name, String description) {return true;}
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
    public Set<String> taskNames(){Set<String> set = new TreeSet<String>(); return set;}
    public Set<String> taskSizes(){Set<String> set = new TreeSet<String>(); return set;}
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

	void setDescription(String description){
		if(this.description == null){
			this.description = description;
		} else {
			this.description = this.description + " " + description;
		}
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

class TaskDuration{
	
	TaskDuration(){
	}
	//list of start times
	//list of end times
	//
}