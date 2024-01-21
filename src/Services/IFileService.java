package Services;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface IFileService
{
    public void CreateFile(String Path,String FileName);
    public void WriteToFile(String path, String FileName, JSONArray jsonObject);
    public List<String[]> getCoursesFromFile(String filePath);
    public List<String[]> getStudentsFromFile(String filePath);
    public List<String[]> getTeachersFromFile(String filePath);
    public List<String> getDepartmentsFromFile(String filePath);
    public List<String> getCourseNamesByDepartment(String filePath, String department);
    public String getLargestStudentNo(String filePath);
    public String getLargestCourseCode(String filePath);
    public String getLargestTeacherNo(String filePath);
}
