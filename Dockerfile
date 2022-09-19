# Docker for Content Vault microserivce 
FROM openjdk:17-oracle
ARG env
# local file storage path
RUN mkdir -p /opt/domain-data/log
COPY src/main/resources/application.${env}.properties /opt/domain-data/application.properties
COPY target/domain-data-*.jar /opt/domain-data/domain-data.jar
RUN ln -sf /dev/stdout /opt/domain-data/log/domain-data.sys.log

CMD ["java", "-jar", "/opt/domain-data/domain-data.jar", "--spring.config.additional-location=/opt/domain-data/application.properties"]

EXPOSE 8083
