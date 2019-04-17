# microservices

## Motivation
This project took a monolithic program and broke it into 3 components. A servlet and two microservices. The microservices are unaware of each other. The servlet takes in data and forwards that data to the Lab5calc microservice which interacts with the database and returns numerical grade to servlet in JSON format. Servlet passes grade to lab5map microservice which then returns a letter grade in json format. Servlet parses data and displays to user. Service is lightweight only passing data needed by the microservices.

## Quick Start
Pull both Docker images using:
	$ docker pull dmarnold/lab5calc
	$ docker pull dmarnold/lab5map

## Or build locally
Using dockerfile, follow these steps

## Dockerhub Images
Images for the lab’s microservices can be found on Docker Hub at locations:
dmarnold/lab5calc
dmarnold/lab5map
Both images are exposed on port 8080
Both images contain the war file it needs in the TomCat webapps directory to run the service.
Dockerfiles
Folders for each image are provided and contains dockerfile and resources needed for that build. The folders are named: calcDockerBuild and mapDockerBuild To build locally, open a CLI for the provided directory and type:
$ docker build -t <name> .
$ docker run – p <external port of your choosing>:8080 <name>

## Source Code
There are folders provided for each component: lab5-app, lab5-calc, and lab5-map.
Each folder contains source code, build, properties, web.xml, and resources needed to build war file and distribute to Tomcat webapps directory.
Open a CLI for each directory and execute “ant deploy” to build.
In the lab5-app properties file, change the path for the tomcat directory to that on your local system
In the lab5-app web.xml file, the external ports for mapping the Docker container are provided as init params and can be modified for easy changing of ports. They are currently 8001 for lab5calc, and 8002 for lab5map.
To change ports, edit <param-value> with a new value in web.xml:


## Built With

   [Apache Tomcat](http://tomcat.apache.org/) 

## Authors

  **David Arnold**  - [dmarnol2](https://github.com/dmarnol2)
