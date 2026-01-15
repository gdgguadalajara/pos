create table Recipe (
	createdAt timestamp(6) not null,
	updatedAt timestamp(6),
	id uuid not null,
	product_id uuid unique,
	description varchar(255) not null,
	name varchar(255) not null,
	primary key (id)
);

alter table if exists Recipe 
	add constraint FKrc3np5f00u0wu10sdmw5vcc66 
	foreign key (product_id) 
	references Product;