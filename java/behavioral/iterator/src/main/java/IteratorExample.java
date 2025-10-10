
import java.util.*;

// ===== Model =====
class Employee {

    String name;

    Employee(String name) {
        this.name = name;
    }
}

class Department {

    List<Employee> employees = new ArrayList<>();

    void addEmployee(Employee e) {
        employees.add(e);
    }

    List<Employee> getEmployees() {
        return employees;
    }
}

class Company implements Iterable<Employee> {

    List<Department> departments = new ArrayList<>();

    void addDepartment(Department d) {
        departments.add(d);
    }

    @Override
    public Iterator<Employee> iterator() {
        return new CompanyIterator(this);
    }
}

// ===== Iterator =====
class CompanyIterator implements Iterator<Employee> {

    private final Iterator<Department> deptIter;
    private Iterator<Employee> empIter;

    CompanyIterator(Company company) {
        this.deptIter = company.departments.iterator();
        this.empIter = Collections.emptyIterator();
    }

    @Override
    public boolean hasNext() {
        while (!empIter.hasNext() && deptIter.hasNext()) {
            empIter = deptIter.next().getEmployees().iterator();
        }
        return empIter.hasNext();
    }

    @Override
    public Employee next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return empIter.next();
    }
}

// ===== Client =====
public final class IteratorExample {

    private IteratorExample() {
    }

    public static List<String> demo() {
        Department it = new Department();
        it.addEmployee(new Employee("Alice"));
        it.addEmployee(new Employee("Bob"));

        Department hr = new Department();
        hr.addEmployee(new Employee("Charlie"));

        Company company = new Company();
        company.addDepartment(it);
        company.addDepartment(hr);

        List<String> names = new ArrayList<>();
        for (Employee e : company) {
            names.add(e.name);
        }
        return names;
    }
}
