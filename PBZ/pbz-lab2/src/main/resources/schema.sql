DROP TABLE companies IF EXISTS;
DROP TABLE outlets IF EXISTS;
DROP TABLE individual_mpd IF EXISTS;

CREATE TABLE if NOT EXISTS companies
    (company_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    company_name VARCHAR(255));
CREATE TABLE IF NOT EXISTS outlets
    (outlet_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    diameter DOUBLE NOT NULL,
    flow_rate DOUBLE NOT NULL,
    waste DOUBLE NOT NULL,
    angle DOUBLE NOT NULL,
    depth DOUBLE NOT NULL,
    distance_to_coast DOUBLE NOT NULL,
    distance_on_water DOUBLE,
    company_id BIGINT);
CREATE TABLE IF NOT EXISTS individual_mpd
    (individual_mpd_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    outlet_id BIGINT NOT NULL,
    alignment_id BIGINT NOT NULL,
    substance_id BIGINT NOT NULL,
    background_concentration DOUBLE NOT NULL,
    concentration_in_effluent DOUBLE NOT NULL,
    date DATE NOT NULL);

CREATE TABLE IF NOT EXISTS substances
    (substance_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    substance_name VARCHAR(255) NOT NULL);

CREATE TABLE IF NOT EXISTS alignments
    (alignment_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    alignment_distance DOUBLE);