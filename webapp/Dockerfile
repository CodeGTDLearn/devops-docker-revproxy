FROM pauloportfolio/ubuntu16-njdk8
ENV WEBAPP_HOME /usr/local/webapp
ADD . /usr/local/webapp
RUN mkdir -p /usr/local/webapp/logs
RUN cd /usr/local/webapp && mvn clean package
CMD ["java","-jar","-DlogPath=/usr/local/webapp/logs","/usr/local/webapp/target/webapp-1.0.0-SNAPSHOT.jar"]
