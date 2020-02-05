"# spring-upload-download" 

mvn clean install
mvn spring-boot:run


//hier gehen aber die uploads nicht :(
docker build -t demo .
docker run -p 8080:8080 demo
// wenn im hintergrund
docker run -d -p 8080:8080 demo 


// in docker container kommandos ausführen:
Zb:
docker exec -it [CONTAINER] ls uploads

// Docker container übersicht:
docker ps

//Docker container stoppen:
docker kill [CONTAINER]