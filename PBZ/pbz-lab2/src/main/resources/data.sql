insert into companies (company_id, company_name,water_usage_type) values (1,'Meevee',1);
insert into companies (company_id, company_name,water_usage_type) values (2,'Meembee',2);

insert into outlets (diameter, flow_rate, waste, angle, depth, distance_to_coast,distance_on_water, company_id) values
(47, 72, 99, 149, 96, 54, 20, 1),
(5, 77, 84, 52, 31, 77, 20, 1),
(82, 44, 58, 197, 57, 87, 20, 2);

insert into substances (substance_name) values
('азот'),
('метан'),
('cероводород'),
('сернистая кислота');

insert into alignments (alignment_distance) values
(30),
(40);

insert into individual_mpd (outlet_id, alignment_id, substance_id, background_concentration , concentration_in_effluent, date)
values
(1, 1, 1,0, 0, '2012-01-01'),
(1,2,1,0,0,'2012-01-01'),
(1,2,3,0,0,'2012-01-01'),
(3,2,2,0,0,'2013-01-01'),
(3,2,1,0,0,'2013-01-01'),
(3, 1, 3,0, 0, '2013-01-01');


insert into substance_danger_class (water_usage_type, danger_class, substance_id) values
(1,2,1),
(1,1,2),
(1,2,3),
(1,2,4),
(2,1,1),
(2,2,2),
(2,2,3),
(2,1,4);

