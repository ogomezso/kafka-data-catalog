#!/usr/bin/env bash

set -u -e

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"

cd ${DIR}

docker-compose up -d

echo "Esperando 30 sg a que el entorno este listo..."

sleep 30s
${DIR}/environment/install-connectors.sh

echo "Esperando un minuto al reinicio de Connect..."

sleep 90s
${DIR}/julie-ops/build-topology.sh