create table contacts (
  lname  varchar(30),
  fname  varchar(30),
  email  varchar(30),
  homePhone varchar(20),
  cellPhone varchar(20),
  officePhone varchar(20),
  address varchar(100),
  comment varchar(100),
  primary key (lname,fname)
);
