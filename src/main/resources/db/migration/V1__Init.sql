create schema if not EXISTS security_schema;

create table if not EXISTS security_schema.t_users (
                                        id serial primary key,
                                        username varchar not null check ( length(trim(username)) > 0 ) unique ,
                                        email varchar not null check ( length(trim(username)) > 0 ) unique ,
                                        password varchar
);



