FROM alpine:3.19.1

RUN apk update && apk add --no-cache openjdk17

COPY config-service/target/config-service-1.0.0.jar app/config-service-1.0.0.jar
COPY scripts/docker/start_container_app.sh app/start_app.sh

RUN chmod +x /app/start_app.sh

ENTRYPOINT ["app/start_app.sh", "app/config-service-1.0.0.jar"]