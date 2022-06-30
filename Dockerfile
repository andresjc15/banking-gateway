FROM openjdk:12
VOLUME /tmp
EXPOSE 8090
ADD ./target/banking-gateway-1.0.0.jar banking-gateway-1.0.0.jar
ENTRYPOINT ["java","-jar","/banking-gateway-1.0.0.jar"]