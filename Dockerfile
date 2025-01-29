FROM adoptopenjdk/openjdk17:jdk-17.0.1_12-slim
WORKDIR /opt
COPY target/*.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
