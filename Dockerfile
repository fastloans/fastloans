# Stage 1: Build the application
FROM gradle:jdk21 AS build
COPY . /apps/lms
WORKDIR /apps/lms
RUN ./gradlew build

# Stage 2: Run the application
FROM openjdk:21-jdk
WORKDIR /apps/lms
COPY --from=build /apps/lms/build/libs/lms-0.0.1-SNAPSHOT.jar /apps/lms/build/libs/lms-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/apps/lms/build/libs/lms-0.0.1-SNAPSHOT.jar"]
