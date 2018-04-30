create table if not exists userdb.xiaoqu (id varchar(64) primary key, xiaoquName varchar(30) not null, locat_x_min double not null, locat_x_max double not null, locat_y_min double not null,
locat_y_max double not null, xiaoqu_db_id bigint(10) not null auto_increment unique key) engine=InnoDB default charset utf8 collate utf8_general_ci;

create table if not exists userdb.user (wxNo varchar(20) primary key not null, xiaoqu varchar(64) not null, phoneNum bigint(16) default null, carNo varchar(10) not null, index carNo(carNo), CONSTRAINT fk_xiaoqu FOREIGN KEY (xiaoqu)
REFERENCES userdb.xiaoqu(id)) engine=InnoDB default charset utf8 collate utf8_general_ci;

use userdb;
drop procedure if exists init_db;
delimiter //
CREATE PROCEDURE init_db(in db_id varchar(16))
begin
  set @db_name = concat('db_', db_id);
  set @create_db_sql = CONCAT('create database if not exists ', @db_name);
  PREPARE excutable FROM @create_db_sql;
  EXECUTE excutable;
  DEALLOCATE PREPARE excutable;
  
  set @create_tbl_bbs_sql = concat('create table if not exists ', @db_name, '.bbs(id bigint(12) primary key auto_increment, type varchar(8) not null, creater varchar(20) not null, timestamp long not null, properties varchar(256) default null, index type(type)) engine=InnoDB default charset utf8 collate utf8_general_ci');
  PREPARE excutable FROM @create_tbl_bbs_sql;
  EXECUTE excutable;
  DEALLOCATE PREPARE excutable;  
end;//
delimiter ;

