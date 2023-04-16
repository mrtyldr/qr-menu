create table restaurant
(
    id             uuid primary key,
    name           varchar(50) not null,
    password       text        not null,
    photo_url_link text,
    email          text        not null,
    created_at     timestamp with time zone,
    updated_at     timestamp with time zone
);

create table tables
(
    id            uuid primary key,
    restaurant_id uuid         not null,
    created_at    timestamp with time zone,
    updated_at    timestamp with time zone,
    name          varchar(250) not null,
    foreign key (restaurant_id) references restaurant (id)
);

create index rest_id on tables (restaurant_id);

create table item
(
    id             uuid primary key,
    created_at     timestamp with time zone,
    updated_at     timestamp with time zone,
    name           varchar(50)   not null,
    restaurant_id  uuid          not null,
    description    text,
    price          numeric(9, 2) not null,
    category_id    uuid          not null,
    photo_link_url text
);
create index rest_id_item on item (restaurant_id);
create index cate_id on item (category_id);

create table orders
(
    id            uuid primary key,
    created_at    timestamp with time zone,
    updated_at    timestamp with time zone,
    restaurant_id uuid          not null,
    item_ids      uuid[]        not null,
    note          text,
    total         numeric(9, 2) not null,
    table_id      uuid          not null
);
create index rest_id_order on orders(restaurant_id);
create index item_id_order on orders(item_ids);
create table notification
(
    id            uuid primary key,
    created_at    timestamp with time zone,
    updated_at    timestamp with time zone,
    restaurant_id uuid not null,
    type          varchar(50),
    table_id      uuid not null
);
create index rest_id_notification on notification(restaurant_id);
create table category
(
    id         uuid primary key,
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
    name       varchar(50) not null
);