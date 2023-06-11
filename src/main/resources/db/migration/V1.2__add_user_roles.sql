CREATE TABLE "roles" (
    role_id SERIAL PRIMARY KEY,
    name TEXT UNIQUE NOT NULL
);

CREATE TABLE "user_roles" (
    user_id BIGSERIAL REFERENCES users(id),
    role_id integer REFERENCES roles(role_id),
    PRIMARY KEY (user_id, role_id)
);

