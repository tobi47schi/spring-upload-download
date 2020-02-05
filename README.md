"# spring-upload-download" 

mvn clean install
mvn spring-boot:run


//hier gehen aber die uploads nicht :(
docker build -t demo .
docker run -p 8080:8080 demo
