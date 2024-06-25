DROP SCHEMA IF EXISTS cccat16 CASCADE;

CREATE SCHEMA cccat16;

CREATE TABLE cccat16.account (
	account_id UUID PRIMARY KEY,
	name TEXT NOT NULL,
	email TEXT NOT NULL,
	cpf TEXT NOT NULL,
	car_plate TEXT NULL,
	is_passenger BOOLEAN NOT NULL DEFAULT FALSE,
	is_driver BOOLEAN NOT NULL DEFAULT FALSE
);

create table cccat16.ride (
    ride_id uuid,
    passenger_id uuid,
    driver_id uuid,
    status text,
    fare numeric,
    distance numeric,
    from_lat numeric,
    from_long numeric,
    to_lat numeric,
    to_long numeric,
    date timestamp
);
