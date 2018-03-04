FROM ubuntu:16.04

MAINTAINER Trubnikov Dmitriy


RUN apt-get update -y
RUN apt-get install -y openjdk-8-jdk-headless
RUN apt-get install -y maven


ENV WORK /opt
ADD . $WORK/server
RUN mkdir -p /var/www/html
RUN touch /etc/httpd.conf


WORKDIR $WORK/server
RUN mvn clean install

EXPOSE 80

CMD java -jar ./target/ServerHighload-1.0-SNAPSHOT.jar