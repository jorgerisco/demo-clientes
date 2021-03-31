FROM openjdk:8u111-jre-alpine
VOLUME /tmp
ADD target/demo-clientes-1.0.0-SNAPSHOT.jar /app.jar
RUN sh -c 'touch /app.jar'
EXPOSE 8082
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]