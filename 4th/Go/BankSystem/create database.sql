CREATE DATABASE if not exists `Bank`;

USE `Bank`;

CREATE TABLE  if not exists Customer
(
	SSN INT  PRIMARY KEY,
	Name VARCHAR(100),
	Age INT
);

CREATE TABLE  if not exists Card
(
	CardNum INT PRIMARY KEY,
	BankName VARCHAR(100),
	SSN INT,
    FOREIGN KEY(SSN) REFERENCES Customer(SSN)
);

SELECT *
FROM Customer;

SELECT *
FROM Card;