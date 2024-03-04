create table history_owners(
    id serial primary key,
    owner_id int not null references owner(id),
    car_id int not null references car(id)
);