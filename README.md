### BUILT USING:
1. Cucumber- for BDD framework
2. Selenium - for Web Automation, cross browser testing
3. Extent Reports - for HTML reporting
4. TestNG - for parallel execution and automatic rerun of failed test cases
5. Maven - for project/dependency management
6. SLF4J - for logs
7. POI - for handling Excel test data
8. PDFBox - for handling PDFs

### STEPS TO INSTALL:
1. Download and install JDK latest stable version
2. Download Node JS 
3. Download Maven
4. Download and install Eclipse IDE for JAVA Web Developers
5. Set the environment variables in the OS


	1. WINDOWS -> Set the following SYSTEM environment variables for JAVA, Maven & NODE	
		JAVA_HOME -> 	C:\Program Files\Java\jdk-11.0.13
		NODE_HOME -> 	C:\Program Files\nodejs
		MAVEN_HOME -> 	C:\apache-maven-3.8.4
		PATH -> 
		%MAVEN_HOME%
		%MAVEN_HOME%\node_modules\npm\bin
		%MAVEN_HOME%\bin
		%JAVA_HOME%\bin		
	2. MACOS->
		1. Open the bash profile in the TERMINAL,
			vi ~/.bash_profile
		2. Use the below set of commands to save the environment variables
			Hit "i" to insert and enter the below set of statements			
			export JAVA_HOME=$(/usr/libexec/java_home)
			export MAVEN_HOME=/Users/<user_name>/Documents/apache-maven-3.6.3
			export M2=$MAVEN_HOME/bin
			export PATH=$PATH:/usr/local/git/bin:/usr/local/bin:			
			Hit "ESC" ":wq"			
			We need not explicitly add path for Node since Node & Python are present in the /usr/local/bin and we have already included them in the PATH variable as displayed above.
		3. Use the below command to source the bash_profile
			source ~/.bash_profile

### FEATURES:
1. Parallel Execution
2. Automatic Rerun of failed test cases
3. HTML Reports (Extent reports, Cucumber Reports)
4. Logger mechanism
5. This can be directly integrated with Jenkins
6. Cross Browser Execution
7. Can be tweaked to execute in Cloud/grid

### HOW TO EXECUTE:
1. Extract the Zip
2. Use Eclipse IDE to import the project
3. Right click on the pom.xml and click on Maven -> Update Project
4. To execute, Right click on the pom.xml and click on Run -> Maven Test

### WHERE TO FIND:
* Feature files are found in "src/test/resources/features" folder
* Step definitions are found in "src/test/java/stepDefinitions" folder
* Locators are found in "src/test/java/locators" folder
* Actions are found in "src/test/java/Actions" folder
* Reports are found in "test-output/Reports/Spark_DDMMYYYY" folder
* Logs are found in "test-output/Logs" folder