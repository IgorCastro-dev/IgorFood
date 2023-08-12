ALTER TABLE restaurante ADD COLUMN ativado tinyint(1);
UPDATE restaurante set ativado = TRUE;