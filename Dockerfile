FROM openjdk:8-alpine

COPY target/uberjar/choices.jar /choices/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/choices/app.jar"]
