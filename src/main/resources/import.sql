 -- =================================================================================================
-- Team List
-- teams
 -- =================================================================================================
 INSERT INTO team(team_name)VALUE('SuicideSquad')
 INSERT INTO team(team_name)VALUE('Cyborg Reapers')
 INSERT INTO team(team_name)VALUE('Sneaky Cyborgs')
-- =================================================================================================
-- Address List
-- Addresses
 -- =================================================================================================
 INSERT INTO address (address_street, city, country, postal_code)VALUE('Rua de Ricardo Severo', 'Povoa de varzim', 'Portugal', '4490-547')
 INSERT INTO address (address_street, city, country, postal_code)VALUE('Rua Comendador brandão', 'Povoa de varzim', 'Portugal', '4490-547')
 INSERT INTO address (address_street, city, country, postal_code)VALUE('Rua de Escudeiro', 'Povoa de varzim', 'Portugal', '4490-073')
 INSERT INTO address (address_street, city, country, postal_code)VALUE('Rua da Areeira ', 'Povoa de varzim', 'Portugal', '4490-037')
-- =================================================================================================
-- Person List
-- Employees
 -- =================================================================================================
 INSERT INTO person (address_one_id, address_two_id, email, first_name, gender, last_name, nif)VALUE (1, 2, 'neiva_tiago@hotmail.com', 'Tiago', 'MALE', 'Silva', '222222222')
 INSERT INTO person (address_one_id, address_two_id, email, first_name, gender, last_name, nif)VALUE(1, 2, 'marlene_p_b_@hotmail.com', 'Marlene', 'FEMALE', 'Silva', '222222222')
 INSERT INTO person (address_one_id, address_two_id, email, first_name, gender, last_name, nif)VALUE(3, 4, 'skgdelf@hotmail.com', 'Miguel', 'MALE', 'Silva', '222222222')
 INSERT INTO person (address_one_id, address_two_id, email, first_name, gender, last_name, nif)VALUE(3, 4, 'franciscosilva@gmail.com', 'Francisco', 'MALE', 'Silva', '222222222')
  -- =================================================================================================
 -- employees
  -- =================================================================================================
 insert into employee (birthdaydate, employee_team_name_id, picture)VALUE ("02-02-1999",1,"")
 insert into employee (birthdaydate, employee_team_name_id, picture)VALUE ("02-02-1999",2,"")
 insert into employee (birthdaydate, employee_team_name_id, picture)VALUE ("02-02-1999",3,"")
 insert into employee (birthdaydate, employee_team_name_id, picture)VALUE ("02-02-1999",4,"")
 -- =================================================================================================
-- Person Contacts List
-- Contacts
 -- =================================================================================================
 INSERT INTO contact (contact, contact_name) VALUE ('tiagoneiva', 'Skype')
 INSERT INTO contact (contact, contact_name) VALUE('915906988', 'Phone')
 INSERT INTO contact (contact, contact_name) VALUE('marlene', 'Skype')
 INSERT INTO contact (contact, contact_name) VALUE('915906988', 'Phone')
 INSERT INTO contact (contact, contact_name) VALUE('miguel', 'Skype')
 INSERT INTO contact (contact, contact_name) VALUE('915906988', 'Phone')
 INSERT INTO contact (contact, contact_name) VALUE('francisco', 'Skype')
 INSERT INTO contact (contact, contact_name) VALUE('915906988', 'Phone')
 -- =================================================================================================
-- Skills List
-- Skills
 -- =================================================================================================
 INSERT INTO work_skill (name_of_skill) VALUE ('java')
 INSERT INTO work_skill (name_of_skill) VALUE('C#')
 INSERT INTO work_skill (name_of_skill) VALUE('HTML 5')
 -- =================================================================================================
-- Person Contact List
-- Contact List
 -- =================================================================================================

 INSERT INTO person_contact_list (person_id, contact_list_id) VALUE (1, 1)
 INSERT INTO person_contact_list (person_id, contact_list_id) VALUE (1, 1)
 INSERT INTO person_contact_list (person_id, contact_list_id) VALUE (1, 2)
 INSERT INTO person_contact_list (person_id, contact_list_id) VALUE (2, 3)
 INSERT INTO person_contact_list (person_id, contact_list_id) VALUE (2, 4)
 INSERT INTO person_contact_list (person_id, contact_list_id) VALUE (3, 5)
 INSERT INTO person_contact_list (person_id, contact_list_id) VALUE (3, 6)
 INSERT INTO person_contact_list (person_id, contact_list_id) VALUE (4, 7)
 INSERT INTO person_contact_list (person_id, contact_list_id) VALUE (4, 8)
  -- =================================================================================================
  -- Person Skill List
  -- Skill list for employee
 -- =================================================================================================

 INSERT INTO person_skill_list (employee_id, skill_list_id) VALUE(1, 1)
 INSERT INTO person_skill_list (employee_id, skill_list_id) VALUE(1, 2)
 INSERT INTO person_skill_list (employee_id, skill_list_id) VALUE(1, 3)
 INSERT INTO person_skill_list (employee_id, skill_list_id) VALUE(1, 1)
 INSERT INTO person_skill_list (employee_id, skill_list_id) VALUE(1, 2)
 INSERT INTO person_skill_list (employee_id, skill_list_id) VALUE(1, 3)
 INSERT INTO person_skill_list (employee_id, skill_list_id) VALUE(1, 1)
 INSERT INTO person_skill_list (employee_id, skill_list_id) VALUE(1, 2)
 INSERT INTO person_skill_list (employee_id, skill_list_id) VALUE(1, 3)
 INSERT INTO person_skill_list (employee_id, skill_list_id) VALUE(1, 1)
 INSERT INTO person_skill_list (employee_id, skill_list_id) VALUE(1, 2)
 INSERT INTO person_skill_list (employee_id, skill_list_id) VALUE(1, 3)