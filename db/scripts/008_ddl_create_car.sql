create table car(
    id serial primary key,
    name  text  not null,
    engine_id int not null unique references engine(id),
    owner_id int not null unique references owner(id)
);