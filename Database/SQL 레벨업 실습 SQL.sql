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
                 - COUNT(*) OVER () AS diff
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
CREATE TABLE omit
(
    keycol CHAR(1),
    seq    INTEGER,
    val    INTEGER,
    CONSTRAINT pk_omit PRIMARY KEY (keycol, seq)
);

INSERT INTO omit
VALUES ('A', 1, 50),
       ('A', 2, NULL),
       ('A', 3, NULL),
       ('A', 4, 70),
       ('A', 5, NULL),
       ('A', 6, 900),
       ('B', 1, 10),
       ('B', 2, 20),
       ('B', 3, NULL),
       ('B', 4, 3),
       ('B', 5, NULL),
       ('B', 6, NULL);

DELETE
FROM omit;

UPDATE omit
SET val = (SELECT val
           FROM omit AS o2
           WHERE o2.keycol = omit.keycol
             AND o2.seq = (SELECT MAX(seq)
                           FROM omit AS o3
                           WHERE o3.keycol = omit.keycol
                             AND o3.seq < omit.seq
                             AND o3.val IS NOT NULL))
WHERE val IS NULL;

--- 27강 레코드에서 필드로의 갱신
CREATE TABLE score_rows
(
    student_id CHAR(5),
    subject    CHAR(3),
    score      INTEGER,
    CONSTRAINT pk_score PRIMARY KEY (student_id, subject)
);

INSERT INTO score_rows
VALUES ('A001', '영어', 100),
       ('A001', '국어', 58),
       ('A001', '수학', 90),
       ('B002', '영어', 77),
       ('B002', '국어', 60),
       ('C003', '영어', 52),
       ('C003', '국어', 49),
       ('C003', '사회', 100);

CREATE TABLE score_cols
(
    student_id CHAR(5),
    score_en   INTEGER,
    score_nl   INTEGER,
    score_math INTEGER,
    CONSTRAINT pk_score_cols PRIMARY KEY (student_id)
);

INSERT INTO score_cols
VALUES ('A001', NULL, NULL, NULL),
       ('B002', NULL, NULL, NULL),
       ('C003', NULL, NULL, NULL),
       ('D004', NULL, NULL, NULL);

UPDATE score_cols
SET score_en   = (SELECT score
                  FROM score_rows
                  WHERE student_id = score_cols.student_id
                    AND subject = '영어'),
    score_nl   = (SELECT score
                  FROM score_rows
                  WHERE student_id = score_cols.student_id
                    AND subject = '국어'),
    score_math = (SELECT score
                  FROM score_rows
                  WHERE student_id = score_cols.student_id
                    AND subject = '수학');

UPDATE score_cols
SET (score_en, score_nl, score_math) = (SELECT MAX(CASE WHEN subject = '영어' THEN score ELSE score_en END)   AS score_en,
                                               MAX(CASE WHEN subject = '국어' THEN score ELSE score_nl END)   AS score_nl,
                                               MAX(CASE WHEN subject = '수학' THEN score ELSE score_math END) AS score_math
                                        FROM score_rows
                                        WHERE student_id = score_cols.student_id);

UPDATE score_cols
SET (score_en, score_nl, score_math) = (SELECT MAX(CASE WHEN subject = '영어' THEN score ELSE score_en END)   AS score_en,
                                               MAX(CASE WHEN subject = '국어' THEN score ELSE score_nl END)   AS score_nl,
                                               MAX(CASE WHEN subject = '수학' THEN score ELSE score_math END) AS score_math
                                        FROM score_rows
                                        WHERE student_id = score_cols.student_id
                                        GROUP BY student_id);

CREATE TABLE score_cols2
(
    student_id CHAR(5),
    score_en   INTEGER NOT NULL,
    score_nl   INTEGER NOT NULL,
    score_math INTEGER NOT NULL,
    CONSTRAINT pk_score_cols2 PRIMARY KEY (student_id)
);

INSERT INTO score_cols2
VALUES ('A001', 0, 0, 0),
       ('B002', 0, 0, 0),
       ('C003', 0, 0, 0),
       ('D004', 0, 0, 0);

