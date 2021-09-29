#!/usr/bin/env bash

set -u -e

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"

echo "$DIR"

docker run -t -i \
      -v ${DIR}/config:/config \
      --network="kafka-data-catalog_default"\
      purbon/kafka-topology-builder:latest \
      /bin/bash -c 'julie-ops-cli.sh --brokers broker:29092 --clientConfig /config/topology-builder.properties  --topology /config/topology/data-catalog-topology.yml'

docker run -t -i \
      -v ${DIR}/config:/config \
      --network="kafka-data-catalog_default"\
      purbon/kafka-topology-builder:latest \
      /bin/bash -c 'julie-ops-cli.sh --brokers broker:29092 --clientConfig /config/topology-builder.properties  --topology /config/topology/global-topology.yml -quiet'

docker run -t -i \
      -v ${DIR}/config:/config \
      --network="kafka-data-catalog_default"\
      purbon/kafka-topology-builder:latest \
      /bin/bash -c 'julie-ops-cli.sh --brokers broker:29092 --clientConfig /config/topology-builder.properties  --topology /config/topology/es-topology.yml -quiet'

docker run -t -i \
      -v ${DIR}/config:/config \
      --network="kafka-data-catalog_default"\
      purbon/kafka-topology-builder:latest \
      /bin/bash -c 'julie-ops-cli.sh --brokers broker:29092 --clientConfig /config/topology-builder.properties  --topology /config/topology/uk-topology.yml -quiet'

python metadatapublisher/metadata_producer.py