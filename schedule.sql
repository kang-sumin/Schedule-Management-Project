create table schedule(
                         id int not null auto_increment,
                         todo varchar(100) not null,
                         charge varchar(20) not null,
                         password varchar(10) not null,
                         createDate datetime not null,
                         updateDate datetime not null,
                         primary key (id)
);