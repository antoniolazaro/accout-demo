DROP TABLE IF EXISTS account;

CREATE TABLE account (
  number varchar(20) PRIMARY KEY,
  name varchar(50) NOT NULL,
  document varchar(20) NOT NULL
);

