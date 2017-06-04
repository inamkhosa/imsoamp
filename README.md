# imsoamp Build 

# Check out the all OAMP Package NetBeans project from SVN.
git clone https://github.com/inamkhosa/imsoamp.git

# Build Web Manager
Open NetBeans IDE and point to your local checkout folder. 
The NetBean IDE will recougnize ManagerApp project Select ManagerApp Project. Resolve the Server Problem ( Makesure you have Apache Tomcat installed, in Resolve Server Problem prompt you recieve from NetbeanIDE , Point it your local Tomcat server installation. 
By default building ManagerApp will build SNMPManager and TrapServer projects. 
However if any source code changes are performed on other projects, those project should be built using NetBeans standard build operation and then WebManager project should be rebuild to reflect the latest changes. Before deployment following actions should be considered as pre-requisite:

# MYSQL DATABASE SETUP
MySQL database is used for storing the counters, alarms/faults/traps and configuration related data recieved from Network Elements ( i.e. Media Server, HSS, Softswitch, OCS, CDF etc.) 
Install latest MySQL distribution from MySQL along with latest distribution of MySQL GUI Tools.
Get the latest OAMP MySQL dump from SVN.
Use MySQL Administrator application to create the schema named OAMP and then restore the downloaded dump file.

# APACHE TOMCAT SETUP
Usually NetBeans ships with built-in apache tomcat distribution, however if it isn't available you'll have to download (Apache Tomcat) and register the server in NetBeans. Please refer to NetBeans.org for more details on registering external Tomcat server with the IDE. Server registration is not only used to deploy the application but also to build the application without errors.
After installing tomcat following action must be performed to enable application security: 

•	Open [CATALINA_HOME]/conf/Server.xml and add comment out current realm. Then copy and paste the following realm in the server.xml and change the database server name and password.

<driverName="com.mysql.jdbc.Driver" connectionURL="jdbc:mysql://localhost:3306/oamp? 
user=root&password=rootroot" 
userTable="tbl_users" 
userNameCol="username" 
userCredCol="password" 
userRoleTable="tbl_user_roles" roleNameCol="role_name"
/>

# OPENJMS SETUP
Download and install OpenJMS and add a topic named TrapsTopic using OpenJMS admin application client. This topic is published from OAMP web service and Network Analyzer listens to this topic to get Trap notifications and details instantly. For more information on this visit OpenJMS Official Web Site. WEBMANAGER CONFIGURATIONS SETTINGS
Modify the file in sources: [Project Folder]\src\java\org\ict\oamp\config\appconfig.xml and change the settings to your own MySQL installation settings.
After performing all above tasks, rebuild the project once again. If you want to deploy the application using NetBeans then just simply right click on the project node and click deploy.
If all goes well then application will be available on [tomcat host URL:port /webmanager]
In case some errors were encountered then check Tomcat logs for details.

# TRAP Server Setup
Trap server project deploys as part of the ManagerApp project. To build the application though please reset the libraries path from the project properties.

# SNMP Manager
Like Trap Server project SNMP Manager project also deploys with WebManager and to build the project from NetBeans reset the libraries path from Project Properties menu option. Note: for both projects lib directory in project folders contain all the required third party library jars.

# Network Analyzer Setup
To build the project, Open AdvoSSNetworkAnalyzer NetBeans project from your local checkout directory.
This project depends on various third party library jars contained in lib folder of the project home directory. Also this project is dependent on OAMPServiceClient project.
The OAMPServiceClient NetBeans project is also checked-out in the local checkout dirctory when working for Network Analyzer application.
Network Analyzer is a desktop application so no deployment is required. Just click the NetBeans standard Run command to run the project.
When client is running, click Tools>Settings from menu bar. A dialog box will appear in which provide the WebManager HTTP URL according to your own installation. Leave the service name unchanged.

# imsoapm is licensed under Creative Common License (https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode). 
You can look at https://creativecommons.org/licenses/by-nc-sa/4.0/ for non-commercial usage. 
