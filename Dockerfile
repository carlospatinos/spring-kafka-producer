FROM openjdk:8-jdk-alpine


RUN mkdir -p /app/
ADD build/libs/carlospatinos-salesforce-connector-0.1.0.jar /app/carlospatinos-salesforce-connector-0.1.0.jar

ENV username "default_username"
ENV password "default_password"
ENV channel "default_channel"
ENV datadog_key = "key"

EXPOSE 8080
EXPOSE 9010

#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
#ENTRYPOINT ["java", "-jar", "/app/java-restful-test-0.1.0.jar"]
ENTRYPOINT ["java", \
    "-Dcom.sun.management.jmxremote", \
    "-Dcom.sun.management.jmxremote.port=9010", \
    "-Dcom.sun.management.jmxremote.local.only=false", \
    "-Dcom.sun.management.jmxremote.authenticate=false", \
    "-Dcom.sun.management.jmxremote.ssl=false",\
    "-Dsalesforce.username=${username}",\
    "-Dsalesforce.password=${password}",\
    "-Dsalesforce.channel=${channel}",\
    "-Dsalesforce.datadogkey=${datadog_key}",\
    "-jar","/app/carlospatinos-salesforce-connector-0.1.0.jar"]