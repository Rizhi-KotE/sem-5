use PBZ

CREATE TABLE providers (
  provider_id VARCHAR(5) PRIMARY KEY NOT NULL,
  provider_name VARCHAR(20),
  status VARCHAR(20),
  city VARCHAR(20)
);

CREATE TABLE details (
  detail_id VARCHAR(5) PRIMARY KEY NOT NULL,
  detail_name VARCHAR(20),
  color VARCHAR(20),
  size INTEGER,
  city VARCHAR(20)
);

CREATE TABLE projects (
  project_id VARCHAR(5) PRIMARY KEY NOT NULL,
  project_name VARCHAR(20),
  city VARCHAR(20)
);

CREATE TABLE dependencies (
  provider_id VARCHAR(5),
  detail_id VARCHAR(5),
  project_id VARCHAR(5),
  count INTEGER
);

UPDATE projects 
SET City='Tallinn'
WHERE project_id='PR2'; 

INSERT INTO providers VALUES
  ('P1', 'Petrov', '20', 'Moskva'),
  ('P2', 'Sinitsin', '10', 'Tallinn'),
  ('P3', 'Fedorov', '30', 'Tallinn'),
  ('P4', 'Chajanov', '20', 'Minsk'),
  ('P5', 'Krukov', '30', 'Kiev');

INSERT INTO details VALUES
  ('D1', 'Bolt', 'Red', 12, 'Moskva'),
  ('D2', 'Nut', 'Green', 17, 'Minsk'),
  ('D3', 'Disk', 'Black', 17, 'Vilnjus'),
  ('D4', 'Disk', 'Black', 14, 'Moskva'),
  ('D5', 'Body', 'Red', 12, 'Minsk'),
  ('D6', 'Cap', 'Red', 19, 'Moskva');

INSERT INTO projects VALUES
  ('PR1', 'IPR1', 'Minsk'),
  ('PR2', 'IPR2', 'Tallinn'),
  ('PR3', 'IPR3', 'Pskov'),
  ('PR4', 'IPR4', 'Pskov'),
  ('PR5', 'IPR4', 'Moskva'),
  ('PR6', 'IPR6', 'Saratov'),
  ('PR7', 'IPR7', 'Moskva');

INSERT INTO dependencies VALUES
  ('P1','D1','PR1','200'),
  ('P1','D1','PR2','700'),
  ('P2','D3','PR1','400'),
  ('P2','D2','PR2','200'),
  ('P2','D3','PR3','200'),
  ('P2','D3','PR4','500'),
  ('P2','D3','PR5','600'),
  ('P2','D3','PR6','400'),
  ('P2','D3','PR7','800'),
  ('P2','D5','PR2','100'),
  ('P3','D3','PR1','200'),
  ('P3','D4','PR2','500'),
  ('P4','D6','PR3','300'),
  ('P4','D6','PR7','300'),
  ('P5','D2','PR2','200'),
  ('P5','D2','PR4','100'),
  ('P5','D5','PR5','500'),
  ('P5','D5','PR7','100'),
  ('P5','D6','PR2','200'),
  ('P5','D1','PR2','100'),
  ('P5','D3','PR4','200'),
  ('P5','D4','PR4','800'),
  ('P5','D5','PR4','400'),
  ('P5','D6','PR4','500');

