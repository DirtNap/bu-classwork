DROP TABLE project;
CREATE TABLE project (
  pname      varchar2(25) not null,
  pnumber    number(4,0),
  plocation  varchar2(15),
  dnum       number(4,0) not null,
  CONSTRAINT pk_project PRIMARY KEY (pnumber),
  CONSTRAINT uq_project_pname UNIQUE (pname),
  CONSTRAINT fk_project_department FOREIGN KEY (dnum) references department(dnumber)
);
