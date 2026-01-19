create table StockOutEvent (
    currentStock float(53) not null,
    quantity float(53) not null,
    date timestamp(6),
    id uuid not null,
    product_id uuid,
    ingredientSnapshot json not null,
    userSnapshot json not null,
    primary key (id)
);

alter table if exists StockOutEvent 
	add constraint FK4lji4oe04k1jg7kyl0h0leolh 
	foreign key (product_id) 
	references Product;
