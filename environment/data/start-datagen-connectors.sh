#!/bin/bash

set -u -e

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"

echo "$DIR"

source ${DIR}/../docker.env
source ${DIR}/../tools/ops_functions.sh

echo "delete client-datagen-connector"
delete_connector client-datagen-connector || echo "could not create topic"

echo "Deleting sgt.clients_topic topic"
docker-compose exec broker kafka-topics \
    --delete \
    --bootstrap-server broker:9092 \
    --topic sgt.clients_topic || echo "could not delete topic"

echo "Creating sgt.clients_topic topic"
docker-compose exec broker kafka-topics \
    --create \
    --bootstrap-server broker:9092 \
    --topic sgt.clients_topic\
    --replication-factor 1 \
    --partitions 1

echo "Creating client-datagen-connector"
deploy_connector client-datagen-connector.json

echo "delete card-datagen-connector"
delete_connector card-datagen-connector || echo "could not create topic"

echo "Deleting sgt.cards_topic topic"
docker-compose exec broker kafka-topics \
    --delete \
    --bootstrap-server broker:9092 \
    --topic sgt.cards_topic || echo "could not delete topic"

echo "Creating sgt.cards_topic topic"
docker-compose exec broker kafka-topics \
    --create \
    --bootstrap-server broker:9092 \
    --topic sgt.cards_topic\
    --replication-factor 1 \
    --partitions 1

echo "Creating card-datagen-connector"
deploy_connector card-datagen-connector.json