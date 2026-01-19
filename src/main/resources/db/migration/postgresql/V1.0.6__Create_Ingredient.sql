create table Ingredient (
    costPerPurchaseUnit float(53) not null,
    systemStock float(53) not null,
    id uuid not null,
    name varchar(255) not null,
    purchaseUnit varchar(255) not null check ((purchaseUnit in ('TON','KG','G','MG','L','ML','PIECE'))),
    usageUnit varchar(255) not null check ((usageUnit in ('TON','KG','G','MG','L','ML','PIECE'))),
    primary key (id)
);
