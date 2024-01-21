package Forms;

import Models.Student;
import Services.FileService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class StudentForm extends JFrame{
    private JPanel StudentForm;
    private JTextField tfFirstName;
    private JTextField tfLastName;
    private JLabel lbStatusStudentFirstName;
    private JButton btnClear;
    private JButton btnOk;
    private JTextField tfStudentNo;
    private JComboBox cbStudentDepartment;
    private JComboBox cbStudentCourse;
    private JLabel lbStatusStudentLastName;
    private JLabel lbStatusStudentNo;
    private JLabel lbStatusStudentDepartment;
    private JLabel lbStatusStudentCourse;
    private JLabel lbStatusStudentFaculty;
    private JComboBox cbStudentFaculty;
    private JTextField tfSearchStudent;
    private JTable tableStudents;

    Student student = new Student();
    String studentFirstName = student.FirstName;
    String studentLastName = student.LastName;
    String studentNo = student.StudentNo;
    String studentFaculty = student.StudentFaculty;
    String studentDepartment = student.StudentDepartment;
    String studentCourse = student.StudentCourse;
    FileService fileService = new FileService();
    private int id = Integer.parseInt(String.valueOf(fileService.getLargestStudentNo("src/Data/Student.json")));

    List<String> departmentList = new ArrayList<>();
    List<String> courseList;
    public StudentForm(){
        DefaultTableModel tableStudentModel = new DefaultTableModel();


        tfStudentNo.setText(String.valueOf(id + 1));

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                studentFirstName = tfFirstName.getText();
                studentLastName = tfLastName.getText();
                studentNo = tfStudentNo.getText();
                studentFaculty = (String) cbStudentFaculty.getSelectedItem();
                studentDepartment = (String) cbStudentDepartment.getSelectedItem();
                studentCourse = (String) cbStudentCourse.getSelectedItem();

                lbStatusStudentFirstName.setText("studentFirstName: " + studentFirstName);
                lbStatusStudentLastName.setText("studentLastName: " + studentLastName);
                lbStatusStudentNo.setText("studentNo: " + studentNo);
                lbStatusStudentFaculty.setText("studentFaculty: " + studentFaculty);
                lbStatusStudentDepartment.setText("studentDepartment: " + studentDepartment);
                lbStatusStudentCourse.setText("studentCourse: " + studentCourse);


                if (studentFirstName.isEmpty() || studentLastName.isEmpty() || studentNo.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Lütfen tüm alanları doldurunuz!", "Uyarı", JOptionPane.WARNING_MESSAGE);
                }else {
                    // JSON verilerini hazırla
                    JSONArray jsonArray = new JSONArray();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("studentFirstName", studentFirstName);
                    jsonObject.put("studentLastName", studentLastName);
                    jsonObject.put("studentNo", studentNo);
                    jsonObject.put("StudentFaculty", studentFaculty);
                    jsonObject.put("studentDepartment", studentDepartment);
                    jsonObject.put("studentCourse", studentCourse);
                    jsonArray.put(jsonObject);

                    fileService.WriteToFile("src/Data", "Student.json", jsonArray);

                    id = Integer.parseInt(fileService.getLargestStudentNo("src/Data/Student.json")) + 1;
                    tfStudentNo.setText(String.valueOf(id));

                    List<String[]> students = fileService.getStudentsFromFile("src/Data/Student.json");

                    if (students != null && !students.isEmpty()) {
                        String[] lastStudent = students.get(students.size() - 1);
                        tableStudentModel.addRow(lastStudent);
                    }
                    tableStudents.setModel(tableStudentModel);
                }

            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfFirstName.setText("");
                tfLastName.setText("");
                cbStudentFaculty.setSelectedIndex(-1);

                cbStudentDepartment.setSelectedIndex(-1);
                cbStudentDepartment.removeAllItems();
                cbStudentDepartment.setEnabled(false);

                cbStudentCourse.setSelectedIndex(-1);
                cbStudentCourse.removeAllItems();
                cbStudentCourse.setEnabled(false);

                lbStatusStudentFirstName.setText("");
                lbStatusStudentLastName.setText("");
                lbStatusStudentNo.setText("");
                lbStatusStudentFaculty.setText("");
                lbStatusStudentDepartment.setText("");
                lbStatusStudentCourse.setText("");
            }
        });

        cbStudentFaculty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                studentFaculty = (String) cbStudentFaculty.getSelectedItem();

                if(studentFaculty != null){
                    departmentList = fileService.getDepartmentsFromFile("src/Data/"+studentFaculty+".txt");
                    if (departmentList != null) {
                        cbStudentDepartment.removeAllItems();
                        for (String department : departmentList) {
                            cbStudentDepartment.addItem(department);
                        }
                    }
                }
                cbStudentDepartment.setSelectedIndex(-1);
                cbStudentCourse.setEnabled(false);
                cbStudentCourse.removeAllItems();
            }
        });

        cbStudentDepartment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cbStudentDepartment.setEnabled(true);
                studentDepartment = (String) cbStudentDepartment.getSelectedItem();

                if(studentDepartment != null){
                    courseList = fileService.getCourseNamesByDepartment("src/Data/Course.json",studentDepartment);
                    if (courseList != null) {
                        cbStudentCourse.removeAllItems();
                        for (String course : courseList) {
                            cbStudentCourse.addItem(course);
                        }
                    }
                }
                cbStudentCourse.setEnabled(true);
            }
        });

        tableStudentModel.addColumn("studentNo");
        tableStudentModel.addColumn("studentFirstName");
        tableStudentModel.addColumn("studentLastName");
        tableStudentModel.addColumn("StudentFaculty");
        tableStudentModel.addColumn("studentDepartment");
        tableStudentModel.addColumn("studentCourse");
        tableStudentModel.addRow(new Object[]{"student No", "student First Name", "student Last Name", "Student Faculty", "student Department", "student Course"});

        List<String[]> students = fileService.getStudentsFromFile("src/Data/Student.json");

        if (students != null) {
            for (String[] student : students) {
                tableStudentModel.addRow(student);
            }
        }

        tableStudents.setModel(tableStudentModel);

        tfSearchStudent.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String input = tfSearchStudent.getText().toLowerCase().trim();
                TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(tableStudentModel);
                tableStudents.setRowSorter(rowSorter);

                rowSorter.setRowFilter(RowFilter.regexFilter("(?i).*" + input + ".*"));
            }
        });

        setContentPane(StudentForm);
        setTitle("Student Form");
        setMinimumSize(new Dimension(1100,500));
        setLocation(820,500);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setVisible(true);
    }
/*
    public static void main(String[] args) {
        StudentForm studentForm = new StudentForm();
    }
 */
}
