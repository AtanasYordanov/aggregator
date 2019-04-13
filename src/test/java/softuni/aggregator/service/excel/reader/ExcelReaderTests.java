package softuni.aggregator.service.excel.reader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import softuni.aggregator.service.excel.reader.imports.ImportType;
import softuni.aggregator.service.excel.reader.model.ExcelImportDto;

import java.util.List;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ExcelReaderTests {

    private static final String TEST_FILE_RELATIVE_PATH = "/src/test/java/softuni/aggregator/resources/EmployeesSample.xlsx";
    private static final int LINES_IN_EXCEL_FILE = 99;

    private ExcelReader excelReader;

    @Before
    public void init() {
        excelReader = new ExcelReaderImpl();
    }

    @Test
    public void readExcel_validFile_shouldMapExcelDataToDto() {
        String projectPath = System.getProperty("user.dir");
        String fullPath = projectPath + TEST_FILE_RELATIVE_PATH;
        List<ExcelImportDto> excelImportDtos = excelReader.readExcel(fullPath, ImportType.EMPLOYEES);

        Assert.assertEquals(LINES_IN_EXCEL_FILE, excelImportDtos.size());
    }

    @Test
    public void readExcel_invalidFile_shouldMapExcelDataToDto() {
        String wrongPath = "wrongFilePath.xlsx";
        List<ExcelImportDto> excelImportDtos = excelReader.readExcel(wrongPath, ImportType.EMPLOYEES);

        Assert.assertEquals(0, excelImportDtos.size());
    }
}