-- create schema
DROP TABLE IF EXISTS test_load CASCADE;
CREATE TABLE test_load( 
COLUMN1 VARCHAR,
COLUMN2 VARCHAR,
COLUMN3 VARCHAR
);
-- import data
COPY test_load FROM  '/Database/input_data/import_load_test.gzip' GZIP DELIMITER ',';

