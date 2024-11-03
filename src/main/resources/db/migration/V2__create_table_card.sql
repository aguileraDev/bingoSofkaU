create table card(
    id serial primary key,
    player_id text not null,
    game_id int not null references game (id) on delete cascade on update cascade,
    numbers integer[] not null
);