UPDATE score_cols2
SET (score_en, score_nl, score_math) = (SELECT COALESCE(MAX(CASE WHEN subject = '영어' THEN score ELSE score_en END), 0) AS score_en,
                                               COALESCE(MAX(CASE WHEN subject = '국어' THEN score ELSE score_nl END), 0) AS score_nl,
                                               COALESCE(MAX(CASE WHEN subject = '수학' THEN score ELSE score_math END),
                                                        0)                                                             AS score_math
                                        FROM score_rows
                                        WHERE student_id = score_cols2.student_id)
WHERE EXISTS (SELECT *
              FROM score_rows
              WHERE student_id = score_cols2.student_id);

MERGE INTO score_cols2
USING (SELECT student_id,
              COALESCE(MAX(CASE WHEN subject = '영어' THEN score END), 0) AS score_en,
              COALESCE(MAX(CASE WHEN subject = '국어' THEN score END), 0) AS score_nl,
              COALESCE(MAX(CASE WHEN subject = '수학' THEN score END), 0) AS score_math
       FROM score_rows
       GROUP BY student_id) AS temp
ON score_cols2.student_id = temp.student_id
WHEN MATCHED THEN
    UPDATE
    SET score_en   = temp.score_en,
        score_nl   = temp.score_nl,
        score_math = temp.score_math;

--- 28강 필드에서 레코드로 변경
DELETE
FROM score_rows;
DELETE
FROM score_cols;

INSERT INTO score_rows
VALUES ('A001', '영어', NULL),
       ('A001', '국어', NULL),
       ('A001', '수학', NULL),
       ('B002', '영어', NULL),
       ('B002', '국어', NULL),
       ('C003', '영어', NULL),
       ('C003', '국어', NULL),
       ('C003', '사회', NULL);

INSERT INTO score_cols
VALUES ('A001', 100, 58, 90),
       ('B002', 70, 60, NULL),
       ('C003', 52, 49, NULL),
       ('D004', 10, 70, 100);

UPDATE score_rows
SET (score) = (SELECT CASE
                          WHEN subject = '영어' THEN score_en
                          WHEN subject = '국어' THEN score_nl
                          WHEN subject = '수학' THEN score_math END
               FROM score_cols
               WHERE score_rows.student_id = score_cols.student_id);

INSERT INTO score_rows
VALUES ('A001', '영어', 0),
       ('A001', '국어', 0),
       ('A001', '수학', 0),
       ('B002', '영어', 0),
       ('B002', '국어', 0),
       ('C003', '영어', 0),
       ('C003', '국어', 0),
       ('C003', '사회', 0);

INSERT INTO score_cols
VALUES ('A001', 100, 58, 90),
       ('B002', 70, 60, NULL),
       ('C003', 52, 49, NULL),
       ('D004', 10, 70, 100);

UPDATE score_rows
SET (score) = (SELECT CASE
                          WHEN subject = '영어' THEN score_en
                          WHEN subject = '국어' THEN score_nl
                          WHEN subject = '수학' THEN score_math
                          ELSE 0 END
               FROM score_cols
               WHERE score_rows.student_id = score_cols.student_id);

SELECT *
FROM score_rows;

--- 29강 같은 테이블의 다른 레코드로 갱신
CREATE TABLE stocks
(
    brand     VARCHAR(8),
    sale_date DATE,
    price     INTEGER,
    CONSTRAINT pk_stocks PRIMARY KEY (brand, sale_date)
);

INSERT INTO stocks
VALUES ('A철강', '2008-07-01', 1000),
       ('A철강', '2008-07-04', 1200),
       ('A철강', '2008-08-12', 800),
       ('B상사', '2008-06-04', 3000),
       ('B상사', '2008-09-11', 3000),
       ('C전기', '2008-07-01', 9000),
       ('D산업', '2008-06-04', 5000),
       ('D산업', '2008-06-05', 5000),
       ('D산업', '2008-06-06', 4800),
       ('D산업', '2008-12-01', 5100);

CREATE TABLE stocks2
(
    brand     VARCHAR(8),
    sale_date DATE,
    price     INTEGER,
    trend     CHAR(3),
    CONSTRAINT pk_stocks2 PRIMARY KEY (brand, sale_date)
);

