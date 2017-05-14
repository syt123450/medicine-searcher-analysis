##### ##### ##### ##### ##### ###### ##### ##### #####
##### ##### #####   SPECIAL   ###### ##### ##### #####
##### ##### ##### ##### ##### ###### ##### ##### #####
# // Pie for yesterday
SELECT SUM(s.totalPrice) as totalSum, m.factoryName
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND c.fullDate = subdate(current_date, 1)
AND	s.medicineKey = m.medicineKey
AND s.calendarKey = c.calendarKey
GROUP BY m.factoryName
;



##### ##### ##### ##### ##### ###### ##### ##### #####
##### ##### #####    NFNY     ###### ##### ##### #####
##### ##### ##### ##### ##### ###### ##### ##### #####

# // (NFL,NTL) Pie
SELECT SUM(s.totalPrice) as totalSum, m.factoryName
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND c.year >= 2012
AND	s.medicineKey = m.medicineKey
AND s.calendarKey = c.calendarKey
GROUP BY m.factoryName
;

#// (NFL,NTL) Sankey part0
SELECT SUM(s.totalPrice) as totalSum, m.brandName, m.factoryName
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND c.year >=2012
AND	s.medicineKey = m.medicineKey
GROUP BY m.brandName, m.factoryName
ORDER BY m.factoryName, totalSum DESC
;

#// (NFL,NTL) Sankey part1
SELECT SUM(s.totalPrice) as totalSum, m.medicineName, m.brandName, m.factoryName
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND c.year >=2012
AND	s.medicineKey = m.medicineKey
GROUP BY m.medicineName, m.brandName, m.factoryName
ORDER BY m.factoryName, totalSum DESC
;

SELECT SUM(s.totalPrice) as totalSum, m.medicineName, m.brandName, m.factoryName
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND c.year >=2012
AND	s.medicineKey = m.medicineKey
GROUP BY m.medicineName, m.brandName, m.factoryName
ORDER BY m.factoryName DESC, totalSum DESC
;

#// (NFL,NTL) Line
SELECT SUM(s.totalPrice) as totalSum, m.factoryName, c.year
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND c.year >= 2012
AND s.medicineKey = m.medicineKey
AND s.calendarKey = c.calendarKey
GROUP BY m.factoryName, c.year
ORDER BY c.year, m.factoryName ASC
;

#// (NFL,NTL) Combo Main Columns
SELECT SUM(s.totalPrice) as totalSum, m.factoryName, c.year
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND c.year >=2012
AND	s.medicineKey = m.medicineKey
GROUP BY c.year, m.factoryName
ORDER BY c.year, m.factoryName, totalSum DESC
;

#// (NFL,NTL) Combo AVG Line
SELECT AVG(t.totalSum) as avgSum, t.year
FROM (
SELECT SUM(s.totalPrice) as totalSum, m.factoryName, c.year
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND c.year >=2012
AND	s.medicineKey = m.medicineKey
GROUP BY c.year, m.factoryName
ORDER BY c.year, m.factoryName, totalSum DESC
) as t
GROUP BY t.year
;


##### ##### ##### ##### ##### ###### ##### ##### #####
##### ##### #####    1F1Y     ###### ##### ##### #####
##### ##### ##### ##### ##### ###### ##### ##### #####

# // (1F,1Y) Pie
SELECT SUM(s.totalPrice) as totalSum, m.brandName, m.factoryName
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND m.factoryName = 'Greenstone LLC'
AND c.year = 2014
AND	s.medicineKey = m.medicineKey
AND s.calendarKey = c.calendarKey
GROUP BY m.brandName, m.factoryName
;

#// (1F,1Y) Line
SELECT SUM(s.totalPrice) as totalSum, c.quarter, c.year, m.factoryName
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND m.factoryName = 'Greenstone LLC'
AND c.year = 2014
AND s.medicineKey = m.medicineKey
AND s.calendarKey = c.calendarKey
GROUP BY c.quarter, c.year
ORDER BY c.quarter, c.year ASC
;

#// (1F,1Y) Combo Main Columns
SELECT SUM(s.totalPrice) as totalSum, m.brandName, c.quarter
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND m.factoryName = 'Greenstone LLC'
AND c.year = 2014
AND	s.medicineKey = m.medicineKey
GROUP BY m.brandName, c.quarter
ORDER BY c.quarter, m.brandName, totalSum DESC
;

#// (1F,1Y) Combo AVG Line
SELECT AVG(t.totalSum) as avgSum, t.quarter
FROM (
SELECT SUM(s.totalPrice) as totalSum, m.brandName, c.quarter
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND m.factoryName = 'Greenstone LLC'
AND c.year = 2014
AND	s.medicineKey = m.medicineKey
GROUP BY m.brandName, c.quarter
ORDER BY c.quarter, m.brandName, totalSum DESC
) as t
GROUP BY t.quarter
;

#// (1F,1Y) Sankey part0
SELECT SUM(s.totalPrice) as totalSum, m.brandName, m.factoryName
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND m.factoryName = 'Greenstone LLC'
AND c.year = 2014
AND	s.medicineKey = m.medicineKey
GROUP BY m.brandName, m.factoryName
ORDER BY m.factoryName, totalSum DESC
;

#// (1F,1Y) Sankey part1
SELECT SUM(s.totalPrice) as totalSum, m.medicineName, m.brandName, m.factoryName
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND m.factoryName = 'Greenstone LLC'
AND c.year = 2014
AND	s.medicineKey = m.medicineKey
GROUP BY m.medicineName, m.brandName, m.factoryName
ORDER BY m.factoryName, totalSum DESC
;















