package softuni.aggregator.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import softuni.aggregator.domain.repository.UserRepository;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTests {

    @MockBean
    private UserRepository mockUserRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getAllUsers_admin_shouldReturnCorrectView() throws Exception {
        mockMvc.perform(get("/admin/users"))
                .andExpect(view().name("users"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void getAllUsers_moderator_shouldBeForbidden() throws Exception {
        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void getAllUsers_user_shouldBeForbidden() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void getAllUsers_noUser_shouldBeForbidden() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getUsersPage_admin_shouldReturnCorrectView() throws Exception {
        Mockito.when(mockUserRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/admin/users/page"))
                .andExpect(status().isOk());

        Mockito.verify(mockUserRepository).findAll((Pageable) Mockito.any());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void getUsersPage_moderator_shouldBeForbidden() throws Exception {
        mockMvc.perform(get("/admin/users/page"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void getUsersPage_user_shouldBeForbidden() throws Exception {
        mockMvc.perform(get("/admin/users/page"))
                .andExpect(status().isForbidden());
    }
}
