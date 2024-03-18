create table car
(
    id serial primary key,
    year int not null,
    mileage int not null,
    name  text  not null,
    color  text  not null,
    body  text  not null,
    transmission  text  not null,
    drive  text  not null,
    engine_id int not null unique references engine(id),
    owner_id int not null unique references owner(id)
);