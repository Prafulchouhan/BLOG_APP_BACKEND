FROM openjdk:8
EXPOSE 8080
ADD target/blog-app.jar blog-app.jar
ENTRYPOINT ["java","-jar","/blog-app.jar"]