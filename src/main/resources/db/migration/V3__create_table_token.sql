create table token (
  id serial primary key,
  token_called integer not null,
  game_id integer not null references game(id),
  call_time timestamp default current_timestamp
);

