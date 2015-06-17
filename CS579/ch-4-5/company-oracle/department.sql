DROP TABLE department;
CREATE TABLE department (
  dname        varchar2(25) not null,
  dnumber      number(4,0),
  mgrssn       char(9) not null, 
  mgrstartdate date,
  CONSTRAINT pk_department PRIMARY KEY (dnumber),
  CONSTRAINT uq_department_dname UNIQUE (dname)
);
