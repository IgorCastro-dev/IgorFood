CREATE TABLE restaurante_usuario_responsavel(
	restaurante_id bigint,
	usuario_id bigint,
	primary key(restaurante_id,usuario_id)
)engine=InnoDB default charset=utf8;