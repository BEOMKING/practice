-- 4장 집약과 자르기
--- 12강 집약
create table price_by_age
(
    product_id varchar(32) not null,
    log_age    integer     not null,
    high_age   integer     not null,
    price      int         not null,
    primary key (product_id, log_age),
    CHECK ( log_age <= high_age )
);
alter table price_by_age
    rename log_age to low_age;

INSERT INTO price_by_age
VALUES ('제품1', 0, 50, 2000),
       ('제품1', 51, 100, 3000),
       ('제품2', 0, 100, 4200),
       ('제품3', 0, 20, 500),
       ('제품3', 31, 70, 800),
       ('제품3', 71, 100, 1000),
       ('제품4', 0, 99, 8900);

SELECT *
from price_by_age;

SELECT product_id
FROM price_by_age
GROUP BY product_id
HAVING SUM(high_age - low_age + 1) = 101;

CREATE TABLE hotel_rooms
(
    room_nbr   INT,
    start_date DATE,
    end_date   DATE,
    PRIMARY KEY (room_nbr, start_date)
);

INSERT INTO hotel_rooms
VALUES (101, '2008-02-01', '2008-02-06'),
       (101, '2008-02-06', '2008-02-08'),
       (101, '2008-02-10', '2008-02-13');

SELECT room_nbr
FROM hotel_rooms
GROUP BY room_nbr
HAVING SUM(end_date - start_date) >= 10;

--- 13강 자르기
CREATE TABLE persons
(
    name   varchar,
    age    int,
    height float,
    weight float,
    PRIMARY KEY (name)
);

INSERT INTO persons
values ('anderson', 30, 188, 90),
       ('adela', 21, 167, 55),
       ('bates', 87, 158, 48),
       ('becky', 54, 187, 70),
       ('bill', 39, 177, 120),
       ('chris', 90, 175, 48);

explain
SELECT SUBSTRING(name, 1, 1), COUNT(*)
FROM persons
GROUP BY SUBSTRING(name, 1, 1);

select *
from persons;

SELECT CASE
           WHEN age < 20 THEN '어린이'
           WHEN age BETWEEN 20 AND 69 THEN '성인'
           WHEN age >= 70 THEN '노인' END AS classification,
       COUNT(*)
FROM persons
GROUP BY classification;

SELECT CASE
           WHEN (weight / POWER(height / 100, 2)) < 18.5 THEN '저체중'
           WHEN (weight / POWER(height / 100, 2)) >= 18.5 AND (weight / POWER(height / 100, 2)) < 25 THEN '정상'
           WHEN (weight / POWER(height / 100, 2)) > 25 THEN '과체중' END AS 분류,
       COUNT(*)
FROM persons
GROUP BY 분류;

SELECT name,
       age,
       CASE
           WHEN age < 20 THEN '어린이'
           WHEN age BETWEEN 20 AND 69 THEN '성인'
           WHEN age >= 70 THEN '노인' END AS 분류,
       RANK() OVER (PARTITION BY
           CASE
               WHEN age < 20 THEN '어린이'
               WHEN age BETWEEN 20 AND 69 THEN '성인'
               WHEN age >= 70 THEN '노인' END
           ORDER BY age DESC)
FROM persons;

-- 5장 반복문
--- 16강 SQL에서는 반복을 어떻게 표현할까?
CREATE TABLE sales
(
    company VARCHAR,
    year    INT,
    sales   INT
);

CREATE TABLE sales2
(
    company VARCHAR,
    year    INT,
    sales   INT,
    var     CHAR(1)
);

INSERT INTO sales
VALUES ('A', 2002, 50),
       ('A', 2003, 52),
       ('A', 2004, 55),
       ('B', 2002, 27),
       ('B', 2003, 28),
       ('B', 2004, 28),
       ('C', 2002, 40),
       ('C', 2003, 39),
       ('C', 2004, 35);

EXPLAIN
SELECT company,
       year,
       sales,
       CASE SIGN(sales - MAX(sales) OVER (PARTITION BY company ORDER BY year ROWS BETWEEN 1 PRECEDING AND 1 PRECEDING))
           WHEN 1 THEN '+'
           WHEN 0 THEN '='
           WHEN -1 THEN '-' END AS var
FROM sales;

EXPLAIN
SELECT company,
       year,
       sales,
       CASE SIGN(sales - (SELECT MAX(sales)
                          FROM sales AS s2
                          WHERE s2.company = s1.company
                            AND s2.year = s1.year - 1))
           WHEN 1 THEN '+'
           WHEN 0 THEN '='
           WHEN -1 THEN '-' END AS var
