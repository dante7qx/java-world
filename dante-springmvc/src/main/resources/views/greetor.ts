class Student {
  fullName: String;
  
  constructor(public firstName, public middleName: public lastName) {
    this.fullName = firstName + " " + middleName + " " + lastName;
  }
}

interface Person {
  
}

function greetor(student: Student) {
  return "hello, " + student.fullName;
}

document.body.innerHTML = greetor(new Student("Michale", "D.", "Dante"));