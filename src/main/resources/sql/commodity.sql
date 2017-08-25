CREATE TABLE commodity (
  commodity_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  type_id VARCHAR(30) NOT NULL ,
  category_id VARCHAR(30) NOT NULL ,
  create_time TIMESTAMP,
  update_time TIMESTAMP,
  status VARCHAR(20) DEFAULT 'ON_SALE',
  name VARCHAR(15) NOT NULL,
  description VARCHAR(150),
  pics VARCHAR(200),
  unit VARCHAR(10) NOT NULL,
  prize NUMERIC(8,2) DEFAULT 0.00,
  num INTEGER DEFAULT 0
)charset=utf8;

