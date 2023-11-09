package com.openclassroomsprojet.poseidon.controllers;

import com.openclassroomsprojet.poseidon.domain.RuleName;
import com.openclassroomsprojet.poseidon.service.IRuleNameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
public class RuleNameControllerTests {

    @MockBean
    private IRuleNameService ruleNameServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser("spring")
    public void getAllRuleNameTest() throws Exception {
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"));
        verify(ruleNameServiceMock, times(1)).findAllRuleName();
    }

    @Test
    public void getAllRuleNameTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void getAddRuleNameFormTest() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk()).andExpect(view().name("ruleName/add"));
    }

    @Test
    public void getAddRuleNameFormTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void postValidateTestWithValidObject() throws Exception {
        RuleName ruleNameTest = new RuleName();
        ruleNameTest.setName("test");
        ruleNameTest.setDescription("test");
        ruleNameTest.setJson("test");
        ruleNameTest.setTemplate("test");
        ruleNameTest.setSqlStr("test");
        ruleNameTest.setSqlPart("test");
        mockMvc.perform(post("/ruleName/validate").flashAttr("ruleName", ruleNameTest))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/ruleName/list"));
        verify(ruleNameServiceMock, times(1)).saveRuleName(ruleNameTest);
    }

    @Test
    @WithMockUser("spring")
    public void postValidateTestWithNotValidObject() throws Exception {
        RuleName ruleNameTest = new RuleName();
        mockMvc.perform(post("/ruleName/validate").flashAttr("ruleName", ruleNameTest))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
        verify(ruleNameServiceMock, times(0)).saveRuleName(ruleNameTest);
    }

    @Test
    public void postValidateTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(post("/ruleName/validate"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void getShowUpdateFormTestWithValidId() throws Exception {
        when(ruleNameServiceMock.findRuleNameById(1)).thenReturn(Optional.of(new RuleName()));
        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
        verify(ruleNameServiceMock, times(1)).findRuleNameById(1);
    }

    @Test
    @WithMockUser("spring")
    public void getShowUpdateFormTestWithNotValidId() throws Exception {
        when(ruleNameServiceMock.findRuleNameById(99)).thenReturn(Optional.empty());
        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(get("/ruleName/update/99"));
        });
        verify(ruleNameServiceMock, times(1)).findRuleNameById(99);
    }

    @Test
    public void getShowUpdateFormTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void postUpdateRuleNameTestWithValidObject() throws Exception {
        RuleName ruleNameTest = new RuleName();
        ruleNameTest.setName("test");
        ruleNameTest.setDescription("test");
        ruleNameTest.setJson("test");
        ruleNameTest.setTemplate("test");
        ruleNameTest.setSqlStr("test");
        ruleNameTest.setSqlPart("test");
        doNothing().when(ruleNameServiceMock).saveRuleName(any());
        mockMvc.perform(post("/ruleName/update/1").flashAttr("ruleName", ruleNameTest))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/ruleName/list"));
        verify(ruleNameServiceMock, times(1)).saveRuleName(any());
    }

    @Test
    @WithMockUser("spring")
    public void postUpdateRuleNameTestWithNotValidObject() throws Exception {
        RuleName ruleNameTest = new RuleName();
        doNothing().when(ruleNameServiceMock).saveRuleName(any());
        mockMvc.perform(post("/ruleName/update/1").flashAttr("ruleName", ruleNameTest))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
        verify(ruleNameServiceMock, times(0)).saveRuleName(any());
    }

    @Test
    public void postUpdateRuleNameTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(post("/ruleName/update/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }

    @Test
    @WithMockUser("spring")
    public void getDeleteRuleNameTestWithValidObject() throws Exception {
        when(ruleNameServiceMock.findRuleNameById(1)).thenReturn(Optional.of(new RuleName()));
        doNothing().when(ruleNameServiceMock).deleteRuleNameById(1);
        mockMvc.perform(get("/ruleName/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(view().name("redirect:/ruleName/list"));
        verify(ruleNameServiceMock, times(1)).deleteRuleNameById(1);
        verify(ruleNameServiceMock, times(1)).findRuleNameById(1);
    }

    @Test
    @WithMockUser("spring")
    public void getDeleteRuleNameTestWithNotValidObject() throws Exception {
        when(ruleNameServiceMock.findRuleNameById(1)).thenReturn(Optional.empty());
        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(get("/ruleName/delete/1"));
        });
        verify(ruleNameServiceMock, times(0)).deleteRuleNameById(1);
        verify(ruleNameServiceMock, times(1)).findRuleNameById(1);
    }

    @Test
    public void getDeleteRuleNameTestWithNotAuthorizedUser() throws Exception {
        mockMvc.perform(post("/ruleName/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/github")); //TODO http://localhost/login
    }
}