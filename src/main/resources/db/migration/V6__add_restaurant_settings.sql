create table restaurant_settings(
    restaurant_id uuid primary key,
    first_url text,
    second_url text,
    photo_url text,
    foreign key (restaurant_id) references restaurant(id)
);