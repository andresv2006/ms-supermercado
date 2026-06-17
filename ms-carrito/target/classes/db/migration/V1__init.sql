create table carrito (
    id bigint not null auto_increment,
    cliente_id bigint not null,
    estado varchar(30) not null,
    total decimal(12,2) not null,
    primary key (id)
);

create table carrito_item (
    id bigint not null auto_increment,
    carrito_id bigint,
    producto_id bigint not null,
    cantidad integer not null,
    precio_unitario decimal(12,2) not null,
    primary key (id),
    constraint fk_carrito_item_carrito foreign key (carrito_id) references carrito(id)
);
