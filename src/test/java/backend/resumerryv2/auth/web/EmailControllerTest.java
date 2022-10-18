package backend.resumerryv2.auth.web;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import backend.resumerryv2.security.config.SecurityConfiguration;
import backend.resumerryv2.security.config.WebConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@WebMvcTest(EmailControllerTest.class)
@ContextHierarchy({
        @ContextConfiguration(classes = SecurityConfiguration.class),
        @ContextConfiguration(classes = WebConfiguration.class)
})
class EmailControllerTest {
    private final String EMAIL_URL = "/email";
    @Autowired WebApplicationContext context;

    MockMvc mockMvc;

    @BeforeEach
    public void setUpMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void searchWithInValidParam() throws Exception {
        mockMvc.perform(get(EMAIL_URL).queryParam("email", "sdhg12@aaa"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

}