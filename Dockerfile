FROM openjdk:latest
EXPOSE 8080
ADD /target/quiz-portal-1.0.0.jar /quiz-portal.jar
ADD /src/main/webapp /src/main/webapp
ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:MaxRAMPercentage=75.0","-XX:+CrashOnOutOfMemoryError","-jar","/quiz-portal.jar"]
