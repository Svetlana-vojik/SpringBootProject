FROM openjdk:17
ARG WAR_FILE
COPY target/SpringBootProject-0.0.1-SNAPSHOT.war /SpringBootProject-0.0.1-SNAPSHOT.war
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/shop-0.0.1-SNAPSHOT.jar","-web -webAllowOthers -tcp -tcpAllowOthers -browser"]