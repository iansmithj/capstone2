
DROP TABLE IF EXISTS tenmo_user,account,transfer_type,transfer_status,transfer;
DROP SEQUENCE IF EXISTS seq_user_id;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

CREATE TABLE tenmo_user (
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) UNIQUE NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(20),
	CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
	);
	
	
	CREATE TABLE account(
	 account_id SERIAL
	,user_id int NOT NULL
	,balance DECIMAL NOT NULL

	,CONSTRAINT PK_account PRIMARY KEY (account_id)
    ,CONSTRAINT FK_user_id  FOREIGN KEY(user_id) REFERENCES tenmo_user(user_id)
	);
	
	
	CREATE TABLE transfer_type(
	 type_id SERIAL
	,type VARCHAR(12) NOT NULL
	,CONSTRAINT PK_type_id PRIMARY KEY (type_id)
	,CHECK (type IN ('SEND','REQUEST'))	
	);
	
	
	CREATE TABLE transfer_status(
	status_id SERIAL
	,status VARCHAR(10) NOT NULL
    ,CONSTRAINT PK_status_id PRIMARY KEY (status_id)	
	,CHECK (status IN ('Approved','Rejected','Pending'))
	);
	
	

	CREATE TABLE transfer(
	 transfer_id SERIAL
	,type_id INT NOT NULL CHECK (type_id IN (1, 2))
    ,status_id INT NOT NULL CHECK (status_id IN (1, 2, 3))
	,deposit_id INT NOT NULL
	,withdraw_id INT NOT NULL
	,transfer_date_time TIMESTAMP NOT NULL
	,amount_transferred DECIMAL NOT NULL
	,CHECK (amount_transferred > 0)	
	,CONSTRAINT PK_transfer PRIMARY KEY (transfer_id)
	,CONSTRAINT FK_type_id FOREIGN KEY 	(type_id) REFERENCES transfer_type (type_id)
	,CONSTRAINT FK_status_id FOREIGN KEY (status_id) REFERENCES transfer_status (status_id)		
	,CONSTRAINT FK_deposit_account FOREIGN KEY (deposit_id) REFERENCES 	account(account_id)
	,CONSTRAINT FK_withdraw_account FOREIGN KEY (withdraw_id) REFERENCES account(account_id) 
	,CHECK (withdraw_id != deposit_id )	
	
	);				
	-- Inserting into tenmo_user table
INSERT INTO tenmo_user (username, password_hash, role)
VALUES
    ('user1', 'hashed_password_1', 'user'),
    ('user2', 'hashed_password_2', 'user'),
    ('admin1', 'hashed_password_admin1', 'admin');
-- Inserting into account table
INSERT INTO account (user_id, balance)
VALUES
    (1001, 1000.00),
    (1002, 2500.50),
    (1003, 500.75);
-- Inserting into transfer_type table
INSERT INTO transfer_type (type)
VALUES
    ('SEND'),
    ('REQUEST');
-- Inserting into transfer_status table
INSERT INTO transfer_status (status)
VALUES
    ('Approved'),
    ('Rejected'),
     ('Pending');
	
-- Inserting into transfers table
INSERT INTO transfer (type_id, status_id, deposit_id, withdraw_id, transfer_date_time, amount_transferred)
VALUES
    (1, 3, 1, 2, '2024-07-02 10:00:00', 500.00),
    (2, 1, 2, 3, '2024-07-01 15:30:00', 100.50),
    (1, 2, 3, 1, '2024-07-03 08:45:00', 200.75),
	(2, 3, 1, 3, '2024-07-03 08:45:00', 23.75);																		   
																			   
																			   
																			   
																			   
																			   
																			
	


