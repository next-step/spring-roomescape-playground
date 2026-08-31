drop table if exists reservation;
drop table if exists time;

create table reservation
(
    id bigint not null auto_increment,
    name varchar(255) not null,
    date date not null,
    time time not null,
    primary key (id)
);

create table time
(
    id bigint not null auto_increment,
    time time not null,
    primary key (id)
);