import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

@SpringBootApplication
public class ValueGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ValueGeneratorApplication.class, args);
    }
}

@Component
class ValueGenerator implements CommandLineRunner {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${excel.file.path}")
    private String excelFilePath;

    @Override
    public void run(String... args) throws Exception {
        generateValues();
    }

    public void generateValues() {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(excelFilePath))) {
            Sheet axisSheet = workbook.getSheet("AXIS");
            double minValue = axisSheet.getRow(1).getCell(1).getNumericCellValue();
            double maxValue = axisSheet.getRow(1).getCell(2).getNumericCellValue();

            try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
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
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
