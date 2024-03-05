create table photo(
    id serial primary key,
    name varchar not null,
    path varchar not null unique,
    auto_post_id int references auto_post(id)
);