ALTER TABLE restaurante ADD COLUMN aberto tinyint(1);
UPDATE restaurante set aberto = TRUE;