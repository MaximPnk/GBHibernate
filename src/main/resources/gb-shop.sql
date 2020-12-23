-- Перед началом необходимо создать БД и схему, выставить search_path
-- Скрипт написан под PostgreSQL

DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
	id bigserial NOT NULL,
	"name" text NOT NULL,
	email varchar(64) NOT NULL,
	CONSTRAINT customer_pk PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "order";

CREATE TABLE "order" (
	id bigserial NOT NULL,
	customer_id bigserial NOT NULL,
	"date" timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
	payed bool NOT NULL DEFAULT false,
	CONSTRAINT order_pk PRIMARY KEY (id),
	CONSTRAINT order_fk FOREIGN KEY (customer_id) REFERENCES customer(id)
);

DROP TABLE IF EXISTS product;

CREATE TABLE product (
	id serial NOT NULL,
	"name" text NOT NULL,
	price float8 NOT NULL,
	count int4 NOT NULL,
	CONSTRAINT product_pk PRIMARY KEY (id)
);

DROP TABLE IF EXISTS order_item;

CREATE TABLE order_item (
	id bigserial NOT NULL,
	order_id bigserial NOT NULL,
	product_id serial NOT NULL,
	count int4 NOT NULL,
	price float8 NOT NULL,
	CONSTRAINT order_item_pk PRIMARY KEY (id),
	CONSTRAINT order_item_fk FOREIGN KEY (order_id) REFERENCES "order"(id),
	CONSTRAINT order_item_fk_1 FOREIGN KEY (product_id) REFERENCES product(id)
);


insert into customer (name, email) values ('Ivan Nikitin', 'i.nikitin@mail.ru');
insert into customer (name, email) values ('Iraida Vlasova', 'i.vlasova@mail.ru');

insert into product (name, price, count) values ('Молоко', 70, 743);
insert into product (name, price, count) values ('Яблоки', 70, 149);
insert into product (name, price, count) values ('Сыр', 850, 438);
insert into product (name, price, count) values ('Колбаса', 1340, 234);
insert into product (name, price, count) values ('Яйца', 100, 859);

insert into "order" (customer_id) values (1);

