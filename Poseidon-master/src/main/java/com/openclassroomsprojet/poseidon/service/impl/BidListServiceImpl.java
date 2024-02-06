package com.openclassroomsprojet.poseidon.service.impl;

import com.openclassroomsprojet.poseidon.domain.BidList;
import com.openclassroomsprojet.poseidon.repositories.BidListRepository;
import com.openclassroomsprojet.poseidon.service.IBidListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Cette classe contient des méthodes permettant d'effectuer des opérations CRUD en appelant le référentiel de listes d'offres.
 *
 * @author chamberlin dior
 * @version 1.0
 */
@Slf4j
@Service
public class BidListServiceImpl implements IBidListService {

    @Autowired
    private BidListRepository bidListRepository;

    /**
     * Permet de rechercher toutes les listes d'offres contenues dans la base de données.
     *
     * @return Une liste de listes d'offres.
     */
    @Override
    public List<BidList> findAllBidList() {
        log.info("Call service method: findAllBidList()");
        return bidListRepository.findAll();
    }

    /**
     * Recherche un objet ListeOffre à partir de son identifiant.
     *
     * @param id L'identifiant de l'objet à trouver.
     * @return Un optionnel qui peut contenir ou non l'objet demandé.
     */
    @Override
    public Optional<BidList> findBidListById(int id) {
        log.info("Call service method: findBidListById(int id)");
        return bidListRepository.findBidListByBidListId(id);
    }

    /**
     * Supprime une liste d'offres.
     *
     * @param id L'identifiant de l'objet à supprimer.
     */
    @Override
    public void deleteBidListById(int id) {
        log.info("Call service method: deleteBidListById(int id)");
        bidListRepository.deleteById(id);
    }

    /**
     * Enregistre une liste d'offres.
     * La création du nom et de la date de création n'est enregistrée qu'une fois, lors de la création.
     * La date et le nom de révision sont mis à jour chaque fois que la ressource est mise à jour.
     *
     * @param listeOffre Objet à enregistrer.
     */
    @Override
    public void saveBidList(BidList bidList) {
        log.info("Call service method: saveBidList(BidList bidList)");
        if (bidList.getBidListId() == null) {
            bidList.setCreationDate(new Date());
            bidList.setCreationName("BidList_" + bidList.getAccount() + "_" + bidList.getType());
        } else {
            Optional<BidList> bidListAlreadySave = bidListRepository.findBidListByBidListId(bidList.getBidListId());
            if (bidListAlreadySave.isPresent()) {
                BidList temporaryBidList = bidListAlreadySave.get();
                bidList.setCreationDate(temporaryBidList.getCreationDate());
                bidList.setCreationName(temporaryBidList.getCreationName());
                bidList.setRevisionDate(new Date());
                bidList.setRevisionName("REVISION_BidList_" + bidList.getAccount() + "_" + bidList.getType());
            }
        }
        bidListRepository.save(bidList);
    }
}