FROM adoptopenjdk:16-jre-hotspot
RUN mkdir /opt/app
ADD maven/${project.artifactId}-${project.version}.jar /opt/app/locations.jar
CMD ["java","-jar","opt/app/locations.jar"]