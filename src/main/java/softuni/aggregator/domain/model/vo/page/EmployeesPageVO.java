package softuni.aggregator.domain.model.vo.page;

import lombok.Getter;
import lombok.Setter;
import softuni.aggregator.domain.model.vo.EmployeeListVO;

import java.util.List;

@Getter
@Setter
public class EmployeesPageVO extends BasePageVO {

    private List<EmployeeListVO> employees;
}
