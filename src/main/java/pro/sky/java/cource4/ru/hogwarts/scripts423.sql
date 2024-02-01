SELECT Student.name, Student.age, Faculty.name AS faculty_name
FROM Student INNER JOIN Faculty ON Student.faculty_id = faculty_id;

SELECT Student.name, Student.age, Avatar.data
FROM Student INNER JOIN Avatar ON Student.id = Avatar.student_id