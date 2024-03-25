FROM openjdk:21-oracle as builder
WORKDIR /app
COPY target/ServiceDiscovery-1.0.jar ServiceDiscovery-1.0.jar
RUN java -Djarmode=layertools -jar ServiceDiscovery-1.0.jar extract

FROM openjdk:21-oracle
WORKDIR /app
EXPOSE 8762
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "org.springframework.boot.loader.launch.JarLauncher"]