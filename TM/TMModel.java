import java.io.Serializable;
import java.io.*; //is it good practice to import the whole thing?
import java.time.*;

public TMModel implements ITMModel {

    // set information in our model
    //
    //TODO: boolean startTask(String name);
    //TODO: boolean stopTask(String name);
    //TODO: boolean describeTask(String name, String description);
    //TODO: boolean sizeTask(String name, String size);
    //TODO: boolean deleteTask(String name);
    //TODO: boolean renameTask(String oldName, String newName);

    // return information about our tasks
    //
    //TODO: String taskElapsedTime(String name);
    //TODO: String taskSize(String name);
    //TODO: String taskDescription(String name);

    // return information about some tasks
    //
    //TODO: String minTimeForSize(String size);
    //TODO: String maxTimeForSize(String size);
    //TODO: String avgTimeForSize(String size);

    //TODO: Set<String> taskNamesForSize(String size);

    // return information about all tasks
    //
    //TODO: String elapsedTimeForAllTasks();
    //TODO: Set<String> taskNames();
    //TODO: Set<String> taskSizes();
}



public class Task implements Serializable{
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

Duration{
	//list of start times
	//list of end times
	//
}