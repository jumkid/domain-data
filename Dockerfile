# Docker for Content Vault microserivce 
FROM openjdk:20
ARG env
# local file storage path
RUN mkdir -p /opt/domain-data/logs
COPY src/main/resources/application.${env}.properties /opt/domain-data/application.properties
COPY target/domain-data-*.jar /opt/domain-data/domain-data.jar

RUN ln -sf /dev/stdout /opt/domain-data/logs/domain-data.sys.log
WORKDIR /opt/domain-data

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar domain-data.jar --spring.config.additional-location=application.properties"]

EXPOSE 8083
