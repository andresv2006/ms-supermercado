create table empleado (
    id bigint not null auto_increment,
    rut varchar(20) not null unique,
    nombre varchar(100) not null,
    apellido varchar(100) not null,
    correo varchar(150) not null unique,
    telefono varchar(30) not null,
    cargo varchar(100) not null,
    turno varchar(50) not null,
    sueldo decimal(12,2) not null,
    activo bit not null,
    primary key (id)
);

insert into empleado (rut, nombre, apellido, correo, telefono, cargo, turno, sueldo, activo)
values ('11111111-1', 'Camila', 'Rojas', 'camila.rojas@supermercado.cl', '912345678', 'Cajera', 'Manana', 520000, 1);

insert into empleado (rut, nombre, apellido, correo, telefono, cargo, turno, sueldo, activo)
values ('22222222-2', 'Diego', 'Perez', 'diego.perez@supermercado.cl', '923456789', 'Reponedor', 'Tarde', 500000, 1);

insert into empleado (rut, nombre, apellido, correo, telefono, cargo, turno, sueldo, activo)
values ('33333333-3', 'Valentina', 'Mendez', 'valentina.mendez@supermercado.cl', '934567890', 'Supervisor', 'Completo', 750000, 1);
