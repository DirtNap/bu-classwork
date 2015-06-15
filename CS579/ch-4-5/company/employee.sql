DROP TABLE employee;
CREATE TABLE employee (
  fname    varchar(15) not null, 
  minit    varchar(1),
  lname    varchar(15) not null,
  ssn      char(9),
  bdate    date,
  address  varchar(50),
  sex      char,
  salary   decimal(10,2),
  superssn char(9),
  dno      integer(4),
  primary key (ssn),
  foreign key (superssn) references employee(ssn),
  foreign key (dno) references department(dnumber)
);
