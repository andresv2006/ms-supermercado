create table inventario (
    id bigint not null auto_increment,
    producto_id bigint not null,
    cantidad integer not null,
    stock_minimo integer not null,
    ubicacion varchar(100) not null,
    primary key (id)
);

insert into inventario (producto_id, cantidad, stock_minimo, ubicacion)
values (1, 100, 10, 'Pasillo 1');

insert into inventario (producto_id, cantidad, stock_minimo, ubicacion)
values (2, 80, 10, 'Pasillo 2');
