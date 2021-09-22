function deploy_connector() {
  curl -k -X POST \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d @$1 \
  $CONNECT_URL/connectors
}

function get_connector_plugins() {
  curl -k $CONNECT_URL/connector-plugins
}

function get_connectors() {
  curl -k $CONNECT_URL/connectors
}

function get_connector() {
  curl -k $CONNECT_URL/connectors/$1
}

function get_connector_status() {
  curl -k $CONNECT_URL/connectors/$1/status
}

function delete_connector() {
  curl -k -X DELETE $CONNECT_URL/connectors/$1
}

function get_connect_worker_logs() {
  ansible \
    -i $CPA_HOME/scram-rbac-tls.yml kafka_connect \
    -a "sudo tail -n 100 /var/log/kafka/connect-distributed.log"
}

function mds_login() {
  export CONFLUENT_USERNAME=$SUPER_USER
  export CONFLUENT_PASSWORD=$SUPER_USER_PW
  confluent login --url $MDS_URL --ca-cert-path ${TRUSTSTORE}
}

function create_topic() {
  docker-compose exec tools kafka-topics \
    --bootstrap-server $BOOTSTRAP_SERVERS \
    --command-config $PROPERTIES_FILE \
    --create --topic $1 --partitions $2 --replication-factor $3
}

function list_topics() {
  docker-compose exec tools kafka-topics \
    --bootstrap-server $BOOTSTRAP_SERVERS \
    --command-config $CPA_HOME/utility/client-ssl.properties \
    --list
}

function delete_topic() {
  docker-compose exec tools kafka-topics \
    --bootstrap-server $BOOTSTRAP_SERVERS \
    --command-config $CPA_HOME/utility/client-ssl.properties \
    --delete \
    --topic $1
}

function list_subjects() {
  curl -k \
     -H "Content-Type: application/vnd.schemaregistry.v1+json" \
     -H "Accept: application/vnd.schemaregistry.v1+json, application/vnd.schemaregistry+json, application/json" \
     $SR_URL/subjects
}

function register_schema() {
  curl -k -X POST \
     -H "Content-Type: application/vnd.schemaregistry.v1+json" \
     -H "Accept: application/vnd.schemaregistry.v1+json, application/vnd.schemaregistry+json, application/json" \
     -d @$2 \
     $SR_URL/subjects/$1/versions
}

function delete_schema() {
  curl -X DELETE \
     -H "Content-Type: application/vnd.schemaregistry.v1+json" \
     -H "Accept: application/vnd.schemaregistry.v1+json, application/vnd.schemaregistry+json, application/json" \
     $SR_URL/subjects/$1/versions/latest
}
