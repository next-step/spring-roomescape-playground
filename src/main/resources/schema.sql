drop table if exists reservation;

create table reservation
(
    id bigint not null auto_increment,
    name varchar(255) not null,
    date varchar(255) not null,
    time varchar(255) not null,
    primary key (id)
);