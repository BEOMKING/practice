-- 12강
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

-- 13강
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

-- 16강
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

-- 18강

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




























