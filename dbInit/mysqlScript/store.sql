USE 226analysis;

CREATE TABLE storeOperation (
    storeId     INT             NOT NULL,
    storeName   VARCHAR(100)    NOT NULL,
    country     VARCHAR(20)     NOT NULL,
    state       VARCHAR(50)     NOT NULL,
    city        VARCHAR(50)     NOT NULL,
    street      VARCHAR(50)     NOT NULL,
    PRIMARY KEY(storeId)
);

insert into storeOperation (storeId, storeName, country, state, city, street) values (1, 'Ryan-Gerhold', 'United States', 'Missouri', 'Saint Louis', 'International');
insert into storeOperation (storeId, storeName, country, state, city, street) values (2, 'Friesen LLC', 'United States', 'Alabama', 'Birmingham', 'Carpenter');
insert into storeOperation (storeId, storeName, country, state, city, street) values (3, 'Krajcik, Zemlak and Jenkins', 'United States', 'Arizona', 'Phoenix', 'Hayes');
insert into storeOperation (storeId, storeName, country, state, city, street) values (4, 'Kunze Group', 'United States', 'Montana', 'Billings', 'Lakewood');
insert into storeOperation (storeId, storeName, country, state, city, street) values (5, 'Klocko Group', 'United States', 'Tennessee', 'Jackson', 'Scoville');
insert into storeOperation (storeId, storeName, country, state, city, street) values (6, 'Lang Inc', 'United States', 'Nebraska', 'Omaha', 'Wayridge');
insert into storeOperation (storeId, storeName, country, state, city, street) values (7, 'Fahey, Leuschke and Rau', 'United States', 'Washington', 'Spokane', 'Paget');
insert into storeOperation (storeId, storeName, country, state, city, street) values (8, 'Hayes, Abshire and Weimann', 'United States', 'North Carolina', 'Winston Salem', 'Mesta');
insert into storeOperation (storeId, storeName, country, state, city, street) values (9, 'Sipes-Mosciski', 'United States', 'New York', 'Bronx', 'Autumn Leaf');
insert into storeOperation (storeId, storeName, country, state, city, street) values (10, 'Jacobi-O''Hara', 'United States', 'Texas', 'Abilene', 'Darwin');
insert into storeOperation (storeId, storeName, country, state, city, street) values (11, 'Pollich, Lockman and Lakin', 'United States', 'District of Columbia', 'Washington', 'Petterle');
insert into storeOperation (storeId, storeName, country, state, city, street) values (12, 'Weissnat, Dickinson and Jakubowski', 'United States', 'Georgia', 'Lawrenceville', 'Thompson');
insert into storeOperation (storeId, storeName, country, state, city, street) values (13, 'Hegmann, Sanford and Jacobs', 'United States', 'Texas', 'Lubbock', 'Dottie');
insert into storeOperation (storeId, storeName, country, state, city, street) values (14, 'Herman-Hirthe', 'United States', 'Alaska', 'Juneau', 'Sugar');
insert into storeOperation (storeId, storeName, country, state, city, street) values (15, 'Gottlieb, Durgan and Mayer', 'United States', 'Missouri', 'Saint Louis', 'Rieder');
insert into storeOperation (storeId, storeName, country, state, city, street) values (16, 'Kessler Inc', 'United States', 'Missouri', 'Saint Louis', 'Buhler');
insert into storeOperation (storeId, storeName, country, state, city, street) values (17, 'Windler Group', 'United States', 'Alabama', 'Birmingham', 'Texas');
insert into storeOperation (storeId, storeName, country, state, city, street) values (18, 'Kuhn Inc', 'United States', 'Idaho', 'Boise', 'Carioca');
insert into storeOperation (storeId, storeName, country, state, city, street) values (19, 'Kiehn Inc', 'United States', 'Tennessee', 'Chattanooga', 'School');
insert into storeOperation (storeId, storeName, country, state, city, street) values (20, 'Kovacek-Waters', 'United States', 'Alabama', 'Mobile', 'Village');
insert into storeOperation (storeId, storeName, country, state, city, street) values (21, 'Parisian-Heller', 'United States', 'Florida', 'Miami', 'Steensland');
insert into storeOperation (storeId, storeName, country, state, city, street) values (22, 'Moen-Lindgren', 'United States', 'Idaho', 'Idaho Falls', 'Main');
insert into storeOperation (storeId, storeName, country, state, city, street) values (23, 'Gleason-Breitenberg', 'United States', 'Massachusetts', 'Brockton', 'Westport');
insert into storeOperation (storeId, storeName, country, state, city, street) values (24, 'Dare, Dooley and Hoeger', 'United States', 'Montana', 'Billings', 'Reindahl');
insert into storeOperation (storeId, storeName, country, state, city, street) values (25, 'Heller-Toy', 'United States', 'Maryland', 'Silver Spring', 'Larry');
insert into storeOperation (storeId, storeName, country, state, city, street) values (26, 'Lindgren and Sons', 'United States', 'Oregon', 'Portland', 'Kings');
insert into storeOperation (storeId, storeName, country, state, city, street) values (27, 'Buckridge-Kunze', 'United States', 'Florida', 'Miami', 'Tennessee');
insert into storeOperation (storeId, storeName, country, state, city, street) values (28, 'Schimmel-Fadel', 'United States', 'New York', 'New York City', 'Reinke');
insert into storeOperation (storeId, storeName, country, state, city, street) values (29, 'Ratke Group', 'United States', 'Oregon', 'Portland', 'Village');
insert into storeOperation (storeId, storeName, country, state, city, street) values (30, 'Gutkowski LLC', 'United States', 'Pennsylvania', 'Philadelphia', 'Anhalt');
insert into storeOperation (storeId, storeName, country, state, city, street) values (31, 'Haag-Schimmel', 'United States', 'New York', 'White Plains', '2nd');
insert into storeOperation (storeId, storeName, country, state, city, street) values (32, 'O''Hara Inc', 'United States', 'Texas', 'Abilene', 'Sycamore');
insert into storeOperation (storeId, storeName, country, state, city, street) values (33, 'Simonis LLC', 'United States', 'Texas', 'San Antonio', 'Hintze');
insert into storeOperation (storeId, storeName, country, state, city, street) values (34, 'Ward-O''Reilly', 'United States', 'Minnesota', 'Saint Paul', 'Buena Vista');
insert into storeOperation (storeId, storeName, country, state, city, street) values (35, 'Lueilwitz, Wintheiser and Wuckert', 'United States', 'Tennessee', 'Memphis', 'Di Loreto');
insert into storeOperation (storeId, storeName, country, state, city, street) values (36, 'Lowe-Wilkinson', 'United States', 'Nevada', 'North Las Vegas', 'Charing Cross');
insert into storeOperation (storeId, storeName, country, state, city, street) values (37, 'Koch-Little', 'United States', 'California', 'Riverside', 'Troy');
insert into storeOperation (storeId, storeName, country, state, city, street) values (38, 'Zboncak LLC', 'United States', 'Texas', 'Lubbock', 'Esch');
insert into storeOperation (storeId, storeName, country, state, city, street) values (39, 'Rutherford, Balistreri and Kihn', 'United States', 'North Carolina', 'Greensboro', 'Oxford');
insert into storeOperation (storeId, storeName, country, state, city, street) values (40, 'Medhurst-Kilback', 'United States', 'Virginia', 'Manassas', 'Darwin');
insert into storeOperation (storeId, storeName, country, state, city, street) values (41, 'Waelchi-Zieme', 'United States', 'Colorado', 'Colorado Springs', 'Maryland');
insert into storeOperation (storeId, storeName, country, state, city, street) values (42, 'Bayer-Harvey', 'United States', 'Indiana', 'Evansville', 'Dwight');
insert into storeOperation (storeId, storeName, country, state, city, street) values (43, 'Simonis-Kerluke', 'United States', 'New York', 'Rochester', 'Onsgard');
insert into storeOperation (storeId, storeName, country, state, city, street) values (44, 'Kerluke LLC', 'United States', 'California', 'Sacramento', 'Novick');
insert into storeOperation (storeId, storeName, country, state, city, street) values (45, 'Bode, Schiller and Waters', 'United States', 'Nebraska', 'Omaha', 'Browning');
insert into storeOperation (storeId, storeName, country, state, city, street) values (46, 'Bauch Inc', 'United States', 'Louisiana', 'New Orleans', 'Heath');
insert into storeOperation (storeId, storeName, country, state, city, street) values (47, 'Satterfield, Herman and Luettgen', 'United States', 'Virginia', 'Ashburn', 'Fallview');
insert into storeOperation (storeId, storeName, country, state, city, street) values (48, 'Hyatt-Leffler', 'United States', 'Hawaii', 'Honolulu', 'Reinke');
insert into storeOperation (storeId, storeName, country, state, city, street) values (49, 'Becker, Bogisich and Walsh', 'United States', 'North Dakota', 'Fargo', 'Maywood');
insert into storeOperation (storeId, storeName, country, state, city, street) values (50, 'Brown LLC', 'United States', 'California', 'Oakland', 'Ridgeview');

CREATE TABLE store (
    storeKey    INT             NOT NULL    AUTO_INCREMENT,
    storeId     INT             NOT NULL,
    storeName   VARCHAR(100)    NOT NULL,
    country     VARCHAR(20)     NOT NULL,
    state       VARCHAR(50)     NOT NULL,
    city        VARCHAR(50)     NOT NULL,
    street      VARCHAR(50)     NOT NULL,
    PRIMARY KEY(storeKey)
);

INSERT INTO store(storeId, storeName, country, state, city, street)
SELECT storeId, storeName, country, state, city, street FROM storeOperation;
