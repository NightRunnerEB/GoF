use std::vec::IntoIter;

// ===== Model =====
#[derive(Debug)]
struct Employee {
    name: String,
}

#[derive(Debug)]
struct Department {
    employees: Vec<Employee>,
}

impl Department {
    fn new() -> Self {
        Self { employees: vec![] }
    }

    fn add_employee(&mut self, name: &str) {
        self.employees.push(Employee {
            name: name.to_string(),
        });
    }
}

// Company holds departments
#[derive(Debug)]
struct Company {
    departments: Vec<Department>,
}

impl Company {
    fn new() -> Self {
        Self {
            departments: vec![],
        }
    }

    fn add_department(&mut self, dept: Department) {
        self.departments.push(dept);
    }
}

// ===== Iterator =====
struct CompanyIter {
    dept_iter: IntoIter<Department>,      // iterate over departments
    emp_iter: Option<IntoIter<Employee>>, // current employee iterator
}

impl Iterator for CompanyIter {
    type Item = Employee;

    fn next(&mut self) -> Option<Self::Item> {
        loop {
            // If we have no current employee iterator or it is exhausted,
            if self.emp_iter.as_ref().map_or(true, |it| it.len() == 0) {
                // take the next department
                if let Some(dept) = self.dept_iter.next() {
                    self.emp_iter = Some(dept.employees.into_iter());
                } else {
                    return None; // no departments left
                }
            }

            // try to take an employee
            if let Some(emp) = self.emp_iter.as_mut().and_then(|it| it.next()) {
                return Some(emp);
            }
        }
    }
}

impl IntoIterator for Company {
    type Item = Employee;
    type IntoIter = CompanyIter;

    fn into_iter(self) -> Self::IntoIter {
        CompanyIter {
            dept_iter: self.departments.into_iter(),
            emp_iter: None,
        }
    }
}

// ===== Client =====
fn main() {
    let mut it = Department::new();
    it.add_employee("Alice");
    it.add_employee("Bob");

    let mut hr = Department::new();
    hr.add_employee("Charlie");

    let mut company = Company::new();
    company.add_department(it);
    company.add_department(hr);

    // Client does not need to know about departments.
    for emp in company {
        println!("Employee: {}", emp.name);
    }
}
