import configparser
import os

script_dir = os.path.dirname(__file__)
rel_path = "matadata_config.ini"
abs_file_path = os.path.join(script_dir, rel_path)

parser = configparser.ConfigParser()
parser.read(abs_file_path)

config = {s: dict(parser.items(s)) for s in parser.sections()}


def get_producer_config():
    return config.get('KAFKA')


def get_schema_registry_config():
    return config.get('SCHEMA_REGISTRY')


def get_app_config():
    return config.get('APP')
