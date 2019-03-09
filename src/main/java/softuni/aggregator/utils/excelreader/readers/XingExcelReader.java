package softuni.aggregator.utils.excelreader.readers;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import softuni.aggregator.utils.excelreader.BaseExcelReader;
import softuni.aggregator.utils.excelreader.columns.XingColumn;
import softuni.aggregator.utils.excelreader.model.XingCompanyDto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class XingExcelReader extends BaseExcelReader<XingCompanyDto> {

    @Override
    public List<XingCompanyDto> readExcel(String path) {
        try (FileInputStream inputStream = new FileInputStream(new File(path));
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            Map<Integer, XingColumn> columns = Arrays.stream(XingColumn.values())
                    .collect(Collectors.toMap(Enum::ordinal, c -> c));

            List<XingCompanyDto> companies = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                XingCompanyDto company = new XingCompanyDto();

                parseRow(row, company, columns);
                if (company.getWebsite() != null && !company.getWebsite().isBlank()) {
                    companies.add(company);
                }
            }

            return companies;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
