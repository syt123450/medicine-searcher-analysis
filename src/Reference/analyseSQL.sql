
SELECT SUM(s.totalPrice) as totalSum, m.factoryName
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND c.fullDate = subdate(current_date, 1)
AND	s.medicineKey = m.medicineKey
AND s.calendarKey = c.calendarKey
GROUP BY m.factoryName
;

# For empty Factory Level and empty Time Level
SELECT SUM(s.totalPrice) as totalSum, m.factoryName
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND c.year >= 2012
AND	s.medicineKey = m.medicineKey
AND s.calendarKey = c.calendarKey
GROUP BY m.factoryName
;

SELECT SUM(s.totalPrice) as totalSum, m.brandName, m.factoryName
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND c.year >=2012
AND	s.medicineKey = m.medicineKey
GROUP BY m.brandName, m.factoryName
ORDER BY m.factoryName, totalSum DESC
;

SELECT SUM(s.totalPrice) as totalSum, m.medicineName, m.brandName
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND c.year >=2012
AND	s.medicineKey = m.medicineKey
GROUP BY m.medicineName, m.brandName
ORDER BY m.brandName, totalSum DESC
;

SELECT SUM(s.totalPrice) as totalSum, m.factoryName, c.year
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND c.year >= 2012
AND	s.medicineKey = m.medicineKey
AND s.calendarKey = c.calendarKey
GROUP BY m.factoryName, c.year
ORDER BY c.year, m.factoryName ASC
;

