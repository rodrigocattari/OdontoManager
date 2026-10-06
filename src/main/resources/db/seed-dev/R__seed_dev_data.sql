-- Dados de desenvolvimento: carregados somente no perfil dev.
-- Migration repetível: o Flyway reaplica este arquivo sempre que ele muda,
-- por isso todo INSERT precisa ser idempotente (INSERT IGNORE sobre coluna única).

INSERT IGNORE INTO disease (name, created_at, updated_at) VALUES
    ('Diabetes',                   NOW(6), NOW(6)),
    ('Hipertensão',                NOW(6), NOW(6)),
    ('Cardiopatia',                NOW(6), NOW(6)),
    ('Asma',                       NOW(6), NOW(6)),
    ('Hepatite',                   NOW(6), NOW(6)),
    ('HIV',                        NOW(6), NOW(6)),
    ('Epilepsia',                  NOW(6), NOW(6)),
    ('Distúrbio de coagulação',    NOW(6), NOW(6)),
    ('Alergia a anestésico local', NOW(6), NOW(6)),
    ('Alergia a penicilina',       NOW(6), NOW(6));
