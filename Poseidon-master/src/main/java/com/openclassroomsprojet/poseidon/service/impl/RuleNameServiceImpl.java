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
 * Cette classe contient des méthodes permettant d'effectuer des opérations CRUD en appelant
 * le référentiel de noms de règles.
 * @author chamberlin dior
 * @version 1.0
 */
@Slf4j
@Service
public class RuleNameServiceImpl implements IRuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    /**
     * Permet de rechercher tous les noms de règles contenus dans la base de données.
     *
     * @return Une liste de noms de règles.
     */
    @Override
    public List<RuleName> findAllRuleName() {
        log.info("Call service method: findAllRuleName()");
        return ruleNameRepository.findAll();
    }

    /**
     * Recherche un objet NomRegle à partir de son identifiant.
     *
     * @param id L'identifiant de l'objet à trouver.
     * @return Un optionnel qui peut contenir ou non l'objet demandé.
     */
    @Override
    public Optional<RuleName> findRuleNameById(int id) {
        log.info("Call service method: findRuleNameById(int id)");
        return ruleNameRepository.findById(id);
    }

    /**
     * Supprime un nom de règle.
     *
     * @param id L'identifiant de l'objet à supprimer.
     */
    @Override
    public void deleteRuleNameById(int id) {
        log.info("Call service method: deleteRuleNameById(int id)");
        ruleNameRepository.deleteById(id);
    }

    /**
     * Enregistre un nom de règle.
     *
     * @param nomRegle Objet à enregistrer.
     */
    @Override
    public void saveRuleName(RuleName ruleName) {
        log.info("Call service method: saveRuleName(RuleName ruleName)");
        ruleNameRepository.save(ruleName);
    }
}