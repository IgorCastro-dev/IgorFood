insert into cozinha(nome) values ('Tailandesa');
insert into cozinha(nome) values ('Indiana');

insert into restaurante(nome, taxa_frete, cozinha_id) values ('Thai Gourmet',10,1);
insert into restaurante(nome, taxa_frete, cozinha_id) values ('Thai Delivery',9.50,1);
insert into restaurante(nome, taxa_frete, cozinha_id) values ('Tuk Tuk Comida Indiana',15,2);

insert into estado(nome) values ('RJ');

insert into cidade(nome,estado_id) values ('Niterói',1);
insert into cidade(nome,estado_id) values ('São gonçalo',1);
insert into cidade(nome,estado_id) values ('copacabana',1);

insert into produto(nome,descricao,preco,ativo,restaurante_id) values ('sopa','agua morna',10.56,true,1);
insert into produto(nome,descricao,preco,ativo,restaurante_id) values ('salada','verduras',20.90,true,1);
insert into produto(nome,descricao,preco,ativo,restaurante_id) values ('sopa','agua morna',10.56,true,2);