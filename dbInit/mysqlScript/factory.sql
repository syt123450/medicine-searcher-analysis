USE 226analysis;

CREATE TABLE factory(
    factoryId   INT             NOT NULL,
    factoryName VARCHAR(200)    NOT NULL,
    country     VARCHAR(20)     NOT NULL,
    state       VARCHAR(20)     NOT NULL,
    city        VARCHAR(20)     NOT NULL,
    street      VARCHAR(50)     NOT NULL,
    PRIMARY KEY (factoryId)
); 

insert into factory (factoryId, factoryName, country, state, city, street) values (1, 'Physicians Total Care, Inc.', 'United States', 'Alabama', 'Montgomery', 'Debs');
insert into factory (factoryId, factoryName, country, state, city, street) values (2, 'Mylan Pharmaceuticals Inc.', 'United States', 'Texas', 'Pasadena', 'Meadow Vale');
insert into factory (factoryId, factoryName, country, state, city, street) values (3, 'Jets, Sets, & Elephants Beauty Corp.', 'United States', 'New York', 'New York City', 'Texas');
insert into factory (factoryId, factoryName, country, state, city, street) values (4, 'Hannaford Brothers Company', 'United States', 'Florida', 'Hialeah', 'High Crossing');
insert into factory (factoryId, factoryName, country, state, city, street) values (5, 'Ventura Corporation Ltd.', 'United States', 'Kentucky', 'Lexington', 'Northport');
insert into factory (factoryId, factoryName, country, state, city, street) values (6, 'Nelco Laboratories, Inc.', 'United States', 'California', 'Bakersfield', 'Merry');
