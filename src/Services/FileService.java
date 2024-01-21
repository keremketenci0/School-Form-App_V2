package Services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Path;

public class FileService implements IFileService {
    @Override
    public void CreateFile(String Path, String FileName) {
        File directory = new File(Path);

        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Klasör oluşturuldu: " + directory.getAbsolutePath());
            } else {
                System.out.println("Klasör oluşturma başarısız!");
            }
        } else {
            System.out.println("Klasör zaten mevcut: " + directory.getAbsolutePath());
        }

        File file = new File(Path, FileName);

        try {
            if (file.createNewFile()) {
                System.out.println("Metin dosyası oluşturuldu: " + file.getName());
            } else {
                System.out.println("Metin dosyası zaten mevcut.");
            }
        } catch (IOException e) {
            System.out.println("Dosya oluşturma hatası: " + e.getMessage());
        }
    }

    @Override
    public void WriteToFile(String path, String FileName, JSONArray jsonObject) {
        try {
            CreateFile(path, FileName);

            File file = new File(path, FileName);

            String fileContent;
            if (file.exists()) {
                fileContent = new String(Files.readAllBytes(Paths.get(file.getPath())));
            } else {
                fileContent = "";
            }

            JSONArray jsonArray;
            if (fileContent.isEmpty()) {
                jsonArray = new JSONArray();
            } else {
                jsonArray = new JSONArray(fileContent);
            }

            jsonArray.put(jsonObject);

            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(jsonArray.toString(2));
                System.out.println("JSON dosyasına veri eklendi.");
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Hata: JSON dosyasına yazarken bir sorun oluştu.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Hata: Dosya işlemlerinde bir sorun oluştu.");
        }
    }

    @Override
    public List<String[]> getCoursesFromFile(String filePath) {
        List<String[]> courseList = new ArrayList<>();

        try {
            Path path = Paths.get(filePath);

            // Check if the file exists and is not empty
            if (Files.exists(path) && Files.size(path) > 0) {
                String fileContent = new String(Files.readAllBytes(path));
                JSONArray outerArray = new JSONArray(fileContent);

                for (int i = 0; i < outerArray.length(); i++) {
                    JSONArray innerArray = outerArray.getJSONArray(i);

                    for (int j = 0; j < innerArray.length(); j++) {
                        JSONObject jsonObject = innerArray.getJSONObject(j);

                        String courseCode = jsonObject.getString("courseCode");
                        String courseName = jsonObject.getString("courseName");
                        String courseFaculty = jsonObject.getString("courseFaculty");
                        String courseDepartment = jsonObject.getString("courseDepartment");

                        courseList.add(new String[]{courseName, courseDepartment, courseCode, courseFaculty});
                    }
                }
            } else {
                System.out.println("File is empty or does not exist.");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
        }

        return courseList;
    }

    @Override
    public List<String[]> getStudentsFromFile(String filePath) {
        List<String[]> studentList = new ArrayList<>();

        try {
            Path path = Paths.get(filePath);

            // Check if the file exists and is not empty
            if (Files.exists(path) && Files.size(path) > 0) {
                String fileContent = new String(Files.readAllBytes(path));
                JSONArray outerArray = new JSONArray(fileContent);

                for (int i = 0; i < outerArray.length(); i++) {
                    JSONArray innerArray = outerArray.getJSONArray(i);

                    for (int j = 0; j < innerArray.length(); j++) {
                        JSONObject jsonObject = innerArray.getJSONObject(j);

                        String studentNo = jsonObject.getString("studentNo");
                        String studentFirstName = jsonObject.getString("studentFirstName");
                        String studentLastName = jsonObject.getString("studentLastName");
                        String StudentFaculty = jsonObject.getString("StudentFaculty");
                        String studentDepartment = jsonObject.getString("studentDepartment");
                        String studentCourse = jsonObject.getString("studentCourse");

                        studentList.add(new String[]{studentNo, studentFirstName, studentLastName, StudentFaculty,studentDepartment, studentCourse });
                    }
                }
            } else {
                System.out.println("File is empty or does not exist.");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
        }

        return studentList;
    }

    @Override
    public List<String[]> getTeachersFromFile(String filePath) {
        List<String[]> teacherList = new ArrayList<>();

        try {
            Path path = Paths.get(filePath);

            // Check if the file exists and is not empty
            if (Files.exists(path) && Files.size(path) > 0) {
                String fileContent = new String(Files.readAllBytes(path));
                JSONArray outerArray = new JSONArray(fileContent);

                for (int i = 0; i < outerArray.length(); i++) {
                    JSONArray innerArray = outerArray.getJSONArray(i);

                    for (int j = 0; j < innerArray.length(); j++) {
                        JSONObject jsonObject = innerArray.getJSONObject(j);

                        String teacherNo = jsonObject.getString("teacherNo");
                        String teacherFirstName = jsonObject.getString("teacherFirstName");
                        String teacherLastName = jsonObject.getString("teacherLastName");
                        String teacherFaculty = jsonObject.getString("teacherFaculty");
                        String teacherDepartment = jsonObject.getString("teacherDepartment");
                        String teacherCourse = jsonObject.getString("teacherCourse");

                        teacherList.add(new String[]{teacherNo, teacherFirstName, teacherLastName, teacherFaculty,teacherDepartment, teacherCourse });
                    }
                }
            } else {
                System.out.println("File is empty or does not exist.");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
        }

        return teacherList;
    }

    @Override
    public List<String> getDepartmentsFromFile(String filePath) {
        List<String> departmentList = new ArrayList<>();

        try {
            File file = new File(filePath);
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] departments = line.split("\n");

                for (String department : departments) {
                    departmentList.add(department);
                }
            }

            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            return null;
        }

        return departmentList;
    }

    @Override
    public List<String> getCourseNamesByDepartment(String filePath, String department) {
        List<String> courseNames = new ArrayList<>();

        try {
            // Dosyayı oku ve içeriğini bir String olarak al
            String content = Files.readString(Paths.get(filePath));

            // JSON dizisini oluştur
            JSONArray jsonArray = new JSONArray(content);

            // Her bir JSON dizisindeki kursları işle
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray courseArray = jsonArray.getJSONArray(i);

                for (int j = 0; j < courseArray.length(); j++) {
                    JSONObject courseObject = courseArray.getJSONObject(j);
                    String courseDepartment = courseObject.getString("courseDepartment");

                    // Belirli bir departmana ait kursları filtrele
                    if (courseDepartment.equals(department)) {
                        String courseName = courseObject.getString("courseName");
                        courseNames.add(courseName);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return courseNames;
    }

    @Override
    public String getLargestStudentNo(String filePath) {
        List<Integer> studentNumbers = new ArrayList<>();

        try {
            String content = Files.readString(Paths.get(filePath));

            if(content.isEmpty()){
                System.out.println("File is empty.");
                return "0";
            } else {
                JSONArray jsonArray = new JSONArray(content);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONArray studentArray = jsonArray.getJSONArray(i);

                    for (int j = 0; j < studentArray.length(); j++) {
                        JSONObject studentObject = studentArray.getJSONObject(j);
                        String studentNo = studentObject.getString("studentNo");
                        studentNumbers.add(Integer.parseInt(studentNo));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int largest = Collections.max(studentNumbers); // Get the largest number
        return String.valueOf(largest);
    }

    @Override
    public String getLargestCourseCode(String filePath) {
        List<Integer> courseCodes = new ArrayList<>();

        try {
            String content = Files.readString(Paths.get(filePath));

            if(content.isEmpty()){
                return "0";
            } else {
                JSONArray jsonArray = new JSONArray(content);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONArray courseArray = jsonArray.getJSONArray(i);

                    for (int j = 0; j < courseArray.length(); j++) {
                        JSONObject courseObject = courseArray.getJSONObject(j);
                        String courseCode = courseObject.getString("courseCode");
                        courseCodes.add(Integer.parseInt(courseCode));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (courseCodes.isEmpty()) {
            return "1"; // If no course codes were found, return 1
        }

        int largest = Collections.max(courseCodes); // Get the largest code
        return String.valueOf(largest);
    }

    @Override
    public String getLargestTeacherNo(String filePath) {
        List<Integer> teacherNumbers = new ArrayList<>();

        try {
            String content = Files.readString(Paths.get(filePath));

            if(content.isEmpty()){
                return "0";
            } else {
                JSONArray jsonArray = new JSONArray(content);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONArray courseArray = jsonArray.getJSONArray(i);

                    for (int j = 0; j < courseArray.length(); j++) {
                        JSONObject courseObject = courseArray.getJSONObject(j);
                        String courseCode = courseObject.getString("teacherNo");
                        teacherNumbers.add(Integer.parseInt(courseCode));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (teacherNumbers.isEmpty()) {
            return "1"; // If no course codes were found, return 1
        }

        int largest = Collections.max(teacherNumbers); // Get the largest code
        return String.valueOf(largest);
    }
}