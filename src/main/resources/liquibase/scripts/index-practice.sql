-- liquibase formatted sql

-- changeset ekuzminykh:1
CREATE INDEX student_name ON student(name);

-- changeset ekuzminykh:2
CREATE INDEX faculty_name_color ON faculty(name, color)