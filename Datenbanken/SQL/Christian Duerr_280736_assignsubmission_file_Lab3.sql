##Lab3 by Christian Duerr, 756424##

7.1

SELECT r.aggregate_id AS engine, sd.station_text, r.sequence_no AS no, r.station_leaving_date 
FROM resumees AS r 
INNER JOIN station_descriptions AS sd 
ON r.station_id = sd.station_id 
WHERE aggregate_id='XL  0026065'
ORDER BY r.station_leaving_date ASC


7.2

SELECT r.aggregate_id AS engine, sd.station_text, r.sequence_no AS no, r.station_leaving_date 
FROM resumees AS r 
INNER JOIN station_descriptions AS sd 
ON r.station_id = sd.station_id 
WHERE aggregate_id='XL  0026065' 
UNION 
SELECT aggregate_id AS engine, 'Assembly began' as station_text, '0' AS no, assembly_start_date AS station_leaving_date 
FROM headers 
WHERE aggregate_id='XL  0026065' 
UNION 
SELECT aggregate_id AS engine, 'Assembly finished' AS station_text, '0' AS no, assembly_end_date AS station_leaving_date 
FROM headers 
WHERE aggregate_id='XL  0026065' 
ORDER BY station_leaving_date ASC


8.1

SELECT mv.aggregate_id, fd.feature_text,  sd.station_text, mv.sequence_no AS no, el.error_code AS err
FROM measured_values AS mv
INNER JOIN station_descriptions sd
ON mv.station_id = sd.station_id
INNER JOIN error_logs AS el
ON mv.sequence_no = el.sequence_no
INNER JOIN feature_descriptions AS fd
ON mv.feature_id = fd.feature_id
WHERE el.error_code='NIO' AND el.station_id=mv.station_id AND mv.station_id=sd.station_id AND mv.sequence_no=el.sequence_no
--Non: -- do not hardcode the result!
-- Instead think of the join criterias.
-- Have a look on the ER-Diagram. Some Joins are based on more than one column
-- per table. These joins will result in an ON-Clause with more than one
-- criteria with an AND in between.
-- Do not mix-up join criteria (ON-clause) and search criteria (WHERE-clause)



9.1

SELECT ec.error_text, COUNT(el.error_code) AS count FROM error_catalog AS ec 
INNER JOIN error_logs AS el 
ON ec.error_code = el.error_code 
GROUP BY ec.error_text 
ORDER BY ec.error_text ASC


9.2

SELECT ec.error_text, COUNT(el.error_code) AS count FROM error_catalog AS ec 
LEFT OUTER JOIN error_logs AS el 
ON ec.error_code = el.error_code 
GROUP BY ec.error_text 
ORDER BY ec.error_text ASC


9.3
--Non: a verbal answer was expected aiming behaviour of the COUNT-function in a context of NULL-values.
a) CREATE TABLE temporary.labor (Error_Text VARCHAR(40), Count VARCHAR(10) DEFAULT '0')

INSERT INTO temporary.labor(error_text, count) 
SELECT error_text, null 
FROM error_catalog

UPDATE temporary.labor SET COUNT = '0'

b)

INSERT INTO temporary.labor(error_text, COUNT)
SELECT null, COUNT(el.error_code) AS count 
FROM error_catalog AS ec 
LEFT OUTER JOIN error_logs AS el 
ON ec.error_code=el.error_code 
GROUP BY ec.error_text 
ORDER BY ec.error_text ASC


INSERT temporary.labor 
SET count = (SELECT COUNT(el.error_code) AS count 
FROM error_catalog AS ec LEFT OUTER JOIN error_logs AS el 
ON ec.error_code=el.error_code 
GROUP BY ec.error_text 
ORDER BY ec.error_text ASC)

c)

SELECT SUM(count) AS sum 
FROM temporary.labor 
GROUP BY error_text ASC


10
-- Non: do not hardcode the result!
-- Non: Have a look on slide package SQL Joins, slide #13
1. CREATE TABLE duration.labor (slowest VARCHAR(10), AVERAGE VARCHAR(10), FASTEST VARCHAR(10), COUNT VARCHAR(10), STDDEV VARCHAR(10))

2. SELECT aggregate_id, assembly_end_date - assembly_start_date AS Duration 
   FROM headers
3. #slowest: 1015947 average: add all values / 8 fastest: 64354 count: 8 
   INSERT INTO duration.labor (slowest, average, fastest, count, stddev) VALUES (1015947, (163014+203755+84005+1015947+82257+75301+93357+64354)/8, 64354, 8, 0)

4. UPDATE duration.labor 
   SET STDDEV = (((163014-average)^2)/count + ((203755-average)^2)/count + ((84005-average)^2)/count + ((1015947-average)^2)/count + ((82257-average)^2)/count + ((75301-average)^2)/count + ((93357-average)^2)/count + ((64354-average)^2)/count)




