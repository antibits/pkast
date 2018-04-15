create table if not exists userdb.xiaoqu (id varchar(64) primary key, xiaoquName varchar(30) not null, locat_x_min double not null, locat_x_max double not null, locat_y_min double not null,
locat_y_max double not null) engine=InnoDB default charset utf8 collate utf8_general_ci;

create table if not exists userdb.user (wxNo varchar(20) primary key not null, xiaoqu varchar(64) not null, phoneNum bigint(16) default null, carNo varchar(10) not null, index carNo(carNo), CONSTRAINT fk_xiaoqu FOREIGN KEY (xiaoqu)
REFERENCES userdb.xiaoqu(id)) engine=InnoDB default charset utf8 collate utf8_general_ci;


