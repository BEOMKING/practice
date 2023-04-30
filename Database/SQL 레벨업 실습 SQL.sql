-- 12강
create table price_by_age (
                              product_id varchar(32) not null,
                              log_age integer not null,
                              high_age integer not null,
                              price int not null,
                              primary key (product_id, log_age),
                              CHECK ( log_age <= high_age )
);
alter table price_by_age rename log_age to low_age;

INSERT INTO price_by_age
VALUES  ('제품1', 0, 50, 2000),
        ('제품1', 51, 100, 3000),
        ('제품2', 0, 100, 4200),
        ('제품3', 0, 20, 500),
        ('제품3', 31, 70, 800),
        ('제품3', 71, 100, 1000),
        ('제품4', 0, 99, 8900);

SELECT * from price_by_age;

SELECT product_id FROM price_by_age
GROUP BY product_id
HAVING SUM(high_age - low_age + 1) = 101;

CREATE TABLE hotel_rooms
(
    room_nbr INT,
    start_date DATE,
    end_date DATE,
    PRIMARY KEY (room_nbr, start_date)
);

INSERT INTO hotel_rooms
VALUES (101, '2008-02-01', '2008-02-06'),
       (101, '2008-02-06', '2008-02-08'),
       (101, '2008-02-10', '2008-02-13');

SELECT room_nbr FROM hotel_rooms
GROUP BY room_nbr
HAVING SUM(end_date - start_date) >= 10;

-- 13강
CREATE TABLE persons
(
    name varchar,
    age int,
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

explain SELECT SUBSTRING(name, 1, 1), COUNT(*) FROM persons
        GROUP BY SUBSTRING(name, 1, 1);

select * from persons;

SELECT
    CASE WHEN age < 20 THEN '어린이'
         WHEN age BETWEEN 20 AND 69 THEN '성인'
         WHEN age >= 70 THEN '노인' END AS classification,
    COUNT(*)
FROM persons
GROUP BY classification;

SELECT
    CASE WHEN (weight / POWER(height / 100, 2)) < 18.5 THEN '저체중'
         WHEN (weight / POWER(height / 100, 2)) >= 18.5 AND (weight / POWER(height / 100, 2)) < 25 THEN '정상'
         WHEN (weight / POWER(height / 100, 2)) > 25 THEN '과체중' END AS 분류,
    COUNT(*)
FROM persons
GROUP BY 분류;

SELECT name, age,
       CASE WHEN age < 20 THEN '어린이'
            WHEN age BETWEEN 20 AND 69 THEN '성인'
            WHEN age >= 70 THEN '노인' END AS 분류,
       RANK() OVER(PARTITION BY
           CASE WHEN age < 20 THEN '어린이'
                WHEN age BETWEEN 20 AND 69 THEN '성인'
                WHEN age >= 70 THEN '노인' END
           ORDER BY age DESC)
FROM persons;

-- 14강