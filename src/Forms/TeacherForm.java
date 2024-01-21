package Forms;

import Models.Teacher;
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

public class TeacherForm extends JFrame{
    private JPanel TeacherForm;
    private JTextField tfFirstName;
    private JTextField tfLastName;
    private JTextField tfTeacherNo;
    private JComboBox cbTeacherFaculty;
    private JComboBox cbTeacherDepartment;
    private JComboBox cbTeacherCourse;
    private JTextField tfSearchTeacher;
    private JTable tableTeachers;
    private JButton btnClear;
    private JButton btnOk;
    private JLabel lbStatusTeacherLastName;
    private JLabel lbStatusTeacherFirstName;
    private JLabel lbStatusTeacherNo;
    private JLabel lbStatusTeacherFaculty;
    private JLabel lbStatusTeacherDepartment;
    private JLabel lbStatusTeacherCourse;

    Teacher teacher = new Teacher();
    String teacherFirstName = teacher.FirstName;
    String teacherLastName = teacher.LastName;
    String teacherNo = teacher.TeacherNo;
    String teacherFaculty = teacher.TeacherFaculty;
    String teacherDepartment = teacher.TeacherDepartment;
    String teacherCourse = teacher.TeacherCourse;
    FileService fileService = new FileService();
    private int id = Integer.parseInt(String.valueOf(fileService.getLargestTeacherNo("src/Data/Teacher.json")));

    List<String> departmentList = new ArrayList<>();
    List<String> courseList;
    public TeacherForm(){
        DefaultTableModel tableTeacherModel = new DefaultTableModel();


        tfTeacherNo.setText(String.valueOf(id + 1));


        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                teacherFirstName = tfFirstName.getText();
                teacherLastName = tfLastName.getText();
                teacherNo = tfTeacherNo.getText();
                teacherFaculty = (String) cbTeacherFaculty.getSelectedItem();
                teacherDepartment = (String) cbTeacherDepartment.getSelectedItem();
                teacherCourse = (String) cbTeacherCourse.getSelectedItem();

                lbStatusTeacherFirstName.setText("teacherFirstName: " + teacherFirstName);
                lbStatusTeacherLastName.setText("teacherLastName: " + teacherLastName);
                lbStatusTeacherNo.setText("teacherNo: " + teacherNo);
                lbStatusTeacherFaculty.setText("teacherFaculty: " + teacherFaculty);
                lbStatusTeacherDepartment.setText("teacherDepartment: " + teacherDepartment);
                lbStatusTeacherCourse.setText("teacherCourse: " + teacherCourse);


                if (teacherFirstName.isEmpty() || teacherLastName.isEmpty() || teacherNo.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Lütfen tüm alanları doldurunuz!", "Uyarı", JOptionPane.WARNING_MESSAGE);
                }else {
                    // JSON verilerini hazırla
                    JSONArray jsonArray = new JSONArray();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("teacherFirstName", teacherFirstName);
                    jsonObject.put("teacherLastName", teacherLastName);
                    jsonObject.put("teacherNo", teacherNo);
                    jsonObject.put("teacherFaculty", teacherFaculty);
                    jsonObject.put("teacherDepartment", teacherDepartment);
                    jsonObject.put("teacherCourse", teacherCourse);
                    jsonArray.put(jsonObject);

                    fileService.WriteToFile("src/Data", "Teacher.json", jsonArray);

                    id = Integer.parseInt(fileService.getLargestTeacherNo("src/Data/Teacher.json")) + 1;
                    tfTeacherNo.setText(String.valueOf(id));

                    List<String[]> teachers = fileService.getTeachersFromFile("src/Data/Teacher.json");

                    if (teachers != null && !teachers.isEmpty()) {
                        String[] lastTeacher = teachers.get(teachers.size() - 1);
                        tableTeacherModel.addRow(lastTeacher);
                    }
                    tableTeachers.setModel(tableTeacherModel);
                }

            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfFirstName.setText("");
                tfLastName.setText("");
                cbTeacherFaculty.setSelectedIndex(-1);

                cbTeacherDepartment.setSelectedIndex(-1);
                cbTeacherDepartment.removeAllItems();
                cbTeacherDepartment.setEnabled(false);

                cbTeacherCourse.setSelectedIndex(-1);
                cbTeacherCourse.removeAllItems();
                cbTeacherCourse.setEnabled(false);

                lbStatusTeacherFirstName.setText("");
                lbStatusTeacherLastName.setText("");
                lbStatusTeacherNo.setText("");
                lbStatusTeacherFaculty.setText("");
                lbStatusTeacherDepartment.setText("");
                lbStatusTeacherCourse.setText("");
            }
        });

        cbTeacherFaculty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                teacherFaculty = (String) cbTeacherFaculty.getSelectedItem();

                if(teacherFaculty != null){
                    departmentList = fileService.getDepartmentsFromFile("src/Data/"+teacherFaculty+".txt");
                    if (departmentList != null) {
                        cbTeacherDepartment.removeAllItems();
                        for (String department : departmentList) {
                            cbTeacherDepartment.addItem(department);
                        }
                    }
                }
                cbTeacherDepartment.setSelectedIndex(-1);
                cbTeacherCourse.setEnabled(false);
                cbTeacherCourse.removeAllItems();
            }
        });

        cbTeacherDepartment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cbTeacherDepartment.setEnabled(true);
                teacherDepartment = (String) cbTeacherDepartment.getSelectedItem();

                if(teacherDepartment != null){
                    courseList = fileService.getCourseNamesByDepartment("src/Data/Course.json",teacherDepartment);
                    if (courseList != null) {
                        cbTeacherCourse.removeAllItems();
                        for (String course : courseList) {
                            cbTeacherCourse.addItem(course);
                        }
                    }
                }
                cbTeacherCourse.setEnabled(true);
            }
        });

        tableTeacherModel.addColumn("teacherNo");
        tableTeacherModel.addColumn("teacherFirstName");
        tableTeacherModel.addColumn("teacherLastName");
        tableTeacherModel.addColumn("teacherFaculty");
        tableTeacherModel.addColumn("teacherDepartment");
        tableTeacherModel.addColumn("teacherCourse");
        tableTeacherModel.addRow(new Object[]{"Teacher No", "Teacher First Name", "Teacher Last Name", "Teacher Faculty", "Teacher Department", "Teacher Course"});

        List<String[]> teachers = fileService.getTeachersFromFile("src/Data/Teacher.json");

        if (teachers != null) {
            for (String[] teacher : teachers) {
                tableTeacherModel.addRow(teacher);
            }
        }

        tableTeachers.setModel(tableTeacherModel);

        tfSearchTeacher.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String input = tfSearchTeacher.getText().toLowerCase().trim();
                TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(tableTeacherModel);
                tableTeachers.setRowSorter(rowSorter);

                rowSorter.setRowFilter(RowFilter.regexFilter("(?i).*" + input + ".*"));
            }
        });

        setContentPane(TeacherForm);
        setTitle("Teacher Form");
        setMinimumSize(new Dimension(1100,500));
        setLocation(820,10);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setVisible(true);
    }
/*
    public static void main(String[] args) {
        TeacherForm teacherForm = new TeacherForm();
    }
 */
}

