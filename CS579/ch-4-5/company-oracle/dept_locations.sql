DROP TABLE dept_locations;
CREATE TABLE dept_locations (
  dnumber   number(4,0),
  dlocation varchar2(15), 
  CONSTRAINT pk_dept_locations PRIMARY KEY (dnumber, dlocation),
  CONSTRAINT fk_dept_locations_department FOREIGN KEY (dnumber) REFERENCES department(dnumber)
);
