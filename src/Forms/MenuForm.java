package Forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuForm extends JFrame{
    private JPanel MenuForm;
    private JButton btnCourseForm;
    private JButton btnStudentForm;
    private JButton btnTeacherForm;

    public MenuForm() {
        btnCourseForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CourseForm courseForm = new CourseForm();
                courseForm.setVisible(true);
                //dispose();
            }
        });
        btnStudentForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentForm studentForm = new StudentForm();
                studentForm.setVisible(true);
                //dispose();
            }
        });
        btnTeacherForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TeacherForm teacherForm = new TeacherForm();
                teacherForm.setVisible(true);
                //dispose();
            }
        });

        setContentPane(MenuForm);
        setTitle("Menu Form");
        setMinimumSize(new Dimension(200,300));
        setLocation(10,10);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
/*
    public static void main(String[] args){
        MenuForm menuForm = new MenuForm();
    }
 */
}
