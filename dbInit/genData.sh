#!/bin/sh

cd mysqlScript
mysql -u ultimate -psesame < ./brand.sql
mysql -u ultimate -psesame < ./store.sql
mysql -u ultimate -psesame < ./customer.sql
mysql -u ultimate -psesame < ./factory.sql
mysql -u ultimate -psesame < ./medicine.sql
mysql -u ultimate -psesame < ./calendar.sql
mysql -u ultimate -psesame < ./saleTransaction.sql

#mysql -u ultimate -psesame < ./dropTbl.sql

