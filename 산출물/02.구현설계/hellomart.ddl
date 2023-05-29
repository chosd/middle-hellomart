DROP TABLE board CASCADE CONSTRAINTS;
DROP TABLE order_item CASCADE CONSTRAINTS;
DROP TABLE orders CASCADE CONSTRAINTS;
DROP TABLE address CASCADE CONSTRAINTS;
DROP TABLE cart CASCADE CONSTRAINTS;
DROP TABLE userinfo CASCADE CONSTRAINTS;
DROP TABLE product CASCADE CONSTRAINTS;
DROP TABLE categories CASCADE CONSTRAINTS;

CREATE TABLE categories(
		ct_no                         		NUMBER(10)		 NULL ,
		ct_name                       		VARCHAR2(50)		 NOT NULL
);


CREATE TABLE product(
		p_no                          		NUMBER(10)		 NULL ,
		p_name                        		VARCHAR2(50)		 NOT NULL,
		p_price                       		NUMBER(10)		 DEFAULT 0		 NOT NULL,
		p_image                       		VARCHAR2(100)		 DEFAULT 'images/no_image.jpg'		 NOT NULL,
		p_desc                        		VARCHAR2(200)		 NULL ,
		ct_no                         		NUMBER(10)		 NULL 
);

DROP SEQUENCE product_p_no_SEQ;

CREATE SEQUENCE product_p_no_SEQ NOMAXVALUE NOCACHE NOORDER NOCYCLE;


CREATE TABLE userinfo(
		userId                        		VARCHAR2(100)		 NULL ,
		password                      		VARCHAR2(100)		 NULL ,
		name                          		VARCHAR2(100)		 NULL ,
		email                         		VARCHAR2(100)		 NULL 
);


CREATE TABLE cart(
		cart_no                       		NUMBER(10)		 NULL ,
		userId                        		VARCHAR2(100)		 NULL ,
		p_no                          		NUMBER(10)		 NULL ,
		cart_qty                      		NUMBER(10)		 DEFAULT 0		 NULL 
);

DROP SEQUENCE cart_cart_no_SEQ;

CREATE SEQUENCE cart_cart_no_SEQ NOMAXVALUE NOCACHE NOORDER NOCYCLE;

CREATE TABLE address(
		addr_no                       		NUMBER(10)		 NULL ,
		userId                        		VARCHAR2(100)		 NULL ,
		loc                           		VARCHAR2(100)		 NULL 
);

DROP SEQUENCE address_addr_no_SEQ;

CREATE SEQUENCE address_addr_no_SEQ NOMAXVALUE NOCACHE NOORDER NOCYCLE;


CREATE TABLE orders(
		o_no                          		NUMBER(10)		 NULL ,
		o_date                        		DATE		 DEFAULT sysdate		 NULL ,
		o_status                      		CHAR(10)		 DEFAULT 'T'		 NULL ,
		o_option                      		VARCHAR2(100)		 NULL ,
		o_price                       		NUMBER(10)		 NULL ,
		userId                        		VARCHAR2(100)		 NULL ,
		addr_no                       		NUMBER(10)		 NULL 
);

DROP SEQUENCE orders_o_no_SEQ;

CREATE SEQUENCE orders_o_no_SEQ NOMAXVALUE NOCACHE NOORDER NOCYCLE;

CREATE TABLE order_item(
		oi_no                         		NUMBER(10)		 NULL ,
		oi_qty                        		NUMBER(10)		 NULL ,
		o_no                          		NUMBER(10)		 NULL ,
		p_no                          		NUMBER(10)		 NULL 
);

DROP SEQUENCE order_item_oi_no_SEQ;

CREATE SEQUENCE order_item_oi_no_SEQ NOMAXVALUE NOCACHE NOORDER NOCYCLE;


CREATE TABLE board(
		boardno                       		NUMBER(10)		 NULL ,
		title                         		VARCHAR2(50)		 NOT NULL,
		content                       		VARCHAR2(2000)		 NOT NULL,
		regdate                       		DATE		 DEFAULT sysdate		 NULL ,
		readcount                     		NUMBER(10)		 DEFAULT 0		 NULL ,
		groupno                       		NUMBER(10)		 NOT NULL,
		step                          		NUMBER(10)		 NOT NULL,
		depth                         		NUMBER(10)		 DEFAULT 0		 NULL ,
		userId                        		VARCHAR2(100)		 NULL ,
		p_no                          		NUMBER(10)		 NULL 
);

DROP SEQUENCE board_boardno_SEQ;

CREATE SEQUENCE board_boardno_SEQ NOMAXVALUE NOCACHE NOORDER NOCYCLE;

ALTER TABLE categories ADD CONSTRAINT IDX_categories_PK PRIMARY KEY (ct_no);

ALTER TABLE product ADD CONSTRAINT IDX_product_PK PRIMARY KEY (p_no);
ALTER TABLE product ADD CONSTRAINT IDX_product_FK0 FOREIGN KEY (ct_no) REFERENCES categories (ct_no);

ALTER TABLE userinfo ADD CONSTRAINT IDX_userinfo_PK PRIMARY KEY (userId);

ALTER TABLE cart ADD CONSTRAINT IDX_cart_PK PRIMARY KEY (cart_no);
ALTER TABLE cart ADD CONSTRAINT IDX_cart_FK0 FOREIGN KEY (userId) REFERENCES userinfo (userId);
ALTER TABLE cart ADD CONSTRAINT IDX_cart_FK1 FOREIGN KEY (p_no) REFERENCES product (p_no);

ALTER TABLE address ADD CONSTRAINT IDX_address_PK PRIMARY KEY (addr_no);
ALTER TABLE address ADD CONSTRAINT IDX_address_FK0 FOREIGN KEY (userId) REFERENCES userinfo (userId);

ALTER TABLE orders ADD CONSTRAINT IDX_orders_PK PRIMARY KEY (o_no);
ALTER TABLE orders ADD CONSTRAINT IDX_orders_FK0 FOREIGN KEY (userId) REFERENCES userinfo (userId);
ALTER TABLE orders ADD CONSTRAINT IDX_orders_FK1 FOREIGN KEY (addr_no) REFERENCES address (addr_no) on delete cascade;

ALTER TABLE order_item ADD CONSTRAINT IDX_order_item_PK PRIMARY KEY (oi_no);
ALTER TABLE order_item ADD CONSTRAINT IDX_order_item_FK0 FOREIGN KEY (o_no) REFERENCES orders (o_no) on delete cascade;
ALTER TABLE order_item ADD CONSTRAINT IDX_order_item_FK1 FOREIGN KEY (p_no) REFERENCES product (p_no);

ALTER TABLE board ADD CONSTRAINT IDX_board_PK PRIMARY KEY (boardno);
ALTER TABLE board ADD CONSTRAINT IDX_board_FK0 FOREIGN KEY (p_no) REFERENCES product (p_no);
ALTER TABLE board ADD CONSTRAINT IDX_board_FK1 FOREIGN KEY (userId) REFERENCES userinfo (userId) on delete cascade;

