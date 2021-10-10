FROM adoptopenjdk/openjdk16:alpine-slim

ARG SPRING_PROFILE=docker
ENV PROFILE=$SPRING_PROFILE

WORKDIR /opt
COPY build/pdf-platform*.jar pdf-platform.jar

CMD ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "pdf-platform.jar"]

EXPOSE 9000