package softuni.aggregator.service;

import org.springframework.data.domain.Pageable;
import softuni.aggregator.domain.model.vo.EmployeeListVO;
import softuni.aggregator.service.excel.writer.model.ExcelExportDto;

import java.util.List;

public interface EmployeeService {

    List<ExcelExportDto> getEmployeesForExport();

    long getTotalEmployeesCount();

    List<EmployeeListVO> getEmployeesPage(Pageable pageable);
}
