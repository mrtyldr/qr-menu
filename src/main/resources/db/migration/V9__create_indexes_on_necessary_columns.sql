create index on checks (table_id, status);
create index on check_items (item_id);
create index on orders (status);
create index on item (status);
create index on orders (table_id, status);
create index on notification(restaurant_id,status);