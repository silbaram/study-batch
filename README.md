# 스키마
```sql
DROP TABLE people;

CREATE TABLE spring_batch.people (
person_id INT auto_increment PRIMARY KEY,
name varchar(20) NULL
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


INSERT INTO spring_batch.people
(name)
VALUES('a');
INSERT INTO spring_batch.people
(name)
VALUES('b');
INSERT INTO spring_batch.people
(name)
VALUES('c');
INSERT INTO spring_batch.people
(name)
VALUES('d');
INSERT INTO spring_batch.people
(name)
VALUES('e');
INSERT INTO spring_batch.people
(name)
VALUES('f');
INSERT INTO spring_batch.people
(name)
VALUES('g');
INSERT INTO spring_batch.people
(name)
VALUES('h');
INSERT INTO spring_batch.people
(name)
VALUES('l');
INSERT INTO spring_batch.people
(name)
VALUES('m');
INSERT INTO spring_batch.people
(name)
VALUES('n');
INSERT INTO spring_batch.people
(name)
VALUES('o');
INSERT INTO spring_batch.people
(name)
VALUES('p');
INSERT INTO spring_batch.people
(name)
VALUES('q');
INSERT INTO spring_batch.people
(name)
VALUES('r');
INSERT INTO spring_batch.people
(name)
VALUES('s');
INSERT INTO spring_batch.people
(name)
VALUES('t');
INSERT INTO spring_batch.people
(name)
VALUES('u');
INSERT INTO spring_batch.people
(name)
VALUES('v');
INSERT INTO spring_batch.people
(name)
VALUES('w');
INSERT INTO spring_batch.people
(name)
VALUES('x');
INSERT INTO spring_batch.people
(name)
VALUES('y');
INSERT INTO spring_batch.people
(name)
VALUES('z');
```



# 배치 실행
- JobParametersJobConfiguration
    - --job.name=parametersJob requestDate=20210211
- StepNextJobConfiguration
    - --job.name=stepNextJob version=2
- StepNextConditionalJobConfiguration
    - --job.name=stepNextConditionalJob version=3
- DeciderJobConfiguration
    - --job.name=deciderJob version=1
- JdbcDataConfiguration
    - --job.name=jdbcDataJob version=3
- JdbcListDataConfiguration
    - --job.name=jdbcListDataJob version=9