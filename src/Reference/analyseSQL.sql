
# For empty Factory Level and empty Time Level
SELECT SUM(s.totalPrice), m.factoryName, c.year
FROM SaleTransaction s, Medicine m, Calendar c
WHERE s.medicineKey <> -1
AND	s.medicineKey = m.medicineKey
AND s.calendarKey = c.calendarKey
GROUP BY m.factoryName, c.year
	;