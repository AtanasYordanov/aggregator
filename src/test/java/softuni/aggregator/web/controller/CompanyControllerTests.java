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
import softuni.aggregator.domain.entities.Company;
import softuni.aggregator.domain.repository.CompanyRepository;
import softuni.aggregator.domain.repository.MainIndustryRepository;
import softuni.aggregator.domain.repository.SubIndustryRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTests {

    @MockBean
    private CompanyRepository mockCompanyRepository;

    @MockBean
    private MainIndustryRepository mockMainIndustryRepository;

    @MockBean
    private SubIndustryRepository mockSubIndustryRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void companies_loggedUser_shouldReturnCorrectView() throws Exception {
        mockMvc.perform(get("/companies/catalog"))
                .andExpect(view().name("companies"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void getCompaniesData_loggedUser_shouldReturnData() throws Exception {
        Mockito.when(mockMainIndustryRepository.findAll()).thenReturn(List.of());
        Mockito.when(mockSubIndustryRepository.findAll()).thenReturn(List.of());
        Mockito.when(mockCompanyRepository.getFilteredCompaniesPage(
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()
        )).thenReturn(List.of());

        mockMvc.perform(get("/companies/data"))
                .andExpect(status().isOk());

        Mockito.verify(mockMainIndustryRepository).findAll();
        Mockito.verify(mockSubIndustryRepository).findAll();
        Mockito.verify(mockCompanyRepository).getFilteredCompaniesPage(
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()
        );
    }

    @Test
    @WithMockUser
    public void getCompanyDetails_loggedUser_shouldReturnCorrectView() throws Exception {
        Mockito.when(mockCompanyRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(new Company()));

        mockMvc.perform(get("/companies/23"))
                .andExpect(view().name("company-details"))
                .andExpect(status().isOk());

        Mockito.verify(mockCompanyRepository).findById(Mockito.any());
    }

    @Test
    @WithMockUser
    public void deleteCompany_user_shouldBeForbidden() throws Exception {
        Company testCompany = new Company();
        testCompany.setEmployees(List.of());
        Mockito.when(mockCompanyRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(testCompany));

        mockMvc.perform(delete("/companies/delete/3"));

        Mockito.verify(mockCompanyRepository, Mockito.never()).findById(Mockito.any());
        Mockito.verify(mockCompanyRepository, Mockito.never()).delete(testCompany);
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void deleteCompany_moderator_shouldDeleteCompany() throws Exception {
        Company testCompany = new Company();
        testCompany.setEmployees(List.of());
        Mockito.when(mockCompanyRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(testCompany));

        mockMvc.perform(delete("/companies/delete/3"))
                .andExpect(status().isOk());

        Mockito.verify(mockCompanyRepository).findById(Mockito.any());
        Mockito.verify(mockCompanyRepository).delete(testCompany);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteCompany_admin_shouldDeleteCompany() throws Exception {
        Company testCompany = new Company();
        testCompany.setEmployees(List.of());
        Mockito.when(mockCompanyRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(testCompany));

        mockMvc.perform(delete("/companies/delete/3"))
                .andExpect(status().isOk());

        Mockito.verify(mockCompanyRepository).findById(Mockito.any());
        Mockito.verify(mockCompanyRepository).delete(testCompany);
    }
}
