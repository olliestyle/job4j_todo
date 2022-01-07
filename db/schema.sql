create table tasks
(
    id serial primary key,
    description varchar not null,
    created timestamp,
    done boolean
)