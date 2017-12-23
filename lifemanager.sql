create database lifemanager;
use lifemanager;
#drop database lifemanager;

create table users(
id int primary key auto_increment,
name varchar(20) not null,
surname varchar(50) not null,
login varchar(20) not null,
pass varchar(20) not null,
access enum('0', '1') not null
);


create table tasks(
id int primary key auto_increment,
user_id int not null,
name varchar(50) not null,
description varchar(100),
category varchar(20) not null,
date_start date,
duration int not null,
time_start time,
time_end time
);

insert into users (name, surname, login, pass, access) values ('Anna', 'Pyzel', 'pyzel', 'pyzel', '0');
insert into users (name, surname, login, pass, access) values ('User', 'User', 'u', 'u', '0');

insert into tasks (user_id, name, description, category, date_start, duration) values (1, 'project', 'project description', 'work', '2017-12-16', 100);
insert into tasks (user_id, name, description, category, date_start, duration) values (1, 'project2', 'project2 descrtasksiption', 'work', curdate() ,100);

insert into tasks (user_id, name, description, category, date_start, duration, time_start, time_end) values (1, 'project', 'project description', 'work', '2017-12-16', 100, curtime(), curtime());
