alter table NOTIFICATION alter column ID INTEGER auto_increment;
alter table NOTIFICATION
	add notifier_name varchar(100) not null;

alter table NOTIFICATION
	add outer_title varchar(256) not null;