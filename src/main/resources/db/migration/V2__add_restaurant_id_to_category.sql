alter table category
    add column restaurant_id uuid;
create index rest_id_category on category (restaurant_id);