INSERT INTO stocks2
    (SELECT brand,
            sale_date,
            price,
            CASE SIGN(price - LAG(price) OVER (PARTITION BY brand ORDER BY sale_date))
                WHEN 1 THEN '상승'
                WHEN 0 THEN '보합'
                WHEN -1 THEN '하락' END AS trend
     FROM stocks);

INSERT INTO stocks2
    (SELECT brand,
            sale_date,
            price,
            CASE SIGN(price -
                      MAX(price) OVER (PARTITION BY brand ORDER BY sale_date ROWS BETWEEN 1 PRECEDING AND 1 PRECEDING))
                WHEN 1 THEN '상승'
                WHEN 0 THEN '보합'
                WHEN -1 THEN '하락' END AS trend
     FROM stocks);

--- 30강 갱신이 초래하는 트레이드오프
CREATE TABLE orders
(
    order_id   INTEGER,
    order_shop VARCHAR(32),
    order_name VARCHAR(32),
    order_date DATE,
    CONSTRAINT pk_orders PRIMARY KEY (order_id)
);

INSERT INTO orders
VALUES (10000, '서울', '윤인성', '2011/8/22'),
       (10001, '인천', '연하진', '2011/9/1'),
       (10002, '인천', '패밀리마트', '2011/9/20'),
       (10003, '부천', '한빛미디어', '2011/8/5'),
       (10004, '수원', '동네슈퍼', '2011/8/22'),
       (10005, '성남', '야근카페', '2011/8/29');

CREATE TABLE order_receipts
(
    order_id         INTEGER,
    order_receipt_id INTEGER,
    item_group       VARCHAR(32),
    delivery_date    DATE,
    CONSTRAINT pk_order_receipts PRIMARY KEY (order_id, order_receipt_id)
);

INSERT INTO order_receipts
VALUES (10000, 1, '식기', '2011/8/24'),
       (10000, 2, '과자', '2011/8/25'),
       (10000, 3, '소고기', '2011/8/26'),
       (10001, 1, '어패류', '2011/9/4'),
       (10002, 1, '과자', '2011/9/22'),
       (10002, 2, '조미료 세트', '2011/9/22'),
       (10003, 1, '쌀', '2011/8/6'),
       (10003, 2, '소고기', '2011/8/10'),
       (10003, 3, '식기', '2011/8/10'),
       (10004, 1, '야채', '2011/8/23'),
       (10005, 1, '음료수', '2011/8/30'),
       (10005, 2, '과자', '2011/8/30');

SELECT o.order_id,
       o.order_name,
       MAX(orc.delivery_date - o.order_date) AS diff_days
FROM orders o
         INNER JOIN order_receipts orc
                    ON o.order_id = orc.order_id
WHERE orc.delivery_date - o.order_date >= 3
GROUP BY o.order_id;

CREATE TABLE orders2
(
    order_id      INTEGER,
    order_shop    VARCHAR(32),
    order_name    VARCHAR(32),
    order_date    DATE,
    del_late_flag INTEGER,
    CONSTRAINT pk_orders2 PRIMARY KEY (order_id)
);

INSERT INTO orders2
VALUES (10000, '서울', '윤인성', '2011/8/22', 1),
       (10001, '인천', '연하진', '2011/9/1', 1),
       (10002, '인천', '패밀리마트', '2011/9/20', 0),
       (10003, '부천', '한빛미디어', '2011/8/5', 1),
       (10004, '수원', '동네슈퍼', '2011/8/22', 1),
       (10005, '성남', '야근카페', '2011/8/29', 0);

--- 32강 시야 협착: 관련 문제
SELECT o.order_id,
       o.order_name,
       o.order_date,
       COUNT(*)
FROM orders o
         INNER JOIN order_receipts orc
                    ON o.order_id = orc.order_id
GROUP BY o.order_id;

SELECT o.order_id,
       o.order_name,
       o.order_date,
       COUNT(*) OVER (PARTITION BY o.order_id) AS cnt
FROM orders o
         INNER JOIN order_receipts orc
                    ON o.order_id = orc.order_id;

CREATE INDEX idx_orders_order_id_name ON orders (order_id, order_name);