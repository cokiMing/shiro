create DATABASE shiro;
use shiro;
create table user (
  id VARCHAR(30) PRIMARY KEY,
  name VARCHAR(10),
  account VARCHAR(50) UNIQUE,
  password VARCHAR(50),
  create_time DATE,
  update_time DATE
)CHARSET=utf8;

CREATE INDEX IDX_ACCOUNT ON user(account);