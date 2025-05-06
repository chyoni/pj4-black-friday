FROM ubuntu:22.04

RUN apt-get update && \
    apt-get install -y openjdk-21-jdk && \
    apt-get clean;

WORKDIR /app

COPY build/libs/order.jar /app/app.jar

CMD ["java", "-jar", "app.jar", "--spring.profiles.active=staging"]