CREATE UNIQUE INDEX “file_name”
    ON attachment(file_name, deleted)
    WHERE deleted IS false