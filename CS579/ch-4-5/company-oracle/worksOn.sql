DROP TABLE works_on;
CREATE TABLE works_on (
  essn   char(9),
  pno    number(4,0),
  hours  number(4,1),
  CONSTRAINT pk_works_on PRIMARY KEY (essn, pno),
  CONSTRAINT fk_works_on_employee FOREIGN KEY (essn) REFERENCES employee(ssn),
  CONSTRAINT fk_works_on_project FOREIGN KEY (pno) REFERENCES project(pnumber)
);