FROM sales AS s1;

EXPLAIN
SELECT company,
       year,
       sales,
       MAX(company)
       OVER (PARTITION BY company ORDER BY year ROWS BETWEEN 1 PRECEDING AND 1 PRECEDING)            AS before_company,
       MAX(sales) OVER (PARTITION BY company ORDER BY year ROWS BETWEEN 1 PRECEDING AND 1 PRECEDING) AS before_sales
FROM sales;

EXPLAIN
SELECT company,
       year,
       sales,
       (SELECT company
        FROM sales AS s2
        WHERE s2.company = s1.company
          AND s2.year = s1.year - 1) AS before_company,
       (SELECT sales
        FROM sales AS s2
        WHERE s2.company = s1.company
          AND s2.year = s1.year - 1) AS before_sales
FROM sales AS s1;

CREATE TABLE postal_code
(
    pcode         CHAR(7),
    district_name VARCHAR(32),
    CONSTRAINT postal_code_pkey PRIMARY KEY (pcode)
);

INSERT INTO postal_code
VALUES ('4130001', '시즈오카 아타미 이즈미'),
       ('4130002', '시즈오카 아타미 이즈산'),
       ('4130103', '시즈오카 아타미 아지로'),
       ('4130041', '시즈오카 아타미 아오바초');
EXPLAIN
SELECT pcode,
       district_name
FROM postal_code
WHERE CASE
          WHEN pcode LIKE '4130033' THEN 0
          WHEN pcode LIKE '413003%' THEN 1
          WHEN pcode LIKE '41300%' THEN 2
          WHEN pcode LIKE '4130%' THEN 3
          WHEN pcode LIKE '413%' THEN 4
          WHEN pcode LIKE '41%' THEN 5
          WHEN pcode LIKE '4%' THEN 6
          WHEN pcode LIKE '%' THEN 7 END =
      (SELECT MIN(CASE
                      WHEN pcode LIKE '4130033' THEN 0
                      WHEN pcode LIKE '413003%' THEN 1
                      WHEN pcode LIKE '41300%' THEN 2
                      WHEN pcode LIKE '4130%' THEN 3
                      WHEN pcode LIKE '413%' THEN 4
                      WHEN pcode LIKE '41%' THEN 5
                      WHEN pcode LIKE '4%' THEN 6
                      WHEN pcode LIKE '%' THEN 7 END)
       FROM postal_code);

EXPLAIN
SELECT pcode,
       district_name
FROM (SELECT pcode,
             district_name,
             CASE
                 WHEN pcode LIKE '4130033' THEN 0
                 WHEN pcode LIKE '413003%' THEN 1
                 WHEN pcode LIKE '41300%' THEN 2
                 WHEN pcode LIKE '4130%' THEN 3
                 WHEN pcode LIKE '413%' THEN 4
                 WHEN pcode LIKE '41%' THEN 5
                 WHEN pcode LIKE '4%' THEN 6
                 WHEN pcode LIKE '%' THEN 7 END                                                         AS hit_code,
             MIN(CASE
                     WHEN pcode LIKE '4130033' THEN 0
                     WHEN pcode LIKE '413003%' THEN 1
                     WHEN pcode LIKE '41300%' THEN 2
                     WHEN pcode LIKE '4130%' THEN 3
                     WHEN pcode LIKE '413%' THEN 4
                     WHEN pcode LIKE '41%' THEN 5
                     WHEN pcode LIKE '4%' THEN 6
                     WHEN pcode LIKE '%' THEN 7 END) OVER (ORDER BY CASE
                                                                        WHEN pcode LIKE '4130033' THEN 0
                                                                        WHEN pcode LIKE '413003%' THEN 1
                                                                        WHEN pcode LIKE '41300%' THEN 2
                                                                        WHEN pcode LIKE '4130%' THEN 3
                                                                        WHEN pcode LIKE '413%' THEN 4
                                                                        WHEN pcode LIKE '41%' THEN 5
                                                                        WHEN pcode LIKE '4%' THEN 6
                                                                        WHEN pcode LIKE '%' THEN 7 END) AS min_hit_code
      FROM postal_code) AS t
WHERE hit_code = min_hit_code;

CREATE TABLE postal_history
(
    name      CHAR(1),
    pcode     CHAR(7),
    new_pcode CHAR(7),
    CONSTRAINT postal_history_pkey PRIMARY KEY (name, pcode)
);

