FROM 192.168.1.6:30003/jiangqi/jdk-1.8:v1
LABEL app="jqtest"
EXPOSE 8080
ENV TZ=Asia/Shanghai \
	LANG=en_US.UTF-8 \
	LANGUAGE=en_US:en \
	LC_ALL=en_US.UTF-8  
RUN mkdir /jqtest
ADD	serviceTwo/target/serviceTwo-0.0.1-SNAPSHOT.jar /bin
ENTRYPOINT ["java","-XX:+UnlockExperimentalVMOptions","-XX:+UseCGroupMemoryLimitForHeap","-Dlogging.config=/jqtest/logback.xml","-Dfile.encoding=utf-8", "-jar", "/bin/serviceTwo-0.0.1-SNAPSHOT.jar","--spring.config.location=/jqtest/application.properties"]