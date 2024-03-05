CREATE TABLE history
(
    id       serial primary key,
    startAt  timestamp,
    endAt    timestamp,
    owner_id int not null references owner(id),
    car_id   int not null references car(id)
);