package softuni.aggregator.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import softuni.aggregator.domain.repository.CompanyRepository;
import softuni.aggregator.domain.repository.EmployeeRepository;
import softuni.aggregator.domain.repository.ImportRepository;
import softuni.aggregator.service.excel.reader.ExcelReader;
import softuni.aggregator.utils.TestUtils;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ImportControllerTests {

    @MockBean
    private ImportRepository mockImportRepository;

    @MockBean
    private EmployeeRepository mockEmployeeRepository;

    @MockBean
    private CompanyRepository mockCompanyRepository;

    @MockBean
    private ExcelReader mockExcelReader;

    @MockBean
    private ServletContext mockServletContext;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void imports_user_shouldBeForbidden() throws Exception {
        mockMvc.perform(get("/imports"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void imports_moderator_shouldReturnCorrectView() throws Exception {
        mockMvc.perform(get("/imports"))
                .andExpect(view().name("imports"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void imports_admin_shouldReturnCorrectView() throws Exception {
        mockMvc.perform(get("/imports"))
                .andExpect(view().name("imports"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void getImportsPage_moderator_shouldReturnCorrectView() throws Exception {
        Mockito.when(mockImportRepository.findAllByUser(Mockito.any(), Mockito.any()))
                .thenReturn(List.of());

        mockMvc.perform(get("/imports/page")
                .with(user(TestUtils.getLoggedModerator())))
                .andExpect(status().isOk());

        Mockito.verify(mockImportRepository).findAllByUser(Mockito.any(), Mockito.any());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void getImportTypes_moderator_shouldReturnCorrectView() throws Exception {
        mockMvc.perform(post("/imports/types")
                .with(user(TestUtils.getLoggedModerator())))
                .andExpect(status().isOk());
    }
}
