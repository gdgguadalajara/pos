create table ingredient_recipe (
    ingredient_id uuid not null,
    recipe_id uuid not null,
    primary key (ingredient_id, recipe_id)
);

alter table if exists ingredient_recipe 
    add constraint FKpurx8sjjad6xrqkq4vxbtvkjd 
    foreign key (ingredient_id) 
    references Ingredient;

alter table if exists ingredient_recipe 
    add constraint FK7qdy84maqjt0r5ir0y39dyylw 
    foreign key (recipe_id) 
    references Recipe;