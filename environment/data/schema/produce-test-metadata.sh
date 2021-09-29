#!/bin/bash

cat metadata-test-dataset.json | kafka-avro-console-producer --topic metadata_topic --bootstrap-server broker:29092 --property schema.registry.url=http://schema-registry:8081 --property key.schema='{"type":"string"}' --property value.schema="$(< topic_metadata.avsc)" --property parse.key=true --property key.separator=":"