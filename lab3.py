class Student:

    students_quantity = 0

    def __init__(self, name, course, grades):
        self.name = name
        self.course = course
        self.grades = grades
        Student.students_quantity += 1

    def avg_score_by_subject(self, subject):
        if subject in self.grades:
            subject_grades = self.grades[subject]
            return sum(subject_grades)/len(subject_grades)
        else:
            return None

    def is_honors_student(self):
        avg = 0
        iter = 0
        for subject in self.grades:
            subject_grades = self.grades[subject]
            avg += sum(subject_grades) / len(subject_grades)
            iter += 1
        total = avg / iter
        print(total)
        if total >= 8.5:
            return True
        else: return False

n = int(input("Enter the number of students: "))
students = []
for i in range(n):
    print(f"Student {i+1} data: ")
    name = input("Enter the student's name: ")
    course = int(input("Enter the student's course: "))
    grades ={}
    N = int(input(f"Enter the number of {name}'s subjects: "))
    for j in range(N):
        subject = input(f"Enter subject {j+1}: ")
        scores = input(f"Enter grades for {subject} : ")
        grades[subject] = list(map(int, scores.split()))

    student = Student(name, course, grades)
    students.append(student)

for student in students:
    print(f"Student: {student.name}")
    print(f"Course: {student.course}")
    print(f"Grades: {student.grades}")
    print(f"Is a honored student? {student.is_honors_student()}")
    answer = str(input("Do you want to know the average score on a particular subject? "))
    if(answer.lower() == 'yes'):
        subject = str(input("Enter the subject: "))
        if subject in student.grades:
            print(f"Average score in {subject}: {student.avg_score_by_subject(subject)}")
        else:
            print(f"Subject {subject} is not found")