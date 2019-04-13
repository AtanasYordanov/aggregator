package softuni.aggregator.service.excel.writer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import softuni.aggregator.service.excel.writer.exports.ExportType;
import softuni.aggregator.service.excel.writer.model.CompanyExportDto;
import softuni.aggregator.service.excel.writer.model.EmployeeExportDto;
import softuni.aggregator.service.excel.writer.model.EmployeeWithCompanyExportDto;
import softuni.aggregator.service.excel.writer.model.ExcelExportDto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ExcelWriterTests {

    private File testFile;

    private ExcelWriter excelWriter;

    @Before
    public void init() {
        excelWriter = new ExcelWriterImpl();
    }

    @After
    public void deleteCreatedFile() throws IOException {
        if (!testFile.delete()) {
            throw new IOException("Failed to delete test file");
        }
    }

    @Test
    public void readExcel_employeesData_shouldMapExcelDataToDto() {
        List<ExcelExportDto> employeesExportData = buildTestEmployeesData();

        testFile = excelWriter.writeExcel(employeesExportData, ExportType.EMPLOYEES);

        Assert.assertNotNull(testFile);
    }

    @Test
    public void readExcel_xingCompaniesData_shouldMapExcelDataToDto() {
        List<ExcelExportDto> employeesExportData = buildTestCompaniesData();

        testFile = excelWriter.writeExcel(employeesExportData, ExportType.COMPANIES);

        Assert.assertNotNull(testFile);
    }

    @Test
    public void readExcel_orbisCompaniesData_shouldMapExcelDataToDto() {
        List<ExcelExportDto> employeesExportData = buildTestMixedData();

        testFile = excelWriter.writeExcel(employeesExportData, ExportType.MIXED);

        Assert.assertNotNull(testFile);
    }

    private List<ExcelExportDto> buildTestEmployeesData() {
        List<ExcelExportDto> excelImportDtos = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            EmployeeExportDto employeeExportDto = createEmployeeDto();
            excelImportDtos.add(employeeExportDto);
        }
        return excelImportDtos;
    }

    private List<ExcelExportDto> buildTestCompaniesData() {
        List<ExcelExportDto> companyExportDtos = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            CompanyExportDto companyExportDto = createCompanyDto();
            companyExportDtos.add(companyExportDto);
        }
        return companyExportDtos;
    }

    private List<ExcelExportDto> buildTestMixedData() {
        List<ExcelExportDto> companyExportDtos = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            EmployeeWithCompanyExportDto companyExportDto = new EmployeeWithCompanyExportDto();
            EmployeeExportDto employeeDto = createEmployeeDto();
            CompanyExportDto companyDto = createCompanyDto();
            companyExportDto.setEmployeeExportDto(employeeDto);
            companyExportDto.setCompanyExportDto(companyDto);
            companyExportDtos.add(companyExportDto);
        }
        return companyExportDtos;
    }

    private CompanyExportDto createCompanyDto() {
        CompanyExportDto companyExportDto = new CompanyExportDto();
        companyExportDto.setAddress("address");
        companyExportDto.setName("some name");
        companyExportDto.setCity("Sofia");
        companyExportDto.setCountry("Bulgaria");
        companyExportDto.setYearFound(2010);
        return companyExportDto;
    }

    private EmployeeExportDto createEmployeeDto() {
        EmployeeExportDto employeeExportDto = new EmployeeExportDto();
        employeeExportDto.setFullName("Joro");
        employeeExportDto.setEmail("testEmail");
        employeeExportDto.setHunterIoScore(30);
        employeeExportDto.setPosition("CEO");
        return employeeExportDto;
    }
}