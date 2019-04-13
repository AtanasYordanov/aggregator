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
import softuni.aggregator.domain.repository.ExportRepository;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ExportControllerTests {

    @MockBean
    private ExportRepository mockExportRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void exports_allUsers_shouldReturnCorrectView() throws Exception {
        mockMvc.perform(get("/exports"))
                .andExpect(view().name("exports"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void getExportsPage_loggedUser_shouldReturnData() throws Exception {
        Mockito.when(mockExportRepository.findAllByUser(Mockito.any(), Mockito.any()))
                .thenReturn(List.of());
        Mockito.when(mockExportRepository.countByUser(Mockito.any()))
                .thenReturn(5L);

        mockMvc.perform(get("/exports/page"))
                .andExpect(content().json("{ exports: [], totalItemsCount: 5 }"))
                .andExpect(status().isOk());
    }
}
