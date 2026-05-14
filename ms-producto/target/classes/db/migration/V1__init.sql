create table producto (
    id bigint not null auto_increment,
    sku varchar(50) not null unique,
    nombre varchar(120) not null,
    descripcion varchar(255) not null,
    precio decimal(12,2) not null,
    categoria_id bigint not null,
    activo bit not null,
    primary key (id)
);

insert into producto (sku, nombre, descripcion, precio, categoria_id, activo)
values ('BEB-001', 'Agua mineral', 'Agua sin gas 1.5L', 1200, 1, 1);

insert into producto (sku, nombre, descripcion, precio, categoria_id, activo)
values ('LAC-001', 'Leche entera', 'Leche entera 1L', 1100, 2, 1);
