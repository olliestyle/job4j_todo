create table users
(
    id serial primary key,
    name varchar(30) not null,
    password varchar(30) not null
);

create table tasks
(
    id serial primary key,
    user_id int references users(id),
    description varchar not null,
    created timestamp,
    done boolean
);