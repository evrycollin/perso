-- http://localhost:8080/vertica-rs/api/script/init-db

-- create schema
DROP TABLE IF EXISTS customers CASCADE;
CREATE TABLE customers (
	CustID int, 
	Last_Name char(50), 
	First_Name char(50),
	Email char(50), 
	Phone_Number char(20) 
);

-- default date set
INSERT INTO customers (CustID, Last_Name, First_Name, Email, Phone_Number) VALUES(1,'Anna',  'Allen',   'aang@example.com','       123-456-789');
INSERT INTO customers (CustID, Last_Name, First_Name, Email, Phone_Number) VALUES(2,'Bill',  'Brown',   'b.brown@example.com',    '555-444-3333');
INSERT INTO customers (CustID, Last_Name, First_Name, Email, Phone_Number) VALUES(3,'Cindy', 'Chu',     'cindy@example.com',      '555-867-5309');
INSERT INTO customers (CustID, Last_Name, First_Name, Email, Phone_Number) VALUES(4,'Don',   'Dodd',    'd.d@example.com',        '555-555-1212');
INSERT INTO customers (CustID, Last_Name, First_Name, Email, Phone_Number) VALUES(5,'Eric',  'Estavez', 'e.estavez@example.com',  '781-555-0000');
INSERT INTO customers (CustID, Last_Name, First_Name, Email, Phone_Number) VALUES(6,'Evry',  'Collin',   'evry.collin@gmail.com', '06-49-23-49-71');