CREATE INDEX postal_history_new_pcode_idx ON postal_history (new_pcode);

INSERT INTO postal_history
VALUES ('A', '4130001', '4130002'),
       ('A', '4130002', '4130103'),
       ('A', '4130103', NULL),
       ('B', '4130041', NULL),
       ('C', '4103213', '4380824'),
       ('C', '4380824', NULL);

WITH RECURSIVE explosion (name, pcode, new_pcode, depth) AS
                   (SELECT name,
                           pcode,
                           new_pcode,
                           1
                    FROM postal_history
                    WHERE name = 'A'
                      AND new_pcode IS NULL
                    UNION
                    SELECT child.name, child.pcode, child.new_pcode, depth + 1
                    FROM explosion AS parent,
                         postal_history AS child
                    WHERE parent.name = child.name
                      AND parent.pcode = child.new_pcode)
SELECT name,
       pcode,
       new_pcode,
       depth
FROM explosion
WHERE depth = (SELECT MAX(depth) FROM explosion);

CREATE TABLE postal_history2
(
    name  CHAR(1),
    pcode CHAR(7),
    lft   REAL NOT NULL,
    rgt   REAL NOT NULL,
    CONSTRAINT pkey_postal_history2 PRIMARY KEY (name, pcode),
    CONSTRAINT uq_name_lft UNIQUE (name, lft),
    CONSTRAINT uq_name_rgt UNIQUE (name, rgt),
    CHECK (lft < rgt)
);

INSERT INTO postal_history2
VALUES ('A', '4130001', 0, 27),
       ('A', '4130002', 9, 18),
       ('A', '4130103', 12, 15),
       ('B', '4130041', 0, 27),
       ('C', '4103213', 0, 27),
       ('C', '4380824', 9, 18);

EXPLAIN
SELECT name,
       pcode,
       lft,
       rgt
FROM postal_history2 ph1
WHERE name = 'A'
  AND NOT EXISTS (SELECT *
                  FROM postal_history2 ph2
                  WHERE ph2.name = 'A'
                    AND ph1.lft > ph2.lft);

EXPLAIN
SELECT ph1.name,
       ph1.pcode,
       ph1.lft,
       ph1.rgt
FROM postal_history2 AS ph1,
     (SELECT MIN(lft) AS lft,
             MAX(rgt) AS rgt
      FROM postal_history2
      WHERE name = 'A'
      GROUP BY name) AS ph2
WHERE ph1.name = 'A'
  AND ph1.lft = ph2.lft
  AND ph1.rgt = ph2.rgt;

-- 6장 결합
--- 18강 기능적 관점으로 구분하는 결합의 종류
CREATE TABLE Employees
(
    emp_id   CHAR(9),
    emp_name VARCHAR(32),
    dept_id  CHAR(3),
    CONSTRAINT pk_emp PRIMARY KEY (emp_id)
);

INSERT INTO Employees
VALUES ('001', '하린', 10),
       ('002', '한미루', 11),
       ('003', '사라', 11),
       ('004', '중민', 12),
       ('005', '웅식', 12),
       ('006', '주아', 12),
       ('007', '희주', 13),
       ('008', '혜요', 13),
       ('009', '석호', 14),
       ('010', '종혁', 15),
       ('011', '렬', 16);

CREATE TABLE Departments
(
    dept_id   CHAR(3),
    dept_name VARCHAR(32),
    CONSTRAINT pk_dept PRIMARY KEY (dept_id)
);

INSERT INTO Departments
VALUES (10, '인사'),
       (11, '개발'),
       (12, '영업'),
       (13, '기획'),
       (14, '홍보'),
       (15, '총무'),
       (16, '경영');

-- Employees
SELECT *
FROM Employees;

-- Departments
SELECT *
FROM Departments;

-- Cross Join
SELECT *
FROM Employees
         CROSS JOIN Departments;

-- Inner Join
EXPLAIN
SELECT e.emp_id, e.emp_name, d.dept_id, d.dept_name
FROM Employees e
         INNER JOIN Departments d ON e.dept_id = d.dept_id;

-- Outer Join
SELECT *
FROM Departments
         LEFT OUTER JOIN Employees ON Departments.dept_id = Employees.dept_id;

ANALYZE Departments;
ANALYZE Employees;

EXPLAIN
SELECT dept_id, dept_name
FROM Departments D
WHERE NOT EXISTS (SELECT *
                  FROM Employees E
                  WHERE D.dept_id = E.dept_id);

