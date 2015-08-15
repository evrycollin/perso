#!/bin/sh

docker run -p 149.202.54.165:27017:27017  --name myy-mongo -d mongo

echo 
docker ps
