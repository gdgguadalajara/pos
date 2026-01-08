INSERT INTO user_pos (createdat, updatedat, id, email, name) VALUES
    (NOW(),NOW(),'e880a105-a9f9-4c9f-be88-7701498128bb','admin@gdgguadalajara.com','admin');

INSERT INTO account (createdat, updatedat, id, user_id, password, role, status, username) VALUES
    (NOW(),NOW(),'621d7eea-86eb-414f-9c0d-c4ac2af28783','e880a105-a9f9-4c9f-be88-7701498128bb','fb001dfcffd1c899f3297871406242f097aecf1a5342ccf3ebcd116146188e4b','ADMIN','ACTIVE','admin');
