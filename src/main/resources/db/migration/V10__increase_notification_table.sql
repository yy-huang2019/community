create table notification
(
	id INTEGER not null,
	notifier INTEGER not null,
	receiver INTEGER not null,
	outer_id INTEGER not null,
	type int not null,
	gmt_create bigint not null,
	status int default 0 not null,
	constraint notification_pk
		primary key (id)
);