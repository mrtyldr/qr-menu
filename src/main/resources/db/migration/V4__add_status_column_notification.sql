alter table notification
    add column status varchar(50);
create index on notification (status);