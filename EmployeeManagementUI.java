import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class EmployeeManagementUI extends JFrame {

    private JTextField idField, nameField, ageField, departmentField, salaryField;
    private JTextArea outputArea;
    private final ArrayList<Employee> employees = new ArrayList<>();
    private Employee employeeToUpdate = null;

    public EmployeeManagementUI() {
        setupUI();
    }

    private void setupUI() {
        setTitle("Employee Management System");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 2));

        idField = addInputField("Employee ID:");
        nameField = addInputField("Name:");
        ageField = addInputField("Age:");
        departmentField = addInputField("Department:");
        salaryField = addInputField("Salary:");

        addButton("Add Employee", e -> addEmployee());
        addButton("View Employees", e -> viewEmployees());
        addButton("Find Employee for Update", e -> findEmployeeForUpdate());
        addButton("Update Employee", e -> updateEmployee());
        addButton("Delete Employee", e -> deleteEmployee());

        outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea));

        setVisible(true);
    }

    private JTextField addInputField(String label) {
        add(new JLabel(label));
        JTextField textField = new JTextField();
        add(textField);
        return textField;
    }

    private void addButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        add(button);
    }

    private void addEmployee() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String name = nameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String department = departmentField.getText().trim();
            double salary = Double.parseDouble(salaryField.getText().trim());

            Employee employee = new Employee(id, name, age, department, salary);
            employees.add(employee);
            outputArea.setText("Employee added successfully.");
            clearFields();
        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid input. Please enter valid data.");
        }
    }

    private void viewEmployees() {
        if (employees.isEmpty()) {
            outputArea.setText("No employee data available.");
        } else {
            StringBuilder output = new StringBuilder("Employees:\n");
            for (Employee emp : employees) {
                output.append(emp).append("\n");
            }
            outputArea.setText(output.toString());
        }
    }
    

    private void findEmployeeForUpdate() {
        try {
            String idInput = JOptionPane.showInputDialog(this, "Enter Employee ID to find:");
            if (idInput == null || idInput.trim().isEmpty()) {
                outputArea.setText("No Employee ID entered.");
                return;
            }

            int id = Integer.parseInt(idInput.trim());
            employeeToUpdate = null;

            for (Employee emp : employees) {
                if (emp.getId() == id) {
                    employeeToUpdate = emp;
                    break;
                }
            }

            if (employeeToUpdate != null) {
                idField.setText(String.valueOf(employeeToUpdate.getId()));
                nameField.setText(employeeToUpdate.getName());
                ageField.setText(String.valueOf(employeeToUpdate.getAge()));
                departmentField.setText(employeeToUpdate.getDepartment());
                salaryField.setText(String.valueOf(employeeToUpdate.getSalary()));

                outputArea.setText("Employee found. You can update the details and click 'Update Employee'.");
            } else {
                outputArea.setText("Employee with ID " + id + " not found.");
            }
        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid input. Please enter a valid numeric Employee ID.");
        }
    }

    private void updateEmployee() {
        if (employeeToUpdate == null) {
            outputArea.setText("Please find an employee first using 'Find Employee for Update'.");
            return;
        }

        try {
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String department = departmentField.getText().trim();
            String salaryText = salaryField.getText().trim();

            if (!name.isEmpty()) employeeToUpdate.setName(name);
            if (!ageText.isEmpty()) employeeToUpdate.setAge(Integer.parseInt(ageText));
            if (!department.isEmpty()) employeeToUpdate.setDepartment(department);
            if (!salaryText.isEmpty()) employeeToUpdate.setSalary(Double.parseDouble(salaryText));

            outputArea.setText("Employee updated successfully.");
            clearFields();
            employeeToUpdate = null;
        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid input. Please enter valid numeric data where required.");
        }
    }

    private void deleteEmployee() {
        try {
            String idInput = JOptionPane.showInputDialog(this, "Enter Employee ID to delete:");
            if (idInput == null || idInput.trim().isEmpty()) {
                outputArea.setText("No Employee ID entered.");
                return;
            }

            int id = Integer.parseInt(idInput.trim());
            boolean removed = employees.removeIf(emp -> emp.getId() == id);
            if (removed) {
                outputArea.setText("Employee with ID " + id + " deleted successfully.");
            } else {
                outputArea.setText("Employee with ID " + id + " not found.");
            }
            clearFields();
        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid input. Please enter a valid numeric Employee ID.");
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        departmentField.setText("");
        salaryField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EmployeeManagementUI::new);
    }

    static class Employee {
        private final int id;
        private String name;
        private int age;
        private String department;
        private double salary;

        public Employee(int id, String name, int age, String department, double salary) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.department = department;
            this.salary = salary;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        @Override
        public String toString() {
            return String.format("ID: %d, Name: %s, Age: %d, Dept: %s, Salary: %.2f",
                    id, name, age, department, salary);
        }
    }
}
