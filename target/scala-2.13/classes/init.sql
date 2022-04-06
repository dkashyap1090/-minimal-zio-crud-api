create table employees
(
  id uuid not null primary key,
  name varchar not null,
  role varchar not null
);


insert into employees
(id, name, role)
values
('60b01fc9-c902-4468-8d49-3c0f989def37', 'Tom', 'CEO'),
('f76c9ace-be07-4bf3-bd4c-4a9c62882e64', 'Jerry', 'Sr. Software Engineer'),
('784426a5-b90a-4759-afbb-571b7a0ba35e', 'Tim Cook', 'CEO'),
('df8215a2-d5fd-4c6c-9984-801a1b3a2a0b', 'Tata', 'Manager'),
('636ae137-5b1a-4c8c-b11f-c47c624d9cdc', 'Mahindra', 'HR');