import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ValueGenerator {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/your_db";
    private static final String USER = "your_user";
    private static final String PASS = "your_pass";

    public static void main(String[] args) {
        try {
            // Load data from Excel
            Workbook workbook = new XSSFWorkbook(new FileInputStream("path_to_your_file.xlsx"));
            Sheet axisSheet = workbook.getSheet("AXIS");

            // Assume axis X values are in the first row
            double minValue = axisSheet.getRow(1).getCell(1).getNumericCellValue();
            double maxValue = axisSheet.getRow(1).getCell(2).getNumericCellValue();

            // Database connection
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            String insertSQL = "INSERT INTO MachineValues (machine_id, axis, timestamp, value) VALUES (?, ?, ?, ?)";

            Random random = new Random();
            int numMachines = 20;
            String[] axes = {"X", "Y", "Z", "A", "C"};
            int updateInterval = 5; // in seconds

            for (int machineId = 1; machineId <= numMachines; machineId++) {
                for (String axis : axes) {
                    long timestamp = System.currentTimeMillis();
                    for (int i = 0; i < 180; i++) { // 15 minutes
                        double value = minValue + (maxValue - minValue) * random.nextDouble();
                        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                            pstmt.setInt(1, machineId);
                            pstmt.setString(2, axis);
                            pstmt.setTimestamp(3, new java.sql.Timestamp(timestamp + (i * updateInterval * 1000)));
                            pstmt.setDouble(4, value);
                            pstmt.executeUpdate();
                        }
                    }
                }
            }

            connection.close();
            workbook.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}

