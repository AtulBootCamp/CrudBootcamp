
CREATE SEQUENCE employee_sequence START WITH 1 increment by 1;

create table Employee(
id INTEGER DEFAULT next value for employee_sequence PRIMARY KEY,
employee_name VARCHAR(255)
);

CREATE SEQUENCE department_sequence START WITH 1 increment by 1;

create table Department(
id INTEGER DEFAULT next value for department_sequence PRIMARY KEY,
department_name VARCHAR(255),
read_only BOOLEAN,
mandatory BOOLEAN
);

create table Map_employee_department(
employee_id INTEGER,
department_id INTEGER,
FOREIGN KEY (employee_id) REFERENCES Employee(id),
FOREIGN KEY (department_id) REFERENCES Department(id),
PRIMARY KEY (employee_id, department_id)
);

