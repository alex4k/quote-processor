FROM amd64/eclipse-temurin:17-jre
RUN mkdir /opt/app
COPY target/QuoteProcessor-*.jar /opt/app/quote-processor.jar
COPY run.sh /

EXPOSE 8080
ENTRYPOINT ["/run.sh"]