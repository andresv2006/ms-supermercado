create table cliente (
    id bigint not null auto_increment,
    rut varchar(20) not null unique,
    nombre varchar(100) not null,
    apellido varchar(100) not null,
    correo varchar(150) not null unique,
    telefono varchar(30) not null,
    direccion varchar(255) not null,
    activo bit not null,
    primary key (id)
);

insert into cliente (rut, nombre, apellido, correo, telefono, direccion, activo)
values ('44444444-4', 'Sofia', 'Lagos', 'sofia.lagos@mail.cl', '945678901', 'Av. Central 123', 1);

insert into cliente (rut, nombre, apellido, correo, telefono, direccion, activo)
values ('55555555-5', 'Matias', 'Fuentes', 'matias.fuentes@mail.cl', '956789012', 'Los Alerces 456', 1);
