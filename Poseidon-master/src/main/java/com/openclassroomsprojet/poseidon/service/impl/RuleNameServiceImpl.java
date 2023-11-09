package com.openclassroomsprojet.poseidon.service.impl;

import com.openclassroomsprojet.poseidon.domain.RuleName;
import com.openclassroomsprojet.poseidon.repositories.RuleNameRepository;
import com.openclassroomsprojet.poseidon.service.IRuleNameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * This class contains methods that allow performing CRUD actions by calling the ruleName repository
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Slf4j
@Service
public class RuleNameServiceImpl implements IRuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    /**
     * Allows to search all the RuleNames contained in the database
     *
     * @return A list of RuleName
     */
    @Override
    public List<RuleName> findAllRuleName() {
        log.info("Call service method: findAllRuleName()");
        return ruleNameRepository.findAll();
    }

    /**
     * Searches for a RuleName object from its identifier
     *
     * @param id The identifier of the object to find
     * @return An optional which may or may not contain the requested object
     */
    @Override
    public Optional<RuleName> findRuleNameById(int id) {
        log.info("Call service method: findRuleNameById(int id)");
        return ruleNameRepository.findById(id);
    }

    /**
     * Delete a RuleName
     *
     * @param id The identifier of the object to delete
     */
    @Override
    public void deleteRuleNameById(int id) {
        log.info("Call service method: deleteRuleNameById(int id)");
        ruleNameRepository.deleteById(id);
    }

    /**
     * Save a RuleName
     *
     * @param ruleName Object to be saved
     */
    @Override
    public void saveRuleName(RuleName ruleName) {
        log.info("Call service method: saveRuleName(RuleName ruleName)");
        ruleNameRepository.save(ruleName);
    }
}