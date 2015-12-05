Download WildFly
---------------
Download Wildfly 8.2.0 from here: <http://download.jboss.org/wildfly/8.2.0.Final/wildfly-8.2.0.Final.zip>  

Set WILDFLY_HOME
---------------

set `WILDFLY_HOME` with the actual path to your WildFly installation.

Start the WildFly Server with the Full Profile
---------------

1. Open a command prompt and navigate to the root of the WildFly directory.
2. The following shows the command line to start the server with the full profile:

        For Linux:   WILDFLY_HOME/bin/standalone.sh -c standalone-full.xml
        For Windows: WILDFLY_HOME\bin\standalone.bat -c standalone-full.xml
        
Build and Deploy
-------------------------

1. Make sure you have started the WildFly server as described above.
2. Open a command prompt and navigate to the root directory of this quickstart.
3. Type this command to build and deploy the archive:

        mvn clean install wildfly:deploy
        
Undeploy the Archive
--------------------

1. Make sure you have started the WildFly server as described above.
2. Open a command prompt and navigate to the root directory of this quickstart.
3. When you are finished testing, type this command to undeploy the archive:

        mvn wildfly:undeploy