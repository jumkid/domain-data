CREATE TABLE domain (
    domain_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    industry VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    value VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX unique_key_domain_name_value ON domain(industry, name, value);