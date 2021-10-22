#!/usr/bin/env bash

set -u -e

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"

cd ${DIR}

docker-compose down --volumes