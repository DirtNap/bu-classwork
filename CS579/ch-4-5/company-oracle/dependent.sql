DROP TABLE dependent;
CREATE TABLE dependent (
  essn           char(9),
  dependent_name varchar2(15),
  sex            char,
  bdate          date,
  relationship   varchar2(8),
  CONSTRAINT pk_dependent PRIMARY KEY (essn, dependent_name),
  CONSTRAINT fk_dependent_employee FOREIGN KEY (essn) REFERENCES employee(ssn)
);
