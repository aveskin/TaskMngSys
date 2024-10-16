create schema if not EXISTS task_management;

create table if not EXISTS task_management.t_users (
                                        id serial primary key,
                                        username varchar not null check ( length(trim(username)) > 0 ) unique ,
                                        email varchar not null check ( length(trim(username)) > 0 ) unique ,
                                        password varchar
);

create table if not EXISTS task_management.t_comments (
                                            id serial primary key,
                                            text varchar not null
);

create table if not EXISTS task_management.t_tasks (
                                            id serial primary key,
                                            header varchar not null check ( length(trim(header)) > 0 ),
                                            description varchar not null check ( length(trim(description)) > 0 ),
                                            status_id smallint not null,
                                            priority_id smallint not null,
                                            author_id int not null references task_management.t_user(id),
                                            executor_id int not null references task_management.t_user(id),
                                            comment_id int not null references task_management.t_comments(id)
);


