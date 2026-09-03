drop table if exists reservation;
drop table if exists time;

create table time
(
    id bigint not null auto_increment,
    time time not null unique,
    primary key (id)
);

create table reservation
(
    id bigint not null auto_increment,
    name varchar(255) not null,
    date date not null,
    time_id bigint not null,
    primary key (id),
    foreign key (time_id) references time(id),
    constraint uk_user_reserve_time unique (date, time_id)
);