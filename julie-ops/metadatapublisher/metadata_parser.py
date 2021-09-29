import fnmatch
import os

import yaml


def get_topics_metadata(configPath):
    topics_metadata = []
    for filename in os.listdir(configPath):
        file = os.path.join(configPath, filename)
        if os.path.isfile(file):
            if fnmatch.fnmatch(file, '*.yml'):
                with open(file) as f:
                    docs = yaml.load_all(f, Loader=yaml.FullLoader)
                    for doc in docs:
                        projects = doc.get('projects')
                        for project in projects:
                            if 'topics' in project:
                                topics = project.get('topics')
                                for topic in topics:
                                    if 'metadataTopic' in topic:
                                        if 'metadata' in topic:
                                            metadata_dict = {
                                                'metadataTopic': topic.get(
                                                    'metadataTopic'),
                                                'metadataKey': topic.get(
                                                    'name'),
                                                'topicMetadata': topic.get(
                                                    'metadata')
                                            }
                                            topics_metadata.append(
                                                metadata_dict)

    return topics_metadata

print(get_topics_metadata('/Users/ogomezsoriano/projects/confluent/kafka-data-catalog/julie-ops/config/topology'))
