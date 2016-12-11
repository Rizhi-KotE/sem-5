DROP TABLE IF EXISTS individual_mpd;
DROP TABLE IF EXISTS outlets;
DROP TABLE IF EXISTS companies ;
DROP TABLE IF EXISTS substances;
DROP TABLE IF EXISTS substance_danger_class;
DROP TABLE IF EXISTS alignments;

CREATE TABLE if NOT EXISTS companies
    (company_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    company_name VARCHAR(255) NOT NULL,
   water_usage_type ENUM('single','multiple')  NOT NULL);

CREATE TABLE IF NOT EXISTS outlets
    (outlet_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    diameter DOUBLE NOT NULL,
    flow_rate DOUBLE NOT NULL,
    waste DOUBLE NOT NULL,
    angle DOUBLE NOT NULL,
    depth DOUBLE NOT NULL,
    distance_to_coast DOUBLE NOT NULL,
    distance_on_water DOUBLE  NOT NULL,
    company_id BIGINT  NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies(company_id)
    ON UPDATE RESTRICT
    ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS substance_danger_class
    (water_usage_type ENUM('single', 'multiple')  NOT NULL,
    danger_class ENUM('danger', 'not_danger')  NOT NULL,
    substance_id BIGINT NOT NULL);


CREATE TABLE IF NOT EXISTS substances
    (substance_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    substance_name VARCHAR(255) NOT NULL);

CREATE TABLE IF NOT EXISTS alignments
    (alignment_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    alignment_distance DOUBLE  NOT NULL);


CREATE TABLE IF NOT EXISTS individual_mpd
    (individual_mpd_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    outlet_id BIGINT NOT NULL,
    alignment_id BIGINT NOT NULL,
    substance_id BIGINT NOT NULL,
    background_concentration DOUBLE NOT NULL,
    concentration_in_effluent DOUBLE NOT NULL,
    date DATE NOT NULL,
    FOREIGN KEY (outlet_id) REFERENCES outlets(outlet_id)
    ON UPDATE RESTRICT
    ON DELETE CASCADE,
    FOREIGN KEY (alignment_id) REFERENCES alignments(alignment_id)
    ON UPDATE RESTRICT
    ON DELETE CASCADE,
    FOREIGN KEY (substance_id) REFERENCES substances(substance_id)
    ON UPDATE RESTRICT
    ON DELETE CASCADE);

CREATE OR REPLACE VIEW  mpds_view
AS SELECT * FROM individual_mpd AS mpd
    INNER JOIN outlets_view AS o    USING (outlet_id)
    INNER JOIN substances AS s USING (substance_id)
    INNER JOIN alignments AS a USING (alignment_id);

CREATE OR REPLACE VIEW  outlets_view
AS SELECT * FROM outlets
    INNER JOIN companies USING (company_id);