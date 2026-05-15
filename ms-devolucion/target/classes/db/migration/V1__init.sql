create table devolucion (
    id bigint not null auto_increment,
    pedido_id bigint not null,
    pago_id bigint not null,
    motivo varchar(255) not null,
    estado varchar(30) not null,
    primary key (id)
);

insert into devolucion (pedido_id, pago_id, motivo, estado)
values (1, 1, 'Producto danado', 'SOLICITADA');

insert into devolucion (pedido_id, pago_id, motivo, estado)
values (2, 2, 'Cambio solicitado por cliente', 'APROBADA');
