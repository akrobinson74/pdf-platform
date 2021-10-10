FROM adoptopenjdk/openjdk16:alpine-slim

ENV PROFILE="k8s"

WORKDIR /opt
COPY build/pdf-platform*.jar pdf-platform.jar

CMD ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "pdf-platform.jar"]

EXPOSE 9000