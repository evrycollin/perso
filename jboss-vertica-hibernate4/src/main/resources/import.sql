--
-- JBoss, Home of Professional Open Source
-- Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
-- contributors by the @authors tag. See the copyright.txt in the
-- distribution for a full listing of individual contributors.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- http://www.apache.org/licenses/LICENSE-2.0
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- You can use this file to load seed data into the database using SQL statements
insert into MemberHibernate4Demo (id, name, email, phone_number, address) values (0, 'John Smith', 'john.smith@mailinator.com', '2125551212', 'Boston NY') 
insert into MemberHibernate4Demo (id, name, email, phone_number, address) values (1, 'Madhumita Sadhukhan', 'msadhukh@gmail.com', '2135551214', 'Brno CZ') 

insert into T_TYPEPRODUIT (id, code,description) values (1, 'generic','produit generic')

insert into T_PRODUIT (id, code, typeProduit_id ) values ( 1, 'prd-1', 1 )
insert into T_PRODUIT (id, code, typeProduit_id ) values ( 2, 'prd-2', 1 )

insert into T_COMMANDE (id) values (1)
insert into T_COMMANDE_LIGNE (id, commande_id, produit_id, quantity) values (1,1,1,10)
insert into T_COMMANDE_LIGNE (id, commande_id, produit_id, quantity) values (2,1,2,2)
insert into T_COMMANDE_LIGNE (id, commande_id, produit_id, quantity) values (3,1,2,3)

insert into T_COMMANDE (id) values (2)
insert into T_COMMANDE_LIGNE (id, commande_id, produit_id, quantity) values (4,2,2,7)

