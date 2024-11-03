 create table winner(
  id serial primary key,
  player_id text not null,
  game_id int not null references game(id),
  finish_time timestamp default current_timestamp
 );
