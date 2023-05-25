CREATE TABLE domain_industry(
   domain_id INTEGER AUTO_INCREMENT PRIMARY KEY,
   industry VARCHAR(255) NOT NULL,
   domain_name VARCHAR(255) NOT NULL,
   domain_value VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX unique_key_domain_name_value ON domain_industry(industry, domain_name, domain_value);