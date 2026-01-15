create table Ingredient (
	costPerPurchaseUnit float(53) not null,
	systemStock float(53) not null,
	id uuid not null,
	recipe_id uuid,
	name varchar(255) not null,
	purchaseUnit enum ('G','KG','L','MG','ML','PIECE','TON') not null,
	usageUnit enum ('G','KG','L','MG','ML','PIECE','TON') not null,
	primary key (id)
);

alter table if exists Ingredient 
	add constraint FKe6shif331ud2cn5gppjcd7rjv 
	foreign key (recipe_id) 
	references Recipe;
