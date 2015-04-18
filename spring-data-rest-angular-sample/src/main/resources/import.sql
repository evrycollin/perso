insert into task_group (id, name) values (1, 'admin');
insert into task_group (id, name) values (2, 'user');

INSERT INTO task_address(id, address)    VALUES (1, 'rue des rosiers, 21222 rkrk');
INSERT INTO task_address(id, address)    VALUES (2, 'rue des lilas, 36663 dsdsd');

insert into task_user(id,login,firstname, address_id) values (1, 'admin', 'admin',1);
insert into task_user(id,login,firstname, address_id) values (2, 'user', 'user',2);

insert into task_users_groups( user_id, group_id ) values (1,1);
insert into task_users_groups( user_id, group_id ) values (1,2);
insert into task_users_groups( user_id, group_id ) values (2,2);


INSERT INTO task_list(id, task_archived, task_name, task_description, task_priority, task_status, user_id) VALUES (1,0,'Task 1','faire les courses','HIGH','ACTIVE', 2)
INSERT INTO task_list(id, task_archived, task_name, task_description, task_priority, task_status, user_id) VALUES (2,1,'Task 2','laver la voiture','MEDIUM','ACTIVE', 2)
INSERT INTO task_event( id, description, task_id) VALUES (1, 'evenement exceptionnel 1', 1)
INSERT INTO task_event( id, description, task_id) VALUES (2, 'evenement exceptionnel 2', 1)
INSERT INTO task_event_location(id, location, event_id) VALUES (1, 'lieu-1', 1)
INSERT INTO task_event_location(id, location, event_id) VALUES (2, 'lieu-2', 1)
INSERT INTO task_event_location(id, location, event_id) VALUES (3, 'lieu-3', 2)
INSERT INTO task_event_location(id, location, event_id) VALUES (4, 'lieu-4', 2)
