user=$1
host=$2
port=$3

mysql -u$user -h$host -P$port <<<_INIT_SQL<<
  create table if not exists userdb.user (wxNo varchar(20) primary key not null, xiaoqu varchar(20) default null, phoneNum bigint(16) default null, carNo varchar(10) not null, index carNo(carNo)) engine=InnoDB default charset utf8 collate utf8_general_ci;

  
_INIT_SQL