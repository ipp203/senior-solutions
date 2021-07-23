CREATE TABLE if NOT exists location (id bigint auto_increment,
    name varchar(255),
    lat double,
    lon double,
    primary key(id));