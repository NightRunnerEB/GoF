use std::vec::IntoIter;

#[derive(Debug, Clone, PartialEq, Eq)]
pub struct Employee {
    pub name: String,
}

#[derive(Debug, Default)]
pub struct Department {
    employees: Vec<Employee>,
}

impl Department {
    pub fn new() -> Self {
        Self {
            employees: Vec::new(),
        }
    }

    pub fn add_employee(&mut self, name: &str) {
        self.employees.push(Employee {
            name: name.to_string(),
        });
    }

    fn into_iter(self) -> IntoIter<Employee> {
        self.employees.into_iter()
    }
}

#[derive(Debug, Default)]
pub struct Company {
    departments: Vec<Department>,
}

impl Company {
    pub fn new() -> Self {
        Self {
            departments: Vec::new(),
        }
    }

    pub fn add_department(&mut self, department: Department) {
        self.departments.push(department);
    }
}

pub struct CompanyIter {
    dept_iter: IntoIter<Department>,
    emp_iter: Option<IntoIter<Employee>>,
}

impl Iterator for CompanyIter {
    type Item = Employee;

    fn next(&mut self) -> Option<Self::Item> {
        loop {
            if self.emp_iter.as_ref().map_or(true, |it| it.len() == 0) {
                if let Some(department) = self.dept_iter.next() {
                    self.emp_iter = Some(department.into_iter());
                } else {
                    return None;
                }
            }

            if let Some(employee) = self.emp_iter.as_mut().and_then(|it| it.next()) {
                return Some(employee);
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

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example() {
        let mut it = Department::new();
        it.add_employee("Alice");
        it.add_employee("Bob");

        let mut hr = Department::new();
        hr.add_employee("Charlie");

        let mut company = Company::new();
        company.add_department(it);
        company.add_department(hr);

        // Client doesn't need to know about departments!
        for emp in company {
            println!("Employee: {}", emp.name);
        }
    }
}
