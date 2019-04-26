"""
Python script to import data from Excel tables into SQLite database.
Requires .db file and 3 .xlsx files in the same directory as this script.
The .db file is passed as a positional argument. 

This script should be called as "python3 add_to_table.py DB_FILE"
"""

from sys import argv

import xlrd
import sqlite3


def add_students(conn, filename):
    worksheet = xlrd.open_workbook(filename).sheet_by_index(0)
    cur = conn.cursor()
    count = 0
    bad_count = 0
    for row in range(1, worksheet.nrows):
        student_id = worksheet.row_values(row)[0]
        name = worksheet.row_values(row)[1]
        email = worksheet.row_values(row)[2]
        opt_in = int(worksheet.row_values(row)[3])
        try:
            count += 1
            cur.execute("INSERT INTO students VALUES (?, ?, ?, ?)", 
                        (student_id, name, email, opt_in))
        except sqlite3.OperationalError as e:
            print("There is a problem with the query.")
            print(e)
            break
        except sqlite3.IntegrityError as e:
            print("Couldn't add row number {} to students".format(count))
            print(e)
            bad_count += 1
            continue
    conn.commit()
    print("Successfully added {} rows to students table".format(count - bad_count))
    cur.close()

def add_courses(conn, filename):
    worksheet = xlrd.open_workbook(filename).sheet_by_index(0)
    cur = conn.cursor()
    count = 0
    bad_count = 0
    for row in range(1, worksheet.nrows):
        course_number = worksheet.row_values(row)[0]
        course_name = worksheet.row_values(row)[1]
        semester = worksheet.row_values(row)[2]
        try:
            count += 1
            cur.execute("INSERT INTO course_list VALUES (?, ?, ?)",
                        (course_number, course_name, semester))
        except sqlite3.OperationalError as e:
            print("There is a problem with the query:")
            print(e)
            break
        except sqlite3.IntegrityError as e: 
            print("Couldn't add row # {} to course_list:".format(count))
            print(e)
            bad_count += 1
            continue
    conn.commit()
    print("Successfully added {} rows to course_list table".format(count - bad_count))
    cur.close()

def add_courses_taken(conn, filename):
    worksheet = xlrd.open_workbook(filename).sheet_by_index(0)
    cur = conn.cursor()
    count = 0
    bad_count = 0
    for row in range(1, worksheet.nrows):
        student_id = worksheet.row_values(row)[0]
        course_number = worksheet.row_values(row)[1]
        paid = worksheet.row_values(row)[2]
        viewed = int(worksheet.row_values(row)[3])
        completed = int(worksheet.row_values(row)[4])
        try:
            count += 1
            cur.execute("INSERT INTO courses_taken VALUES (?, ?, ?, ?, ?)",
                        (student_id, course_number, paid, viewed, completed))
        except sqlite3.OperationalError as e:
            print("There is a problem with the query:")
            print(e)
            break
        except sqlite3.IntegrityError as e:
            print("Counldn't add row #{} to courses_taken:".format(count))
            print(e)
            bad_count += 1
            continue
    conn.commit()
    print("Successfully added {} rows to courses_taken table.".format(count - bad_count))
    cur.close()
        

if __name__ == '__main__':
    # pylint: disable=unbalanced-tuple-unpacking
    _, database = argv
    students_file = "mock_students.xlsx"
    courses_file = "mock_course_list.xlsx"
    taken_file = "mock_courses_taken.xlsx"

    db = sqlite3.connect(database)
    add_students(db, students_file)
    add_courses(db, courses_file)
    add_courses_taken(db, taken_file)

    db.close()