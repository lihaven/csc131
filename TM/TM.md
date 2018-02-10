Document by **Lindsay Haven**, created for CSC 131, CSUS
# File and Code Organization
All classes are in a single file because that is a requirement of the assignment. This is so that each student's work is contained in an easy to parse format. This may not be good for the organization of a bigger application, but in the context of the class it is better organization of all students work.

Why implement a task class? For isolation. This program should be extensible and will be expanded upon. Only one class can be public, so the Task class is not public.

# Control Structures
Please not that I don't often code without speicific implementation instructions. In most cases it's likely that I could be more clever or efficient with the Java language. This is because I haven't worked on many (if any) projects that allowed me to use the full suite of feautres provided by the Java API and so I don't know how to use many of them.

I wished for a control structure that would allow me to execute various different commands until it failed an expression.
For instance:
if (a >= 1) statement 1;
and if (a >=2) statment 2;
and if (a >= 3) statement 3;
This would be to avoid the repeated statemetns in the argument parsing in appMain.

# API
The Time package was new to me, I used this site for information on implementation.
http://www.oracle.com/technetwork/articles/java/jf14-date-time-2125367.html

Please note that I don't often code without speicific implementation instructions. In most cases it's likely that I could be more clever or efficient with the Java language. This is because I haven't worked on many (if any) projects that allowed me to use the full suite of feautres provided by the Java API and so I don't know how to use many of them. For this reason it took a lot of effort to get serializing and date and times working correctly.

# Exception Handling
I'm pretty terrible at knowing where I should implement try catch blocks. I only vaguely understand what they do and so I can't use any knowledge about them to understand where they would be appropriate to use. This is something I need to work on. 

# Serializing
I chose to serialize my data for a couple of reasons. I heard a student ask if it would be okay to use, and while the instructor didn't seem excited about that implementation, the student was told it would be okay to do that. When consulting an experienced software engineer, serialization was the first suggestion and the suggestion he stuck with after pressure to think of other implementation options. Finally it was the majority of top suggestions that came up in a Google search about how to save an ArrayList to a file.
My primary source for learning how to serialize a class: https://www.geeksforgeeks.org/serialization-in-java/
I also consulted with: http://periodic.github.io/Resume/resume.html

# Tokenizing
I didn't end up tokenizing strings and saving them to a data file. When writing to a file was first suggested in class I didn't understand what the parameters were in the meaning of that instruction. Technically I am using a file to store data and so I didn't seem like I was on the wrong path. However, when it was explained in more detail on Friday I then realized I was employing a different strategy than what the instructor was invisioning. However, it didn't seem like this was a problem for the application desgin, and the specifications say this part of the implimentation is flexible, so I moved forward with the implementation I had. One downside I found was that I couldn't manually alter data in a the text file. While that is good for programs with causual users, being able to directly modify the data may have been useful for testing.

# Log Class
Because I used the seralizing method, there was very little I needed to write to handle the logging functionality. Therefore, I did not write a seperate class for logging.

# Scalability
This process seems inefficient to read the entire set of data from a file each time a small process is run (start/stop). Seems fine for a small program, but would be bad for scalability to a larger program. If the project were to become larger some other data storage method would need to be considered.

# Additional Features
During the process of implementing and testing there were several features that I had wished for or thought would be useful to a user of this program.
- Delete tasks
- Edit/update additional task fields including
> Name
> Start/Stop times
> More collaberation 
> - rarely does a person work alone on a programming project where they also need to track tasks
- Add a way to see if a new task is in progress or not (fixes the problem of a started, brand new task, in progress, summary showing no times and no indication the start task worked).

# Test Cases
- where a log file exists and <task name> is 
> a non-existing task
> an existing task
- where no log file exists yet and <task name> is
> a non-existing task
> an existing task
java TM start <task name>
java TM stop <task name>
java TM describe <task name> <description>
> Additionally: when description is alreay present, and when it's not
java TM summary <task name>
java TM summary 
