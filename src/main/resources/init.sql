create table employees
(
  id uuid not null primary key,
  name varchar not null,
  role varchar not null
);


insert into employees
(id, name, role)
values
('6fa059fa-44ca-4fa6-9401-f550792eb040', 'Tom', 'CEO'),
('6fa059fa-44ca-4fa6-9401-f550792eb042', 'Jerry', 'Sr. Software Engineer'),
('6fa059fa-44ca-4fa6-9401-f550792eb043', 'Tim Cook', 'CEO'),
('6fa059fa-44ca-4fa6-9401-f550792eb044', 'Tata', 'Manager'),
('6fa059fa-44ca-4fa6-9401-f550792eb044', 'Mahindra', 'HR');