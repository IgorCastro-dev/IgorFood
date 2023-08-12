ALTER Table pedido add column codigo varchar(36) not null after id;
update pedido set codigo = uuid();
ALTER TABLE pedido ADD CONSTRAINT uk_pedido_codigo unique (codigo);