drop table if exists `staff`;
CREATE TABLE IF NOT EXISTS `staff`(
    id int default 1,
    `name` varchar(15),
    age int ,
    gender smallint ,
    job smallint ,
    treatArea smallint ,
    primary key identity (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
drop table if exists `patient`;
CREATE TABLE IF NOT EXISTS `patient`(
    id int default 1,
    `name` varchar(15),
    gender smallint ,
    age int,
    treateArea smallint default 0,
    nurseID int,
    grade smallint ,
    waitTransfer smallint ,
    lifeCondition smallint ,
    arriveDate varchar(25),
    tempCount smallint default 0,
    testCount smallint default 0,
    primary key identity (`id`),
    constraint fk_emp_nurseid foreign key (nurseID) references staff(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
drop table if exists `register`;
CREATE TABLE IF NOT EXISTS `register`(
    patientID int,
    registerDate varchar(20),
    lifeCondition smallint ,
    temperature double ,
    symptom varchar(100) default null,
    primary key identity (patientID,registerDate),
    constraint fk_emp_nurseid foreign key (patientID) references patient(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
drop table if exists `test`;
CREATE TABLE IF NOT EXISTS `test`(
    patientID int,
    testDate varchar (20),
    testResult smallint ,
    grade smallint ,
    primary key identity (patientID,testDate),
    constraint fk_emp_nurseid foreign key (patientID) references patient(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
drop table if exists `bed`;
CREATE TABLE IF NOT EXISTS `bed`(
    id int identity default 1,
    patientID int default 0 ,
    roomID int ,
    treatArea smallint ,
    primary key identity (id),
    constraint fk_emp_nurseid foreign key (patientID) references patient(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

