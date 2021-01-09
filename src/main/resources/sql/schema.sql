drop table if exists `register`;
drop table if exists `test`;
drop table if exists `bed`;
drop table if exists `patient`;
drop table if exists `staff`;
CREATE TABLE IF NOT EXISTS `staff`(
    `id` int  not null primary key AUTO_INCREMENT,
    `name` varchar(15),
    age int ,
    gender smallint ,
    job smallint ,
    treatArea smallint
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS `patient`(
    `id` int not null primary key AUTO_INCREMENT,
    `name` varchar(15),
    gender smallint ,
    age int,
    treatArea smallint default 0,
    nurseID int default 0,
    grade smallint ,
    waitTransfer smallint default 1,
    lifeCondition smallint default 1,
    arriveDate varchar(25),
    tempCount smallint default 0,
    testCount smallint default 0
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS `register`(
    patientID int,
    registerDate varchar(20),
    lifeCondition smallint ,
    temperature double ,
    symptom varchar(100) default null,
    primary key  (patientID,registerDate),
    constraint fk_emp_paitentid foreign key (patientID) references patient(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `test`(
    patientID int,
    testDate varchar (20),
    testResult smallint ,
    grade smallint ,
    primary key  (patientID,testDate),
    constraint fk_test_patientid foreign key (patientID) references patient(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS `bed`(
    `id` int not null primary key AUTO_INCREMENT ,
    patientID int default 0 ,
    roomID int ,
    treatArea smallint
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



