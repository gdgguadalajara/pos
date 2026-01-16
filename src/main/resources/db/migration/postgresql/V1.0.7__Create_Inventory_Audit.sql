create table InventoryAudit (
	physicalStock float(53) not null,
	shrinkage float(53) not null,
	systemStockAtMoment float(53) not null,
	date timestamp(6) not null,
	id uuid not null,
	ingredientSnapshot json,
	userSnapshot json,
	primary key (id)
);
