FROM openjdk:8u111-jdk

RUN mkdir /riddles/
RUN mkdir /.gradle

VOLUME /.gradle
ENV GRADLE_USER_HOME /.gradle

WORKDIR /riddles

# docker create -v /.gradle --name persistent_gradle javainterface-build-environment /bin/true
# docker run -v $(pwd):/riddles --volumes-from persistent_gradle javainterface-build-environment:latest bash -c "./gradlew test"