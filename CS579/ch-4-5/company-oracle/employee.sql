DROP TABLE employee;
CREATE TABLE employee (
  fname    varchar2(15) not null, 
  minit    varchar2(1),
  lname    varchar2(15) not null,
  ssn      char(9),
  bdate    date,
  address  varchar2(50),
  sex      char,
  salary   number(10,2),
  superssn char(9),
  dno      number(4,0),
  CONSTRAINT pk_employee PRIMARY KEY (ssn),
  CONSTRAINT fk_employee_employee FOREIGN KEY (superssn) REFERENCES employee(ssn),
  CONSTRAINT fk_employee_department FOREIGN KEY (dno) REFERENCES department(dnumber)
);
