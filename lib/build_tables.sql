CREATE TABLE students (
    student_id VARCHAR(20) PRIMARY KEY,
    student_name VARCHAR(60),
    student_email VARCHAR(50),
    email_opt_in BOOLEAN
);

CREATE INDEX idx_opt_flag ON students(email_opt_in);

CREATE TABLE course_list (
    course_number VARCHAR(20) PRIMARY KEY,
    course_name VARCHAR(80),
    semester VARCHAR(20) 
);

CREATE INDEX idx_semester ON course_list(semester);

CREATE TABLE courses_taken (
    student_id VARCHAR(20),
    course_number VARCHAR(20),
    paid BOOLEAN,
    viewed BOOLEAN,
    completed BOOLEAN,
    PRIMARY KEY (student_id, course_number)
);

CREATE INDEX idx_paid ON courses_taken(paid);
CREATE INDEX idx_viewed ON courses_taken(viewed);
CREATE INDEX idx_completed ON courses_taken(completed);