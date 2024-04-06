FROM openjdk:21-oracle as builder
WORKDIR /app
COPY target/ConfigServer-1.0.jar ConfigServer-1.0.jar
RUN java -Djarmode=layertools -jar ConfigServer-1.0.jar extract

COPY src/main/resources ./

FROM openjdk:21-oracle
WORKDIR /app
EXPOSE 8888
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./
ENTRYPOINT ["java", "-Dspring.profiles.active=native", "org.springframework.boot.loader.launch.JarLauncher"]