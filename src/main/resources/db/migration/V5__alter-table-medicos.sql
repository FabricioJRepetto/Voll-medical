ALTER TABLE medicos ADD activo tinyint;
UPDATE medicos SET activo = 1;
ALTER TABLE pacientes ADD activo tinyint;
UPDATE pacientes SET activo = 1;
