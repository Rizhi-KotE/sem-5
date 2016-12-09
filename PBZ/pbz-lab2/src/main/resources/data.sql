insert into companies (company_id, company_name) values (1,'Meevee');
insert into companies (company_id, company_name) values (2,'Meembee');

insert into outlets (diameter, flow_rate, waste, angle, depth, distance_to_coast, company_id) values (47, 72, 99, 149, 96, 54, 1);
insert into outlets (diameter, flow_rate, waste, angle, depth, distance_to_coast, company_id) values (5, 77, 84, 52, 31, 77, 1);
insert into outlets (diameter, flow_rate, waste, angle, depth, distance_to_coast, company_id) values (82, 44, 58, 197, 57, 87, 1);
insert into outlets (diameter, flow_rate, waste, angle, depth, distance_to_coast, company_id) values (40, 90, 66, 315, 90, 45, 2);
insert into outlets (diameter, flow_rate, waste, angle, depth, distance_to_coast, company_id) values (96, 87, 43, 180, 45, 61, 2);
insert into outlets (diameter, flow_rate, waste, angle, depth, distance_to_coast, company_id) values (42, 38, 36, 153, 38, 96,2);

insert into individual_mpd (outlet_id, alignment_id, substance_id, background_concentration , concentration_in_effluent, date)
values
(1, 1, 1,0, 0, '2012-01-01'),
(1,2,1,0,0,'2012-01-01'),
(1,2,2,0,0,'2012-01-01'),
(1,2,2,0,0,'2013-01-01'),
(1,2,1,0,0,'2013-01-01'),
(1, 1, 1,0, 0, '2013-01-01');

insert into substances (substance_name) values
('азот'),('метан');

insert into alignments (alignment_distance) values
(30),(40);

