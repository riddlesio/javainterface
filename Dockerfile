FROM frekele/gradle:2.4-jdk8

RUN groupadd --gid 998 jenkins-tomcat
RUN useradd --shell /bin/bash --create-home -u 999 -G jenkins-tomcat jenkins

RUN mkdir /riddles && chown 999:999 /riddles

COPY build.gradle /riddles/build.gradle
WORKDIR /riddles

USER jenkins
RUN gradle installDist

# docker create -v /.gradle --name persistent_gradle javainterface-build-environment /bin/true
# docker run -v $(pwd):/riddles --volumes-from persistent_gradle javainterface-build-environment:latest bash -c "./gradlew test"