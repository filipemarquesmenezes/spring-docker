#FROM openjdk:8-jdk-alpine
#EXPOSE 8080
#ARG JAR_FILE=build/libs/spring-docker-0.0.1-SNAPSHOT.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000", "-Djava.security.egd=file:/dev/./urandom", "-jar","/app.jar"]

#FROM gradle:7.0.2-jdk8-openj9
#COPY --chown=gradle:gradle . /home/gradle/src
#WORKDIR /home/gradle/src
#RUN --mount=type=cache,target=/home/gradle/.gradle gradle build --no-daemon --info
#ENTRYPOINT ["java","-XX:+UnlockExperimentalVMOptions","-XX:+UseCGroupMemoryLimitForHeap","-Djava.security.egd=file:/dev/./urandom","-jar","/home/gradle/src/build/libs/spring-docker-0.0.1-SNAPSHOT.jar"]

FROM openjdk:8-jdk-alpine AS build
WORKDIR /workspace/app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY src src

RUN --mount=type=cache,target=/root/.gradle ./gradlew build -x test --info
RUN mkdir -p build/libs && (cd build/libs; jar -xf app-0.0.1-SNAPSHOT.jar)

FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/build/libs
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.example.springdocker.SpringDockerApplication"]