FROM java:8
VOLUME /tmp
EXPOSE 9333
ADD /build/libs/*.jar app.jar
CMD java -Xms256m -Xmx512m -jar app.jar