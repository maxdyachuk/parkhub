
/*-----------------------------------------------------*/

	/*!!!!!!!!!!Second transactive!!!!!!!!*/

CREATE SCHEMA park_hub;

/*-----------------------------------------------------*/
CREATE TABLE IF NOT EXISTS "park_hub".customer (

	id   BIGSERIAL   NOT NULL   PRIMARY KEY,
	role_name   VARCHAR(50)   NOT NULL,
	is_active boolean NOT NULL DEFAULT 'true'

);

CREATE TABLE IF NOT EXISTS "park_hub".user_role (

	id   SERIAL   NOT NULL   PRIMARY KEY,
	role_name   VARCHAR(50)   NOT NULL,
	is_active boolean NOT NULL DEFAULT 'true'

);


/*-----------------------------------------------------*/

CREATE TABLE IF NOT EXISTS "park_hub".user (

	id BIGSERIAL PRIMARY KEY NOT NULL,
    customer_id INT NOT NULL REFERENCES park_hub.customer(id),
    first_name VARCHAR (255) NOT NULL,
	second_name VARCHAR (255) NOT NULL,
	email VARCHAR (255) UNIQUE NOT NULL,
	password VARCHAR (50) NOT NULL,
	role_id INT NOT NULL REFERENCES park_hub.user_role(id),
	link_photo text,
	verification_link VARCHAR(255) UNIQUE NOT NULL,
	created_on TIMESTAMP with time zone 
		NOT NULL DEFAULT current_timestamp,
	last_login TIMESTAMP with time zone 
		NOT NULL DEFAULT current_timestamp,
	is_active boolean NOT NULL DEFAULT 'true'

);

CREATE TABLE IF NOT EXISTS "park_hub".support_ticket(

id BIGSERIAL PRIMARY KEY NOT NULL,
author_id INT NOT NULL REFERENCES park_hub.customer(id),
ticket_solver INT NULL REFERENCES park_hub.user(id),
description text NOT NULL,
is_solved boolean NOT NULL DEFAULT 'false'
);

CREATE TABLE IF NOT EXISTS "park_hub".address(

	id BIGSERIAL PRIMARY KEY NOT NULL,
    city VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    building VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS "park_hub".parking(

id BIGSERIAL PRIMARY KEY NOT NULL,
manager_id INT NOT NULL REFERENCES park_hub.user(id),
parking_name VARCHAR(255) NOT NULL,
address_id INT NOT NULL REFERENCES park_hub.address(id),
slots_number INT NOT NULL,
tariff money,
is_active boolean NOT NULL DEFAULT 'true'
);

CREATE TABLE IF NOT EXISTS "park_hub".slot(

id BIGSERIAL PRIMARY KEY NOT NULL,
parking_id INT NOT NULL REFERENCES park_hub.parking(id),
slot_number VARCHAR NOT NULL,
is_reserved boolean NOT NULL DEFAULT 'false',
is_active boolean NOT NULL DEFAULT 'true'
);

CREATE TABLE IF NOT EXISTS "park_hub".booking(
id BIGSERIAL PRIMARY KEY NOT NULL,
customer_id INT NOT NULL REFERENCES park_hub.customer(id),
car_number VARCHAR(8) NOT NULL,
slot_id INT NOT NULL REFERENCES park_hub.slot(id),
chek_in TIMESTAMP with time zone
		NOT NULL DEFAULT current_timestamp,
check_out TIMESTAMP with time zone,
is_active boolean NOT NULL DEFAULT 'true'
        );

CREATE TABLE IF NOT EXISTS "park_hub".payment(
id BIGSERIAL PRIMARY KEY NOT NULL,
booking_id INT NOT NULL REFERENCES park_hub.booking(id),
price money,
is_paid boolean NOT NULL DEFAULT 'false'
        );