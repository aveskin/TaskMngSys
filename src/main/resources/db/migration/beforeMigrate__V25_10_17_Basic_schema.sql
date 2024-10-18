ALTER TABLE task_management.t_comments
    ADD COLUMN IF Not EXISTS task_id int,
    ADD COLUMN IF Not EXISTS author_name varchar;

