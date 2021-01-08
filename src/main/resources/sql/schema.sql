drop table if exists `staff`;
CREATE TABLE IF NOT EXISTS `staff`(
    id int default 1,
    `name` varchar(15),
    age int ,
    gender smallint ,
    job smallint ,
    primary key identity (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;