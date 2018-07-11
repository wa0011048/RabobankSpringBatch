# Introduction:

	This application process the monthly deliveries of customer statement records of rabobank. 
	This information is delivered in two formats, CSV and XML. These records are read , validated and the 
	failure/invalid records are written into a error file.
	
# Language/tools/framework used:

	- JAVA
	- Spring core
	- Spring Batch
	- Spring Boot
	- Gradle
	
# Application Structure

Root Directory : RabobankSpringBatch
source : src/main/java
test : src/main/test

# GitHub Location 

https://github.com/wa0011048/RabobankSpringBatch

	
# How to run in IDE

1. Import the project using the .project file available
2. Edit the system.properties file to udpate the input and output file paths. /RabobankSpringBatch/system.properties
3. Make sure the input files are kept in the directory specified in above line.
4. Run the main class Main.java as a java program. This will read the file and validates and generate the result files.
5. Run the Unit Test class MainTest.java as a java unit test.

# How to run using Gradle build tool:

1. Run 'gradle bootrun' from the root directory /RabobankSpringBatch/ to run the application to read the files and write the response files.
2. Run 'gradle test' from the root directory /RabobankSpringBatch/  to run the test cases. and the results will be placed under folder - /RabobankSpringBatch/build/reports/tests/test/classes/

# Log Files: 

1. Logs are written to the file & stdout using log4j. Edit the file location in log4j.properties to point to the local server.
