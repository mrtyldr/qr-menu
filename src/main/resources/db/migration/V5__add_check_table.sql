create table checks
(
    id         uuid primary key,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    total      numeric(9, 2),
    table_id   uuid                     not null,
    status     varchar(50)
);
create table check_items
(
    id uuid primary key,
    check_id uuid not null ,
    item_id  uuid not null,
    quantity int  not null,
    foreign key (check_id) references checks (id)
);