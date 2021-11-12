DROP TABLE IF EXISTS transfer;

CREATE TABLE transfer (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  value DECIMAL(10,2) NOT NULL,
  transfer_date DATE NOT NULL,
  schedule_date DATE NULL,
  fk_origin_account varchar(20) NOT NULL,
  fk_destination_account varchar(20) NOT NULL,
  fk_tax INT NOT NULL
);

ALTER TABLE transfer ADD FOREIGN KEY (fk_origin_account) REFERENCES account(number);
ALTER TABLE transfer ADD FOREIGN KEY (fk_destination_account) REFERENCES account(number);
ALTER TABLE transfer ADD FOREIGN KEY (fk_tax) REFERENCES tax(id);
