package org.chapeullah.chupapoapi.security;

import com.jayway.jsonpath.JsonPath;
import org.chapeullah.chupapoapi.iam.access.repository.RoleRepository;
import org.chapeullah.chupapoapi.iam.account.model.Account;
import org.chapeullah.chupapoapi.iam.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SessionAuthenticationIntegrationTests {

    private static final String USERNAME = "viewer1";
    private static final String PASSWORD = "viewer-password-123";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Account viewer;

    @BeforeEach
    void setUp() {
        accountRepository.deleteAll();
        var viewerRole = roleRepository.findByName("VIEWER").orElseThrow();
        viewer = accountRepository.save(new Account(
                USERNAME,
                passwordEncoder.encode(PASSWORD),
                viewerRole));
    }

    @Test
    void sessionLoginUsesCsrfRotatesSessionAndChecksRolePermissions() throws Exception {
        MvcResult initialCsrf = mockMvc.perform(get("/api/auth/csrf"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.headerName").value("X-CSRF-TOKEN"))
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andReturn();

        MockHttpSession session = (MockHttpSession) initialCsrf.getRequest().getSession(false);
        assertThat(session).isNotNull();
        String sessionIdBeforeLogin = session.getId();
        String csrfToken = JsonPath.read(
                initialCsrf.getResponse().getContentAsString(),
                "$.token");

        MvcResult login = mockMvc.perform(post("/api/auth/login")
                        .session(session)
                        .header("X-CSRF-TOKEN", csrfToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "viewer1",
                                  "password": "viewer-password-123"
                                }
                                """))
                .andExpect(status().isNoContent())
                .andReturn();

        session = (MockHttpSession) login.getRequest().getSession(false);
        assertThat(session).isNotNull();
        assertThat(session.getId()).isNotEqualTo(sessionIdBeforeLogin);

        mockMvc.perform(get("/api/auth/me").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.authorities").value(
                        org.hamcrest.Matchers.hasItems(
                                "ROLE_VIEWER",
                                "ACCOUNTS_READ",
                                "ROLES_READ")))
                .andExpect(jsonPath("$.authorities").value(
                        org.hamcrest.Matchers.not(
                                org.hamcrest.Matchers.hasItem("ACCOUNTS_CREATE"))));

        mockMvc.perform(get("/api/accounts/{id}", viewer.getId()).session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(USERNAME));

        MvcResult refreshedCsrf = mockMvc.perform(get("/api/auth/csrf").session(session))
                .andExpect(status().isOk())
                .andReturn();
        String refreshedToken = JsonPath.read(
                refreshedCsrf.getResponse().getContentAsString(),
                "$.token");

        mockMvc.perform(post("/api/accounts")
                        .session(session)
                        .header("X-CSRF-TOKEN", refreshedToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "another_user",
                                  "password": "another-password-123",
                                  "roleId": 1
                                }
                                """))
                .andExpect(status().isForbidden());

        mockMvc.perform(post("/api/auth/logout")
                        .session(session)
                        .header("X-CSRF-TOKEN", refreshedToken))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void loginRequiresCsrfAndDoesNotRevealWhyAuthenticationFailed() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"username":"viewer1","password":"viewer-password-123"}
                                """))
                .andExpect(status().isForbidden());

        MvcResult csrf = mockMvc.perform(get("/api/auth/csrf"))
                .andExpect(status().isOk())
                .andReturn();
        MockHttpSession session = (MockHttpSession) csrf.getRequest().getSession(false);
        String token = JsonPath.read(csrf.getResponse().getContentAsString(), "$.token");

        mockMvc.perform(post("/api/auth/login")
                        .session(session)
                        .header("X-CSRF-TOKEN", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"username":"viewer1","password":"wrong-password"}
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.detail").value("Invalid username or password"));
    }
}