##### Pie General #####
SELECT SUM(s.totalPrice) as totalSum, m.medicineName, m.brandName, m.factoryName
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND m.factoryName like '%Greenstone LLC%'
AND m.brandName like '%%'
AND m.medicineName like '%%'
AND c.year >= 2014
AND c.year <= 2014
AND c.quarter >= 1
AND c.quarter <= 4
AND c.month >= 1
AND c.month <=12
AND s.medicineKey = m.medicineKey
AND s.calendarKey = c.calendarKey
GROUP BY m.medicineName, m.brandName, m.factoryName DESC
;

##### Line General #####
SELECT SUM(s.totalPrice) as totalSum, c.year, c.quarter, m.factoryName
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND m.factoryName like 'Greenstone LLC'
AND m.brandName like '%%'
AND m.medicineName like '%%'
AND c.year >= 2014
AND c.year <= 2014
AND c.quarter >= 1
AND c.quarter <= 4
AND c.month >= 1
AND c.month <=12
AND s.medicineKey = m.medicineKey
AND s.calendarKey = c.calendarKey
GROUP BY c.year, c.quarter, m.factoryName
ORDER BY c.year, c.quarter, m.factoryName ASC
;







#$1$#:
 m.factoryName
 m.factoryName, m.brandName
 m.factoryName, m.brandName, m.medicineName
#$2$#:
 c.year
 c.quarter, c.year
 c.month, c.quarter, c.year
#$3$#:
 t.year
 t.quarter, t.year
 t.month, t.quarter, t.year

SELECT SUM(s.totalPrice) as totalSum, #$1$#, #$2$#
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND m.factoryName like '%%'
AND m.brandName like '%%'
AND m.medicineName like '%%'
AND c.year >= 0
AND c.year <= 9999
AND c.quarter >= 1
AND c.quarter <= 4
AND c.month >= 1
AND c.month <=12
AND s.medicineKey = m.medicineKey
AND s.calendarKey = c.calendarKey
GROUP BY #$1$#, #$2$#
ORDER BY #$2$#, #$1$#, totalSum DESC

SELECT AVG(t.totalSum) as avgSum, #$3$#
FROM (
SELECT SUM(s.totalPrice) as totalSum, #$1$#, #$2$#
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND m.factoryName like '%%'
AND m.brandName like '%%'
AND m.medicineName like '%%'
AND c.year >= 0
AND c.year <= 9999
AND c.quarter >= 1
AND c.quarter <= 4
AND c.month >= 1
AND c.month <=12
AND s.medicineKey = m.medicineKey
AND s.calendarKey = c.calendarKey
GROUP BY #$1$#, #$2$#
ORDER BY #$2$#, #$1$#, totalSum DESC
) as t
GROUP BY #$3$#
;




#// (NFL,NTL) Sankey part0
SELECT SUM(s.totalPrice) as totalSum, m.brandName, m.factoryName
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND c.year >=2012
AND	s.medicineKey = m.medicineKey
GROUP BY m.brandName, m.factoryName
ORDER BY m.factoryName, totalSum DESC
;

#// (1F,1Y) Sankey part0
SELECT SUM(s.totalPrice) as totalSum, m.brandName, m.factoryName
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND m.factoryName = 'Greenstone LLC'
AND c.year = 2014
AND	s.medicineKey = m.medicineKey
GROUP BY m.brandName, m.factoryName
ORDER BY m.factoryName, totalSum DESC
;

SELECT SUM(s.totalPrice) as totalSum, m.brandName, m.factoryName
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND m.factoryName like '%%'
AND m.brandName like '%%'
AND m.medicineName like '%%'
AND c.year >= 0
AND c.year <= 9999
AND c.quarter >= 1
AND c.quarter <= 4
AND c.month >= 1
AND c.month <=12
AND s.medicineKey = m.medicineKey
AND s.calendarKey = c.calendarKey
GROUP BY m.brandName, m.factoryName
ORDER BY m.factoryName, totalSum DESC
;



#// (1F,1Y) Sankey part1
SELECT SUM(s.totalPrice) as totalSum, m.medicineName, m.brandName, m.factoryName
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND m.factoryName = 'Greenstone LLC'
AND c.year = 2014
AND	s.medicineKey = m.medicineKey
GROUP BY m.medicineName, m.brandName, m.factoryName
ORDER BY m.factoryName, totalSum DESC
;

#// (NFL,NTL) Sankey part1
SELECT SUM(s.totalPrice) as totalSum, m.medicineName, m.brandName, m.factoryName
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND c.year >=2012
AND	s.medicineKey = m.medicineKey
GROUP BY m.medicineName, m.brandName, m.factoryName
ORDER BY m.factoryName, totalSum DESC
;

SELECT SUM(s.totalPrice) as totalSum, m.medicineName, m.brandName, m.factoryName
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND m.factoryName like '%%'
AND m.brandName like '%%'
AND m.medicineName like '%%'
AND c.year >= 0
AND c.year <= 9999
AND c.quarter >= 1
AND c.quarter <= 4
AND c.month >= 1
AND c.month <= 12
AND s.medicineKey = m.medicineKey
AND s.calendarKey = c.calendarKey
GROUP BY m.medicineName, m.brandName, m.factoryName
ORDER BY m.factoryName, totalSum DESC
;

