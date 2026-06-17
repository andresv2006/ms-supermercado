create table pago (
    id bigint not null auto_increment,
    pedido_id bigint not null,
    metodo_pago varchar(50) not null,
    monto decimal(12,2) not null,
    estado varchar(30) not null,
    primary key (id)
);

insert into pago (pedido_id, metodo_pago, monto, estado)
values (1, 'TARJETA', 3500, 'APROBADO');

insert into pago (pedido_id, metodo_pago, monto, estado)
values (2, 'EFECTIVO', 2300, 'PENDIENTE');
