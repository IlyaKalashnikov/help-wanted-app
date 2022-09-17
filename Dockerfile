FROM openjdk:17
ADD /target/help-wanted-app-0.0.1-SNAPSHOT.jar backend.jar
ENTRYPOINT ["java", "-jar", "backend.jar"]