CREATE UNIQUE INDEX ON attachment(file_name, deleted)
WHERE deleted IS false