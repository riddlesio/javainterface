FROM openjdk:8u111-jdk

RUN groupadd --gid 998 jenkins-tomcat
RUN useradd --shell /bin/bash --create-home -u 999 -G jenkins-tomcat jenkins


RUN mkdir /riddles && chown 999:999 /riddles
RUN mkdir /.gradle && chown 999:999 /.gradle

VOLUME /.gradle
ENV GRADLE_USER_HOME /.gradle

WORKDIR /riddles

USER jenkins

# docker create -v /.gradle --name persistent_gradle javainterface-build-environment /bin/true
# docker run -v $(pwd):/riddles --volumes-from persistent_gradle javainterface-build-environment:latest bash -c "./gradlew test"