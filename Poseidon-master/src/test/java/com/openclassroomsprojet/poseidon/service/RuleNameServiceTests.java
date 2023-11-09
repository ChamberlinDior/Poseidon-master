package com.openclassroomsprojet.poseidon.service;

import com.openclassroomsprojet.poseidon.domain.RuleName;
import com.openclassroomsprojet.poseidon.repositories.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@SpringBootTest
public class RuleNameServiceTests {

    @MockBean
    private RuleNameRepository ruleNameRepositoryMock;
    @Autowired
    private IRuleNameService ruleNameService;

    @Test
    public void givenRuleNameList_whenFindAllRuleName_thenReturnCorrectRuleNameList() {
        RuleName ruleNameTest = new RuleName();
        ruleNameTest.setId(1);
        ruleNameTest.setName("Name test");
        ruleNameTest.setDescription("Description test");
        ruleNameTest.setJson("Json test");
        ruleNameTest.setTemplate("Template test");
        ruleNameTest.setSqlStr("Sql string test");
        ruleNameTest.setSqlPart("Sql part test");
        List<RuleName> ratingListTest = Collections.singletonList(ruleNameTest);
        when(ruleNameRepositoryMock.findAll()).thenReturn(ratingListTest);
        List<RuleName> ratingList = ruleNameService.findAllRuleName();
        assertNotNull(ratingList);
        assertEquals(1,ratingList.size());
    }

    @Test
    public void givenRuleName_whenFindRuleNameById_thenReturnCorrectRuleName() {
        Random randomInt = new Random();
        RuleName ruleNameTest = new RuleName();
        ruleNameTest.setId(1);
        ruleNameTest.setName("Name test");
        ruleNameTest.setDescription("Description test");
        ruleNameTest.setJson("Json test");
        ruleNameTest.setTemplate("Template test");
        ruleNameTest.setSqlStr("Sql string test");
        ruleNameTest.setSqlPart("Sql part test");
        when(ruleNameRepositoryMock.findById(any())).thenReturn(Optional.of(ruleNameTest));
        Optional<RuleName> OptionalRuleName = ruleNameService.findRuleNameById(randomInt.nextInt(100));
        RuleName finalRuleName = new  RuleName();
        if (OptionalRuleName.isPresent()) {
            finalRuleName = OptionalRuleName.get();
        }
        assertNotNull(finalRuleName);
        assertEquals(1, finalRuleName.getId());
        assertEquals("Name test", finalRuleName.getName());
        assertEquals("Description test", finalRuleName.getDescription());
        assertEquals("Json test", finalRuleName.getJson());
        assertEquals("Template test", finalRuleName.getTemplate());
        assertEquals("Sql string test", finalRuleName.getSqlStr());
        assertEquals("Sql part test", finalRuleName.getSqlPart());
    }

    @Test
    public void givenRuleName_whenDeleteRuleNameById_thenVerify() {
        Random random = new Random();
        int randomInt = random.nextInt(100);
        doNothing().when(ruleNameRepositoryMock).deleteById(any());
        ruleNameService.deleteRuleNameById(randomInt);
        verify(ruleNameRepositoryMock, times(1)).deleteById(randomInt);
    }

    @Test
    public void givenRuleName_whenSaveRuleName_thenVerify() {
        RuleName ruleNameTest = new RuleName();
        ruleNameTest.setId(1);
        ruleNameTest.setName("Name test");
        ruleNameTest.setDescription("Description test");
        ruleNameTest.setJson("Json test");
        ruleNameTest.setTemplate("Template test");
        ruleNameTest.setSqlStr("Sql string test");
        ruleNameTest.setSqlPart("Sql part test");
        when(ruleNameRepositoryMock.save(any())).thenReturn(ruleNameTest);
        ruleNameService.saveRuleName(ruleNameTest);
        verify(ruleNameRepositoryMock, times(1)).save(ruleNameTest);
    }
}