FROM adoptopenjdk/openjdk16:alpine-slim

WORKDIR /opt
COPY build/pdf-platform*.jar pdf-platform.jar

CMD ["java", "-Dspring.profiles.active=docker", "-jar", "pdf-platform.jar"]

EXPOSE 9000