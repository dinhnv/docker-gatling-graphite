# Pull base image
# FROM webdizz/gatling
# FROM denvazh/gatling:2.2.2
# Gatling is a highly capable load testing tool.
#
# Documentation: http://gatling.io/docs/2.2.2/
# Cheat sheet: http://gatling.io/#/cheat-sheet/2.2.2
FROM java:8-jdk-alpine

MAINTAINER Dino "dinhnv.mr@gmail.com"

# working directory for gatling
WORKDIR /opt

# gating version
ENV GATLING_VERSION 2.2.2

# create directory for gatling install
RUN mkdir -p gatling

# install gatling
RUN apk add --update wget && \
  mkdir -p /tmp/downloads && \
  wget -q -O /tmp/downloads/gatling-$GATLING_VERSION.zip \
  https://repo1.maven.org/maven2/io/gatling/highcharts/gatling-charts-highcharts-bundle/$GATLING_VERSION/gatling-charts-highcharts-bundle-$GATLING_VERSION-bundle.zip && \
  mkdir -p /tmp/archive && cd /tmp/archive && \
  unzip /tmp/downloads/gatling-$GATLING_VERSION.zip && \
  mv /tmp/archive/gatling-charts-highcharts-bundle-$GATLING_VERSION/* /opt/gatling/

# change context to gatling directory
WORKDIR  /opt/gatling

# set directories below to be mountable from host
VOLUME ["/opt/gatling/conf", "/opt/gatling/results", "/opt/gatling/user-files"]

# set environment variables
ENV PATH /opt/gatling/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin
ENV GATLING_HOME /opt/gatling

# scala
ENV SCALA_VERSION 2.11.7
ENV SBT_VERSION 0.13.11

# Install SBT
WORKDIR /opt/
ADD https://dl.bintray.com/sbt/native-packages/sbt/${SBT_VERSION}/sbt-${SBT_VERSION}.tgz .
RUN tar xvfz sbt-${SBT_VERSION}.tgz && rm sbt-${SBT_VERSION}.tgz
RUN ln -s /opt/sbt/bin/sbt /usr/bin/

# Hack for forcing SBT to install scala
# scala install
# ADD http://www.scala-lang.org/files/archive/scala-${SCALA_VERSION}.deb .
# RUN sudo dpkg -i scala-${SCALA_VERSION}.deb && rm scala-${SCALA_VERSION}.deb

COPY ./compose/gatling/conf/gatling.conf /opt/gatling/conf/
COPY ./compose/gatling/conf/logback.xml /opt/gatling/conf/
COPY ./compose/gatling/conf/storage-aggregation.conf /opt/gatling/conf/
COPY ./compose/gatling/conf/storage-schemas.conf /opt/gatling/conf/

# RUN mkdir -p /app/results
# RUN mkdir /app/user-files

