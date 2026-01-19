create table Ingredient (
    costPerPurchaseUnit float(53) not null,
    systemStock float(53) not null,
    id uuid not null,
    name varchar(255) not null,
	purchaseUnit enum ('G','KG','L','MG','ML','PIECE','TON') not null,
	usageUnit enum ('G','KG','L','MG','ML','PIECE','TON') not null,
	primary key (id)
);
