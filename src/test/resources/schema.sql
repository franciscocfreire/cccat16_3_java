drop schema if exists cccat16 cascade;

create schema cccat16;

create table cccat16.account (
	account_id uuid primary key,
	name text not null,
	email text not null,
	cpf text not null,
	car_plate text null,
	is_passenger boolean not null default false,
	is_driver boolean not null default false
);

create table cccat16.ride (
    ride_id uuid,
    passenger_id uuid,
    driver_id uuid,
    status text,
    fare numeric,
    distance numeric,
    from_lat numeric(20, 15),
    from_long numeric(20, 15),
    to_lat numeric(20, 15),
    to_long numeric(20, 15),
    date timestamp
);
