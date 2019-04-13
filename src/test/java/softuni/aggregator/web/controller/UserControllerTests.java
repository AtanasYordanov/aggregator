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
import softuni.aggregator.domain.repository.UserRepository;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @MockBean
    private UserRepository mockUserRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void login_noUser_shouldReturnCorrectView() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(view().name("login"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void login_loggedUser_shouldBeForbidden() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void register_noUser_shouldReturnCorrectView() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(view().name("register"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void register_loggedUser_shouldBeForbidden() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void processRegister_validData_shouldCreateNewUser() throws Exception {
        mockMvc.perform(post("/register")
                .with(csrf())
                .param("email", "valid@valid.bg")
                .param("firstName", "Pesho")
                .param("lastName", "Goshov")
                .param("password", "pass123")
                .param("confirmPassword", "pass123")
        )
                .andExpect(view().name("redirect:/login"))
                .andExpect(status().isFound());

        Mockito.verify(mockUserRepository).save(Mockito.any());
    }

    @Test
    public void processRegister_invalidData_shouldNotCreateUser() throws Exception {
        mockMvc.perform(post("/register")
                .with(csrf())
                .param("email", "invalid")
                .param("firstName", "Pesho")
                .param("lastName", "Goshov")
                .param("password", "pass123")
                .param("confirmPassword", "pa3")
        )
                .andExpect(view().name("register"));

        Mockito.verify(mockUserRepository, Mockito.never()).save(Mockito.any());
    }
}
