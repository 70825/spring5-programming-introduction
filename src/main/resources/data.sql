create table MEMBER (
                                  ID int auto_increment primary key,
                                  EMAIL varchar(255),
                                  PASSWORD varchar(100),
                                  NAME varchar(100),
                                  REGDATE datetime,
                                  unique key (EMAIL)
) engine=InnoDB character set = utf8;
