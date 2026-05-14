create table devolucion (
    id bigint not null auto_increment,
    pedido_id bigint not null,
    pago_id bigint not null,
    motivo varchar(255) not null,
    estado varchar(30) not null,
    primary key (id)
);
