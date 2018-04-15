user=$1
if [ -z $user ];
then
  user = 'root'
fi
host=$2
if [ -z $host ];
then
  host = 'localhost'
fi
port=$3
if [ -z $port ];
then
  port = '3306'
fi

mysql -u$user -h$host -P$port <<<_INIT_SQL<<
  create table if not exists userdb.xiaoqu (id bigint(12) primary key AUTO_INCREMENT,country varchar(8) default 'cn', province varchar(10) not null, city varchar(10) not null, area varchar(10) not null, xiaoquName varchar(30) not null, locat_min_x double not null, locat_max_x double not null, locat_min_y double not null,
  locat_max_y double not null, index locat_idx (country, province, city, area, xiaoquName)) engine=InnoDB default charset utf8 collate utf8_general_ci;

  create table if not exists userdb.user (wxNo varchar(20) primary key not null, xiaoqu bigint(12), phoneNum bigint(16) default null, carNo varchar(10) not null, index carNo(carNo), CONSTRAINT fk_xiaoqu FOREIGN KEY (xiaoqu)
  REFERENCES userdb.xiaoqu(id)) engine=InnoDB default charset utf8 collate utf8_general_ci;

  
_INIT_SQL