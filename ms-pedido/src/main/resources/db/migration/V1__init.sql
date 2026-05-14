create table pedido (
    id bigint not null auto_increment,
    cliente_id bigint not null,
    promocion_id bigint,
    estado varchar(30) not null,
    total decimal(12,2) not null,
    primary key (id)
);

create table pedido_detalle (
    id bigint not null auto_increment,
    pedido_id bigint,
    producto_id bigint not null,
    cantidad integer not null,
    precio_unitario decimal(12,2) not null,
    primary key (id),
    constraint fk_pedido_detalle_pedido foreign key (pedido_id) references pedido(id)
);
