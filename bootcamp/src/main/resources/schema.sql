
create table Employee(
id INTEGER PRIMARY KEY AUTO_INCREMENT,
employee_name VARCHAR(255)
);

create table Department(
id INTEGER PRIMARY KEY AUTO_INCREMENT,
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

