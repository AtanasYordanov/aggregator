package softuni.aggregator.domain.model.vo.page;

import lombok.Getter;
import lombok.Setter;
import softuni.aggregator.domain.model.vo.UserListVO;

import java.util.List;

@Getter
@Setter
public class UsersPageVO extends BasePageVO {

    private List<UserListVO> users;
}
