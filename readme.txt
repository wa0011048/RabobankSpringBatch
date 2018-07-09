Introduction:
------------
	This application process the monthly deliveries of customer statement records of rabobank. 
	This information is delivered in two formats, CSV and XML. These records are read , validated and the 
	failure/invalid records are written into a error file.
	
Applcation/Root directory Name  
--------------------------------
SpringBatch

GitHub Location :
------------------
https://github.com/wa0011048/RabobankSpringBatch


Language/tools/framework used:
------------------------------
	- JAVA
	- Spring core, Batch, Boot
	- Gradle
	
How to run
----------
1. Edit the system.properties file to udpate the input and output file paths. /SpringBatch
2. Make sure the input files are kept in the directory specified in above line.
3. Run the main class Main.java as a java program. This will read the file and validates and generate the result files.
4. Run the Unit Test class MainTest.java as a java unit test.