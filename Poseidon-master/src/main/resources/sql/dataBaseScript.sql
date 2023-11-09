CREATE TABLE bid_list (
  bid_list_id tinyint(4) NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  bid_quantity DOUBLE,
  ask_quantity DOUBLE,
  bid DOUBLE,
  ask DOUBLE,
  benchmark VARCHAR(125),
  bid_list_date TIMESTAMP,
  commentary VARCHAR(125),
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  book VARCHAR(125),
  creation_name VARCHAR(125),
  creation_date TIMESTAMP ,
  revision_name VARCHAR(125),
  revision_date TIMESTAMP ,
  deal_name VARCHAR(125),
  deal_type VARCHAR(125),
  source_list_id VARCHAR(125),
  side VARCHAR(125),

  PRIMARY KEY (bid_list_id)
)

CREATE TABLE trade (
  trade_id tinyint(4) NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  buy_quantity DOUBLE,
  sell_quantity DOUBLE,
  buy_price DOUBLE ,
  sell_price DOUBLE,
  trade_date TIMESTAMP,
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  benchmark VARCHAR(125),
  book VARCHAR(125),
  creation_name VARCHAR(125),
  creation_date TIMESTAMP ,
  revision_name VARCHAR(125),
  revision_date TIMESTAMP ,
  deal_name VARCHAR(125),
  deal_type VARCHAR(125),
  source_list_id VARCHAR(125),
  side VARCHAR(125),

  PRIMARY KEY (trade_id)
)

CREATE TABLE curve_point (
  id tinyint(4) NOT NULL AUTO_INCREMENT,
  curve_id tinyint,
  as_Of_date TIMESTAMP,
  term DOUBLE ,
  value DOUBLE ,
  creation_date TIMESTAMP ,

  PRIMARY KEY (id)
)

CREATE TABLE rating (
  id tinyint(4) NOT NULL AUTO_INCREMENT,
  moodys_rating VARCHAR(125),
  sand_prating VARCHAR(125),
  fitch_rating VARCHAR(125),
  order_number tinyint,

  PRIMARY KEY (id)
)

CREATE TABLE rule_name (
  id tinyint(4) NOT NULL AUTO_INCREMENT,
  name VARCHAR(125),
  description VARCHAR(125),
  json VARCHAR(125),
  template VARCHAR(512),
  sql_str VARCHAR(125),
  sql_part VARCHAR(125),

  PRIMARY KEY (id)
)

CREATE TABLE users (
  id tinyint(4) NOT NULL AUTO_INCREMENT,
  username VARCHAR(125),
  password VARCHAR(125),
  full_name VARCHAR(125),
  role VARCHAR(125),
  enabled BOOLEAN,

  PRIMARY KEY (id)
)

insert into users(full_name, username, password, role, enabled) values("UserTest", "usertest", "$2a$10$SHt00jgds98hHBURT1AuGumME2I834NjDXbxyc0XSpfxSlfVjNywW", "USER", true)
insert into users(full_name, username, password, role, enabled) values("AdminTest", "admintest", "$2a$10$H2GXoeHja3yppi3P7tUtWOr6aIHtv.1S20mYM1is/grZXKVV2XZQu", "ADMIN", true)