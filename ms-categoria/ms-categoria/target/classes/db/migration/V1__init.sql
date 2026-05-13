create table categoria (id bigint not null auto_increment, nombre varchar(100) not null unique, descripcion varchar(255) not null, activo bit not null, primary key (id));
insert into categoria (nombre, descripcion, activo) values ('Bebidas', 'Bebidas y jugos', 1), ('Lacteos', 'Leche, queso y yogurt', 1), ('Limpieza', 'Productos de limpieza', 1);
