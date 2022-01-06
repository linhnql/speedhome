FROM maven:3.5-jdk-8-alpine AS BUILD
LABEL Khoa Vu
WORKDIR /home/app
COPY speedhome-api /home/app/speedhome-api
COPY speedhome-client /home/app/speedhome-client
COPY speedhome-service /home/app/speedhome-service
COPY yaml /home/app/yaml
COPY pom.xml /home/app/
RUN mvn clean package -DskipTests

FROM openjdk:8-jdk-alpine
COPY --from=BUILD /home/app/speedhome-service/target/speedhome-service-0.0.1.jar speedhome-service-0.0.1.jar
ENTRYPOINT ["java","-jar","speedhome-service-0.0.1.jar"]