-- 7강 서브쿼리
--- 21강 서브쿼리가 일으키는 폐혜
CREATE TABLE receipts
(
    customer_id CHAR(5),
    number      INTEGER,
    price       INTEGER,
    CONSTRAINT pk_receipts PRIMARY KEY (customer_id, number)
);

INSERT INTO receipts
VALUES ('A', 1, 500),
       ('A', 2, 1000),
       ('A', 3, 700),
       ('B', 5, 100),
       ('B', 6, 5000),
       ('B', 7, 300),
       ('B', 9, 200),
       ('B', 12, 1000),
       ('C', 10, 100),
       ('C', 45, 200),
       ('C', 70, 50),
       ('D', 3, 2000);

SELECT r1.customer_id, number, price
FROM receipts AS r1
         INNER JOIN (SELECT customer_id, MIN(number)
                     FROM receipts
                     GROUP BY customer_id) AS r2
                    ON r1.customer_id = r2.customer_id
                        AND r1.number = r2.min;

SELECT customer_id, number, price
FROM receipts r1
WHERE number = (SELECT MIN(number)
                FROM receipts r2
                WHERE r1.customer_id = r2.customer_id);

SELECT customer_id, number, price
FROM (SELECT customer_id,
             number,
             price,
             ROW_NUMBER() OVER (PARTITION BY customer_id ORDER BY number) AS row_num
      FROM receipts) AS r
WHERE row_num = 1;

SELECT tmp_min.customer_id, tmp_min.price - tmp_max.price AS diff
FROM (SELECT r1.customer_id, r1.number, r1.price
      FROM receipts r1
               INNER JOIN (SELECT customer_id, MIN(number) AS min
                           FROM receipts
                           GROUP BY customer_id) AS r2
                          ON r1.customer_id = r2.customer_id
                              AND r1.number = r2.min) AS tmp_min
         INNER JOIN
     (SELECT r3.customer_id, r3.number, r3.price
      FROM receipts r3
               INNER JOIN (SELECT customer_id, MAX(number) AS max
                           FROM receipts
                           GROUP BY customer_id) r4
                          ON r3.customer_id = r4.customer_id
                              AND r3.number = r4.max) AS tmp_max
     ON tmp_min.customer_id = tmp_max.customer_id
ORDER BY customer_id;

SELECT customer_id,
       SUM(CASE WHEN min_num = 1 THEN price ELSE 0 END) - SUM(CASE WHEN max_num = 1 THEN price ELSE 0 END) AS diff
FROM (SELECT customer_id,
             number,
             price,
             ROW_NUMBER() OVER (PARTITION BY customer_id ORDER BY number)      AS min_num,
             ROW_NUMBER() OVER (PARTITION BY customer_id ORDER BY number DESC) AS max_num
      FROM receipts) AS r
WHERE min_num = 1
   OR max_num = 1
GROUP BY customer_id;

--- 22강 서브쿼리 사용이 더 나은 경우
CREATE TABLE companies
(
    company_code CHAR(3),
    district     CHAR(2),
    CONSTRAINT pk_companies PRIMARY KEY (company_code)
);

INSERT INTO companies
VALUES ('001', 'A'),
       ('002', 'B'),
       ('003', 'C'),
       ('004', 'D');

CREATE TABLE shops
(
    shop_code       CHAR(3),
    company_code    CHAR(3),
    employee_number INTEGER,
    main_flg        CHAR(1),
    CONSTRAINT pk_shops PRIMARY KEY (shop_code, company_code)
);

INSERT INTO shops
VALUES ('1', '001', 300, 'Y'),
       ('2', '001', 400, 'N'),
       ('3', '001', 250, 'Y'),
       ('1', '002', 100, 'Y'),
       ('2', '002', 20, 'N'),
       ('1', '003', 400, 'Y'),
       ('2', '003', 500, 'Y'),
       ('3', '003', 300, 'N'),
       ('4', '003', 200, 'Y'),
       ('1', '004', 999, 'Y');

---- companies(4) * shops(10)
SELECT c.company_code, MAX(c.district), SUM(employee_number) AS sum_emp
FROM companies c
         INNER JOIN shops s
                    ON c.company_code = s.company_code
WHERE main_flg = 'Y'
GROUP BY c.company_code;

---- companies(4) * group_shops(4)
SELECT c.company_code, c.district, s.sum_emp
FROM companies c
         INNER JOIN
     (SELECT company_code, SUM(employee_number) AS sum_emp
      FROM shops
      WHERE main_flg = 'Y'
      GROUP BY company_code) AS s
     ON c.company_code = s.company_code;

