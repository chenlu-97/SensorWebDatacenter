FROM java:8
VOLUME /tmp
ADD datacenter-littleSensor-service.jar app.jar
RUN bash -c 'touch /app.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 9012