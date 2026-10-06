CREATE TABLE users (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    created_at  DATETIME(6)  NOT NULL,
    updated_at  DATETIME(6),
    name        VARCHAR(200) NOT NULL,
    cpf         VARCHAR(14),
    email       VARCHAR(255),
    phone       VARCHAR(50),
    birth_date  DATE,
    password    VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT uk_users_cpf UNIQUE (cpf),
    CONSTRAINT uk_users_email UNIQUE (email)
) ENGINE = InnoDB;

CREATE TABLE user_roles (
    user_id BIGINT      NOT NULL,
    role    VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, role),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE = InnoDB;

CREATE TABLE patient (
    id      BIGINT       NOT NULL,
    address VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT fk_patient_user FOREIGN KEY (id) REFERENCES users (id)
) ENGINE = InnoDB;

CREATE TABLE employee (
    id        BIGINT       NOT NULL,
    cro       VARCHAR(50),
    specialty VARCHAR(100),
    PRIMARY KEY (id),
    CONSTRAINT fk_employee_user FOREIGN KEY (id) REFERENCES users (id)
) ENGINE = InnoDB;

CREATE TABLE disease (
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6)  NOT NULL,
    updated_at DATETIME(6),
    name       VARCHAR(150) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_disease_name UNIQUE (name)
) ENGINE = InnoDB;

CREATE TABLE patient_disease (
    id         BIGINT      NOT NULL AUTO_INCREMENT,
    patient_id BIGINT,
    disease_id BIGINT,
    is_active  SMALLINT,
    created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6),
    note       VARCHAR(500),
    PRIMARY KEY (id),
    CONSTRAINT fk_patient_disease_patient FOREIGN KEY (patient_id) REFERENCES patient (id),
    CONSTRAINT fk_patient_disease_disease FOREIGN KEY (disease_id) REFERENCES disease (id)
) ENGINE = InnoDB;

CREATE TABLE anamnesis (
    id                                 BIGINT        NOT NULL AUTO_INCREMENT,
    created_at                         DATETIME(6)   NOT NULL,
    updated_at                         DATETIME(6),
    version                            INTEGER,
    recorded_at                        DATETIME(6)   NOT NULL,
    recorded_by                        VARCHAR(150),
    responsible_name                   VARCHAR(200),
    reason_for_visit                   VARCHAR(1000),
    flossing_frequency_description     VARCHAR(200),
    brush_count_per_day                INTEGER,
    satisfied_with_teeth               BIT,
    is_pregnant                        BIT,
    uses_drugs                         BIT,
    is_smoker                          BIT,
    uses_respirator                    BIT,
    is_alcoholic                       BIT,
    teeth_grinding                     BIT,
    regular_physical_activity          BIT,
    tongue_cleaning                    BIT,
    facial_asymmetry                   BIT,
    tmj_disorder                       BIT,
    salivary_gland_swelling            BIT,
    medications_description            TEXT,
    pacemaker_description              TEXT,
    bleeding_description               TEXT,
    obstetric_complication_description TEXT,
    recent_stress_description          TEXT,
    mouthwash_description              TEXT,
    lips_description                   TEXT,
    mucosa_description                 TEXT,
    tongue_description                 TEXT,
    gum_description                    TEXT,
    palate_description                 TEXT,
    general_observations               TEXT,
    patient_id                         BIGINT        NOT NULL,
    doctor_id                          BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_anamnesis_patient FOREIGN KEY (patient_id) REFERENCES patient (id),
    CONSTRAINT fk_anamnesis_doctor FOREIGN KEY (doctor_id) REFERENCES employee (id)
) ENGINE = InnoDB;

CREATE TABLE anamnesis_disease (
    anamnesis_id BIGINT NOT NULL,
    disease_id   BIGINT NOT NULL,
    PRIMARY KEY (anamnesis_id, disease_id),
    CONSTRAINT fk_anamnesis_disease_anamnesis FOREIGN KEY (anamnesis_id) REFERENCES anamnesis (id),
    CONSTRAINT fk_anamnesis_disease_disease FOREIGN KEY (disease_id) REFERENCES disease (id)
) ENGINE = InnoDB;