-- 8장 SQL의 순서
--- 23강 레코드에 순서 붙이기
CREATE TABLE weights
(
    student_id CHAR(5),
    weight     INTEGER,
    CONSTRAINT pk_weights PRIMARY KEY (student_id)
);

INSERT INTO weights
VALUES ('A100', 50),
       ('A101', 55),
       ('A124', 55),
       ('B343', 60),
       ('B346', 72),
       ('C563', 72),
       ('C345', 72);

SELECT student_id,
       weight,
       ROW_NUMBER() OVER (ORDER BY student_id) AS row_num
FROM weights;

SELECT student_id,
       weight,
       (SELECT COUNT(*)
        FROM weights w2
        WHERE w2.student_id <= w1.student_id) AS row_num
FROM weights w1;

CREATE TABLE weight2
(
    class      INTEGER,
    student_id CHAR(5),
    weight     INTEGER,
    CONSTRAINT pk_weight2 PRIMARY KEY (class, student_id)
);

INSERT INTO weight2
VALUES (1, '100', 50),
       (1, '101', 55),
       (1, '102', 56),
       (2, '100', 60),
       (2, '101', 72),
       (2, '102', 73),
       (2, '103', 73);

SELECT class,
       student_id,
       weight,
       ROW_NUMBER() OVER (ORDER BY class, student_id) AS row_num
FROM weight2;

SELECT class,
       student_id,
       (SELECT COUNT(*)
        FROM weight2 w2
        WHERE (w2.class, w2.student_id) <= (w1.class, w1.student_id)) AS row_num
FROM weight2 w1;

SELECT class,
       student_id,
       ROW_NUMBER() OVER (PARTITION BY class ORDER BY student_id) AS row_num
FROM weight2;

CREATE TABLE weight3
(
    class      INTEGER,
    student_id CHAR(5),
    weight     INTEGER,
    row_num    INTEGER,
    CONSTRAINT pk_weight3 PRIMARY KEY (class, student_id)
);

INSERT INTO weight3
VALUES (1, '100', 50, null),
       (1, '101', 55, null),
       (1, '102', 56, null),
       (2, '100', 60, null),
       (2, '101', 72, null),
       (2, '102', 73, null),
       (2, '103', 73, null);

UPDATE weight3
SET row_num = (SELECT row_num
               FROM (SELECT class, student_id, ROW_NUMBER() OVER (PARTITION BY class ORDER BY student_id) AS row_num
                     FROM weight3) AS w
               WHERE weight3.class = w.class
                 AND weight3.student_id = w.student_id);

UPDATE weight3
SET row_num = (SELECT COUNT(*)
               FROM weight3 AS w2
               WHERE w2.class = weight3.class
                 AND w2.student_id <= weight3.student_id);

--- 24강 레코드에 순번 붙이기 응용
SELECT AVG(weight)
FROM (SELECT w1.weight
      FROM weights w1,
           weights w2
      GROUP BY w1.weight
      HAVING SUM(CASE WHEN w2.weight >= w1.weight THEN 1 ELSE 0 END) >= COUNT(*) / 2
         AND SUM(CASE WHEN w2.weight <= w1.weight THEN 1 ELSE 0 END) >= COUNT(*) / 2) as temp;

SELECT AVG(weight) AS median
FROM (SELECT weight,
             ROW_NUMBER() OVER (ORDER BY weight ASC, student_id ASC)   AS hi,
             ROW_NUMBER() OVER (ORDER BY weight DESC, student_id DESC) AS lo
      FROM weights) AS temp
WHERE hi IN (lo, lo - 1, lo + 1);

SELECT AVG(weight) AS median
FROM (SELECT weight,
             2 * ROW_NUMBER() OVER (ORDER BY weight)
                 - COUNT(*) OVER ()  AS diff
      FROM weights) AS temp
WHERE diff BETWEEN 0 AND 2;

--- 25강 시퀀스 객체, IDENTITY 필드, 채번 테이블
CREATE SEQUENCE seq
START WITH 1
INCREMENT BY 1
MAXVALUE 100
MINVALUE 1
CYCLE;

CREATE TABLE seq_test
(
    id   INTEGER,
    name VARCHAR(10)
);

INSERT INTO seq_test
VALUES (NEXTVAL('seq'), 'A');

SELECT *
FROM seq_test;

-- 9장 갱신과 데이터 모델
--- 26강 갱신은 효율적으로
