
INSERT INTO Department (id,department_name, read_only, mandatory) VALUES
(100,'Organisation', true, true),
(101,'IT', false, false),
(102,'Sales', false, false),
(103,'HR', false, false);

INSERT INTO Employee (id,employee_name) VALUES
(100,'Ram'),
(101,'Sham'),
(102,'Sita'),
(103,'Gita');

INSERT INTO map_employee_department (employee_id, department_id) VALUES
(100,100),
(101,100),
(102,100),
(103,100)