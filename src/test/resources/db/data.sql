ALTER TABLE domain_industry AUTO_INCREMENT = 1;
TRUNCATE TABLE domain_industry;

INSERT INTO domain_industry (industry,domain_name,domain_value)
VALUES ('dummy industry', 'dummy name', 'dummy 1');
INSERT INTO domain_industry (industry,domain_name,domain_value)
VALUES ('dummy industry', 'dummy name', 'dummy 2');

COMMIT;