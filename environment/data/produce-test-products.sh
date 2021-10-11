#!/bin/bash

set -u -e

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"

echo "$DIR"

cat $DIR/products-test-dataset.json | kafka-avro-console-producer --topic global.bank.local.ops.products.product-catalog --bootstrap-server broker:29092 --property schema.registry.url=http://schema-registry:8081 --property key.schema='{"type":"string"}' --property value.schema="$(< $DIR/schemas/product.avsc)" --property parse.key=true --property key.separator=":"