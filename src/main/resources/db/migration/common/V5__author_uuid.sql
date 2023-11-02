create table author_uuid
(
    id         uuid not null,
    first_name varchar(255),
    last_name  varchar(255),
    primary key (id)
) engine = InnoDB;