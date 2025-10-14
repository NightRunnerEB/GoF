package behavioral;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

final class IteratorTest {

    @Test
    void companyIteratorTraversesAllEmployees() {
        Department it = new Department();
        it.addEmployee(new Employee("Alice"));
        it.addEmployee(new Employee("Bob"));

        Department hr = new Department();
        hr.addEmployee(new Employee("Charlie"));

        Company company = new Company();
        company.addDepartment(it);
        company.addDepartment(hr);

        List<String> names = new ArrayList<>();
        for (Employee employee : company) {
            names.add(employee.name);
        }

        assertThat(names).containsExactly("Alice", "Bob", "Charlie");
    }
}
