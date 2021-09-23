docker compose exec connect confluent-hub install --no-prompt confluentinc/kafka-connect-elasticsearch:latest
docker compose restart connect