drop table if exists reservation;

create table reservation
(
    id bigint not null auto_increment,
    name varchar(255) not null,
    date date not null,
    time time not null,
    primary key (id)
);