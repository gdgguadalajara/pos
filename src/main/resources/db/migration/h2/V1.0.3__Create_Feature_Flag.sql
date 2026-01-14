create table FeatureFlag (
    id uuid not null,
    createdAt timestamp(6) not null,
    updatedAt timestamp(6),
    description varchar(255),
    enabled boolean not null,
    name varchar(255) not null unique,
    primary key (id)
);
