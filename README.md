# 스키마
```sql
DROP TABLE people;

CREATE TABLE people (
person_id INT auto_increment PRIMARY KEY,
name varchar(20) NULL
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


INSERT INTO people
(name)
VALUES('a');
INSERT INTO people
(name)
VALUES('b');
INSERT INTO people
(name)
VALUES('c');
INSERT INTO people
(name)
VALUES('d');
INSERT INTO people
(name)
VALUES('e');
INSERT INTO people
(name)
VALUES('f');
INSERT INTO people
(name)
VALUES('g');
INSERT INTO people
(name)
VALUES('h');
INSERT INTO people
(name)
VALUES('l');
INSERT INTO people
(name)
VALUES('m');
INSERT INTO people
(name)
VALUES('n');
INSERT INTO people
(name)
VALUES('o');
INSERT INTO people
(name)
VALUES('p');
INSERT INTO people
(name)
VALUES('q');
INSERT INTO people
(name)
VALUES('r');
INSERT INTO people
(name)
VALUES('s');
INSERT INTO people
(name)
VALUES('t');
INSERT INTO people
(name)
VALUES('u');
INSERT INTO people
(name)
VALUES('v');
INSERT INTO people
(name)
VALUES('w');
INSERT INTO people
(name)
VALUES('x');
INSERT INTO people
(name)
VALUES('y');
INSERT INTO people
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