from confluent_kafka.schema_registry import SchemaRegistryClient
from confluent_kafka.schema_registry import avro
from confluent_kafka.serialization import StringSerializer

import config_handler
import kafka_avro_producer
import metadata_parser


def create_producer_serializers(topic_metadata):
    schema_registry_conf = config_handler.get_schema_registry_config()
    schema_registry_client = SchemaRegistryClient(schema_registry_conf)
    subject = topic_metadata.get('metadataTopic') + '-value'
    registered_schema = schema_registry_client.get_latest_version(subject)
    print(registered_schema.schema.schema_str)
    producer_serializers = {
        'key.serializer': StringSerializer(),
        'value.serializer': avro.AvroSerializer(
            schema_registry_client=schema_registry_client,
            schema_str=registered_schema.schema.schema_str,
            to_dict=topic_metadata.get('metadata')
        )
    }

    return producer_serializers


if __name__ == '__main__':
    app_config = config_handler.get_app_config()
    topics_metadata = metadata_parser.get_topics_metadata(
        app_config.get('topology.config.path'))
    for topic_metadata in topics_metadata:
        producer_serializer = create_producer_serializers(topic_metadata)
        producer_config = config_handler.get_producer_config() | producer_serializer
        topic_metadata
        producer = kafka_avro_producer.KafkaAvroProducer(producer_config)
        producer.produce_record(topic=topic_metadata.get('metadataTopic'),
                                key=topic_metadata.get('metadataKey'),
                                value=topic_metadata.get('topicMetadata'))
        producer.flush()
