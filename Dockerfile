FROM openjdk:17

RUN mkdir -p /opt/server/

COPY ./target/v2-0.0.1-SNAPSHOT.jar /opt/server

COPY run.sh /opt/server/

COPY ./src/main/resources/static /server

EXPOSE 8888

WORKDIR /opt/server

ENTRYPOINT ["sh", "/opt/server/run.sh"]
