
DROP TABLE IF EXISTS tenmo_user,account,transfer_type,transfer_status,transfers;
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
	,status VARCHAR(8) NOT NULL
    ,CONSTRAINT PK_status_id PRIMARY KEY (status_id)	
	,CHECK (status IN ('Approved','Rejected','Pending'))
	);
	
	

	CREATE TABLE transfer(
	 transfer_id SERIAL
	,type_id INT NOT NULL
    ,status_id INT NOT NULL
	,deposit_id int NOT NULL
	,withdraw_id int NOT NULL
	,transfer_date_time TIMESTAMP NOT NULL
	,amount_transferred NUMERIC NOT NULL
	,CHECK (amount_transferred > 0)	
	,CONSTRAINT PK_transfer PRIMARY KEY (transfer_id)
	,CONSTRAINT FK_type_id FOREIGN KEY 	(type_id) REFERENCES transfer_type (type_id)
	,CONSTRAINT FK_status_id FOREIGN KEY (status_id) REFERENCES transfer_status (status_id)		
	,CONSTRAINT FK_deposit_account FOREIGN KEY (deposit_id) REFERENCES 	account(account_id)
	,CONSTRAINT FK_withdraw_account FOREIGN KEY (withdraw_id) REFERENCES account(account_id) 
	,CHECK (withdraw_id != deposit_id  )	
	
	);																		   
																			   
																			   
																			   
																			   
																			   
																			   
																			
	


