INSERT INTO users (id, email, password_hash, role, created_at, updated_at)
SELECT
    gen_random_uuid(),
    'admin@minishop.com',
    '$2a$10$zs389OWdZ8m3cTpRPektKO6MrTDNcETLGmcGc5l3UD/b0ONn1JATG',
    'ADMIN',
    now(),
    now()
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'admin@minishop.com'
);

--парол: admin123