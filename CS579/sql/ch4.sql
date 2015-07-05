SET LINESIZE 100
SET PAGESIZE 200
SET ECHO ON


ALTER SESSION SET nls_date_format='YYYY-MM-DD';

-- Q0
SELECT bdate, address
  FROM employee
 WHERE fname='John'
   AND minit='B'
   AND lname='Smith';

-- Q1
SELECT fname, lname, address
  FROM employee, department
 WHERE dname='Research'
   AND dnumber=dno;

-- Q2
SELECT pnumber, dnum, lname, address, bdate
  FROM project, department, employee
 WHERE dnum=dnumber
   AND mgrssn=ssn
   AND plocation='Stafford';

-- Q8
SELECT e.fname, e.lname, s.fname, s.lname
  FROM employee e, employee s
 WHERE e.superssn=s.ssn;

-- Q9
SELECT ssn
FROM employee;

-- Q10
SELECT ssn, dname
  FROM employee, department;

-- Q1C
SELECT *
  FROM employee
 WHERE dno=5;

--Q1D
SELECT *
  FROM employee, department
 WHERE dname='Research'
   AND dno=dnumber;

-- Q10A
SELECT *
  FROM employee, department;

-- Q11
SELECT ALL salary
  FROM employee;

-- Q11A
SELECT DISTINCT salary
  FROM employee;

-- Q4A
SELECT DISTINCT pnumber
  FROM project, department, employee
 WHERE dnum=dnumber
   AND mgrssn=ssn
   AND lname='Smith'
 UNION
SELECT DISTINCT pnumber
  FROM project, works_on, employee
 WHERE pnumber=pno
   AND essn=ssn
   AND lname='Smith';

-- Q12
SELECT fname, lname
  FROM employee
 WHERE address LIKE '%Houston, TX%';

-- Q12A
SELECT fname, lname
  FROM employee
 WHERE bdate LIKE '__5_______';

-- Q13
SELECT e.fname, e.lname, 1.1 * e.salary AS increased_sal
  FROM employee e, works_on w, project p
 WHERE e.ssn=w.essn
   AND w.pno=p.pnumber
   AND p.pname='ProductX';

-- Q14
SELECT *
  FROM employee
 WHERE salary BETWEEN 30000 AND 40000
   AND dno=5;

-- Q15
SELECT d.dname, e.lname, e.fname, p.pname
  FROM department d, employee e, works_on w, project p
 WHERE d.dnumber=e.dno
   AND e.ssn=w.essn
   AND w.pno=p.pnumber
 ORDER BY d.dname DESC, e.lname ASC, e.fname ASC;

-- U1
INSERT INTO employee
VALUES ('Richard', 'K', 'Marini', '653298653', '1962-12-30',
        '98 Oak Forest, Katy, TX', 'M', 37000, '653298653', 4);

-- Expected to fail with primary key violation
-- U1A
INSERT INTO employee (fname, lname, dno, ssn)
VALUES ('Richard', 'Marini', 4, '653298653');

-- Expected to fail with foreign key violation
-- U3
INSERT INTO employee (fname, lname, ssn, dno)
VALUES ('Robert', 'Hatcher', '980760540', 2);

-- Expected to fail with NOT NULL violation
-- U2A
INSERT INTO employee (fname, lname, dno)
VALUES ('Robert', 'Hatcher', 5);

ROLLBACK;

-- U3A
CREATE TABLE works_on_info (
       emp_name       VARCHAR2(15),
       proj_name      VARCHAR2(16),
       hours_per_week NUMBER(3,1)
);

-- U3B
INSERT INTO works_on_info (emp_name, proj_name, hours_per_week) (
       SELECT e.lname, p.pname, w.hours
         FROM project p, works_on w, employee e
        WHERE p.pnumber=w.pno
          AND w.essn=e.ssn
);

-- U4A
DELETE
  FROM employee
 WHERE lname='Brown';

-- U4B
DELETE
  FROM employee
 WHERE ssn='123456789';

-- U4C
DELETE
  FROM employee
 WHERE dno=5;

-- U4D
DELETE
  FROM employee;

UPDATE project
   SET plocation='Bellaire', dnum=5
 WHERE pnumber=10;

UPDATE employee
   SET salary=salary * 1.1
 WHERE dno=5;

ROLLBACK;

DROP TABLE works_on_info;
