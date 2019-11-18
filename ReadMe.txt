
In order to deploy - you must have the following profile setup in your maven settings.xml file
(See https://maven.apache.org/settings.html)

The tomcat user must have "manager-script" role
Set POSTGRESQL user name and password for logback logging


<profiles>
		<profile>
			<id>MDMIPublic</id>
			<properties>
				<tomcat.deploy.url>http://(TomcatIPHere):8080/manager/text</tomcat.deploy.url>
				<tomcat.deploy.username>TomcatUserNameHere</tomcat.deploy.username>
				<tomcat.deploy.password>TomcatPasswordHere</tomcat.deploy.password>
				<postgresql.user>POSTGRESQL.user</postgresql.user>
				<postgresql.password>POSTGRESQL.password</postgresql.password>
			</properties>
		</profile>
</profiles>


use the following to build and deploy
 mvn tomcat7:deploy -PMDMIProduction
 
 use the following for local development
  mvn tomcat7:deploy -PMDMIDevelopment
 
 to test CORS
 
 curl -H "Origin: http://example.com" --verbose \  http://localhost:8080/org.mdmi.rt.service
 
 curl -H "Origin: http://example.com" --verbose \  http://35.169.86.146:8080/org.mdmi.rt.service
 
 curl -i -H 'Origin: http://localhost:4200' --verbose  http://35.169.86.146:8080/org.mdmi.rt.service/transformation
 
 oad http://35.169.86.146:8080/org.mdmi.rt.service/transformation. Origin http://localhost:4200 is 
 
 look for
 > Accept: */*
> Origin: http://example.com
 
 
 curl -H "Origin: http://localhost:4200" \
  -H "Access-Control-Request-Method: GET" \
  -H "Access-Control-Request-Headers: X-Requested-With" \
  -X OPTIONS --verbose \
  http://35.169.86.146:8080/org.mdmi.rt.service/transformation
  
  
 
 http://localhost:4200/assets/images/MDIX_logo_tagline_print.png
 
 http://localhost:8080/assets/images/MDIX_logo_tagline_print.png
 
