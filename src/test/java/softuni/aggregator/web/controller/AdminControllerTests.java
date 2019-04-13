package softuni.aggregator.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import softuni.aggregator.domain.entities.Export;
import softuni.aggregator.domain.entities.Import;
import softuni.aggregator.domain.entities.Role;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.enums.UserRole;
import softuni.aggregator.domain.repository.ExportRepository;
import softuni.aggregator.domain.repository.ImportRepository;
import softuni.aggregator.domain.repository.RoleRepository;
import softuni.aggregator.domain.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTests {

    @MockBean
    private UserRepository mockUserRepository;

    @MockBean
    private RoleRepository mockRoleRepository;

    @MockBean
    private ExportRepository mockExportRepository;

    @MockBean
    private ImportRepository mockImportRepository;

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

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getUserDetails_admin_shouldGetUserAndReturnCorrectView() throws Exception {
        User user = new User();
        user.setAuthorities(Set.of(new Role(UserRole.ROLE_USER.toString())));
        user.setImports(List.of());
        user.setExports(List.of());

        Mockito.when(mockUserRepository.findById(5L))
                .thenReturn(Optional.of(user));

        mockMvc.perform(get("/admin/users/5")
                .param("id", "5"))
                .andExpect(view().name("user-details"))
                .andExpect(status().isOk());

        Mockito.verify(mockUserRepository).findById(5L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void changeUserRole_admin_shouldChangeRole() throws Exception {
        User user = new User();
        user.setAuthorities(Set.of(new Role(UserRole.ROLE_USER.toString())));
        user.setImports(List.of());
        user.setExports(List.of());

        Mockito.when(mockUserRepository.findById(5L))
                .thenReturn(Optional.of(user));
        Mockito.when(mockRoleRepository.findByName(Mockito.any()))
                .thenReturn(Optional.of(new Role()));


        mockMvc.perform(post("/admin/roles/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userId\": \"5\", \"roleName\": \"USER\" }"))
                .andExpect(status().isOk());

        Mockito.verify(mockUserRepository).save(Mockito.any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void allExports_admin_shouldReturnCorrectView() throws Exception {
        mockMvc.perform(get("/admin/exports"))
                .andExpect(view().name("exports"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getAllExports_admin_shouldGetExports() throws Exception {
        Page<Export> testPage = new PageImpl<>(List.of());

        Mockito.when(mockExportRepository.findAll((Pageable) Mockito.any())).thenReturn(testPage);
        Mockito.when(mockExportRepository.count()).thenReturn(5L);

        mockMvc.perform(get("/admin/exports/page"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void allImports_admin_shouldReturnCorrectView() throws Exception {
        mockMvc.perform(get("/admin/imports"))
                .andExpect(view().name("imports"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getAllImports_admin_shouldGetImports() throws Exception {
        Page<Import> testPage = new PageImpl<>(List.of());

        Mockito.when(mockImportRepository.findAll((Pageable) Mockito.any())).thenReturn(testPage);
        Mockito.when(mockImportRepository.count()).thenReturn(5L);

        mockMvc.perform(get("/admin/imports/page"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void suspendUser_admin_shouldUpdateUserStatus() throws Exception {
        Mockito.when(mockUserRepository.findById(5L))
                .thenReturn(Optional.of(new User()));

        mockMvc.perform(put("/admin/suspend/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(mockUserRepository).save(Mockito.any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void activateUser_admin_shouldUpdateUserStatus() throws Exception {
        Mockito.when(mockUserRepository.findById(5L))
                .thenReturn(Optional.of(new User()));

        mockMvc.perform(put("/admin/activate/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(mockUserRepository).save(Mockito.any());
    }
}
