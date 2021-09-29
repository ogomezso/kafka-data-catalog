from confluent_kafka import SerializingProducer


class KafkaAvroProducer(object):

    def __init__(self, producer_conf):
        self.producer_conf = producer_conf
        self.producer = SerializingProducer(producer_conf)

    def produce_record(self, key, value, topic):
        self.producer.produce(topic, key=key, value=value, on_delivery=acked)
        self.producer.poll(0)

    def flush(self):
        self.producer.flush()


delivered_records = 0


def acked(err, msg):
    global delivered_records
    """Delivery report handler called on
    successful or failed delivery of message
    """
    if err is not None:
        print("Failed to deliver message: {}".format(err))
    else:
        delivered_records += 1
        print("Produced record to topic {} partition [{}] @ offset {}"
              .format(msg.topic(), msg.partition(), msg.offset()))
