# docker-gatling-graphite
Dockerize Gatling send result to graphite realtime

- for first time:
    docker-compose build 
- up to run
    docker-compose up
  OR 
    docker-compose up -d (to attach mode)
- run gatling test:
    docker-compose run gatling /opt/gatling/bin/gatling.sh


