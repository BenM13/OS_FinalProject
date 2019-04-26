SELECT c.student_id AS id, c.course_number AS course,
students.student_name AS student, students.student_email AS email
FROM (SELECT * FROM courses_taken WHERE course_number IN 
('181x/FL15', '181x/FL16') AND completed = 1) c 
JOIN students ON students.student_id = c.student_id;

