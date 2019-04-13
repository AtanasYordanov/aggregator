package softuni.aggregator.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import softuni.aggregator.domain.entities.Employee;
import softuni.aggregator.domain.repository.EmployeeRepository;
import softuni.aggregator.domain.repository.MainIndustryRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeesControllerTests {

    @MockBean
    private EmployeeRepository mockEmployeeRepository;

    @MockBean
    private MainIndustryRepository mockMainIndustryRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void employees_loggedUser_shouldReturnCorrectView() throws Exception {
        mockMvc.perform(get("/employees/catalog"))
                .andExpect(view().name("employees"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void getEmployeesData_loggedUser_shouldReturnData() throws Exception {
        Mockito.when(mockMainIndustryRepository.findAll()).thenReturn(List.of());
        Mockito.when(mockEmployeeRepository.getFilteredEmployeesPage(
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()
        )).thenReturn(List.of());

        mockMvc.perform(get("/employees/data"))
                .andExpect(status().isOk());

        Mockito.verify(mockMainIndustryRepository).findAll();
        Mockito.verify(mockEmployeeRepository).getFilteredEmployeesPage(
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()
        );
    }

    @Test
    @WithMockUser
    public void getEmployeeDetails_loggedUser_shouldReturnCorrectView() throws Exception {
        Mockito.when(mockEmployeeRepository.findByIdEager(Mockito.any()))
                .thenReturn(Optional.of(new Employee()));

        mockMvc.perform(get("/employees/23"))
                .andExpect(view().name("employee-details"))
                .andExpect(status().isOk());

        Mockito.verify(mockEmployeeRepository).findByIdEager(Mockito.any());
    }

    @Test
    @WithMockUser
    public void deleteEmployee_user_shouldBeForbidden() throws Exception {
        mockMvc.perform(delete("/employees/delete/3"));

        Mockito.verify(mockEmployeeRepository, Mockito.never()).deleteById(Mockito.any());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void deleteEmployee_moderator_shouldDeleteEmployee() throws Exception {
        mockMvc.perform(delete("/employees/delete/3"))
                .andExpect(status().isOk());

        Mockito.verify(mockEmployeeRepository).deleteById(Mockito.any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteEmployee_admin_shouldDeleteEmployee() throws Exception {
        mockMvc.perform(delete("/employees/delete/3"))
                .andExpect(status().isOk());

        Mockito.verify(mockEmployeeRepository).deleteById(Mockito.any());
    }
}
