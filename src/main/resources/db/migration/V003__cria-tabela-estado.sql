create table estado(
    id bigint not null auto_increment,
    nome varchar(80) not null,
    primary key (id)
) engine = InnoDB default charset=utf8;

INSERT INTO estado(nome) SELECT DISTINCT nome_estado from cidade;

ALTER table cidade add column estado_id bigint not null;

UPDATE cidade c set c.estado_id = (SELECT e.id from estado e where c.nome_estado = e.nome);

ALTER table cidade add constraint fk_cidade_estado
foreign key (estado_id) references estado(id);

ALTER table cidade drop column nome_estado;

ALTER table cidade change nome_cidade nome varchar(80) not null;