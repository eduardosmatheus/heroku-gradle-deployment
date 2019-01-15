FROM tomcat
LABEL MANTAINER="Matheus Eduardo, Fábio Kiatkowski"
COPY /lib/ojdbc7-12.1.0.2.jar /usr/local/tomcat/lib
COPY /lib/HikariCP-2.7.4.jar /usr/local/tomcat/lib
COPY /lib/slf4j-api-1.7.25.jar /usr/local/tomcat/lib
COPY /lib/server.xml /usr/local/tomcat/conf
COPY /lib/logging.properties /usr/local/tomcat/conf
#COPY /lib/context.xml /usr/local/tomcat/conf
ENV TZ=CST
COPY /build/libs/server.war /usr/local/tomcat/webapps/
RUN chmod 775 -R /usr/local/tomcat/webapps
RUN mkdir /spring-kotlin-app
ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# RUN apt-get update && apt-get install -y --no-install-recommends apt-utils
# RUN apt-get install -y samba smbclient
# RUN apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*
# RUN service smbd start
