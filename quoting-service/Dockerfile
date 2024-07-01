FROM alpine:3.19.1

RUN apk update
RUN apk add --no-cache openjdk17
RUN apk add --no-cache curl

COPY quoting-service/target/quoting-service-2.0.0.jar app/quoting-service-2.0.0.jar
COPY ../scripts/docker/start_container_app.sh app/start_app.sh

RUN chmod +x /app/start_app.sh

ENTRYPOINT ["app/start_app.sh", "app/quoting-service-2.0.0.jar"]