package com.openclassroomsprojet.poseidon.service.impl;

import com.openclassroomsprojet.poseidon.domain.BidList;
import com.openclassroomsprojet.poseidon.domain.Trade;
import com.openclassroomsprojet.poseidon.repositories.TradeRepository;
import com.openclassroomsprojet.poseidon.service.ITradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Cette classe contient des méthodes permettant d'effectuer des opérations CRUD en appelant le référentiel de transactions.
 *
 * @author chamberlin dior
 * @version 1.0
 */
@Slf4j
@Service
public class TradeServiceImpl implements ITradeService {

    @Autowired
    private TradeRepository tradeRepository;

    /**
     * Permet de rechercher toutes les transactions contenues dans la base de données.
     *
     * @return Une liste de transactions.
     */
    @Override
    public List<Trade> findAllTrade() {
        log.info("Call service method: findAllTrade()");
        return tradeRepository.findAll();
    }

    /**
     * Recherche un objet Transaction à partir de son identifiant.
     *
     * @param id L'identifiant de l'objet à trouver.
     * @return Un optionnel qui peut contenir ou non l'objet demandé.
     */
    @Override
    public Optional<Trade> findTradeById(int id) {
        log.info("Call service method: findTradeById(int id)");
        return tradeRepository.findById(id);
    }

    /**
     * Supprime une transaction.
     *
     * @param id L'identifiant de l'objet à supprimer.
     */
    @Override
    public void deleteTradeById(int id) {
        log.info("Call service method: deleteTradeById(int id)");
        tradeRepository.deleteById(id);
    }

    /**
     * Enregistre une transaction.
     * La création du nom et de la date de création n'est enregistrée qu'une fois, lors de la création.
     * La date et le nom de révision sont mis à jour chaque fois que la ressource est mise à jour.
     *
     * @param transaction Objet à enregistrer.
     */
    @Override
    public void saveTrade(Trade trade) {
        log.info("Call service method: saveTrade(Trade trade)");
        if (trade.getTradeId() == null) {
            trade.setCreationDate(new Date());
            trade.setCreationName("Trade" + trade.getAccount() + "_" + trade.getType());
        } else {
            Optional<Trade> tradeAlreadyExist = tradeRepository.findById(trade.getTradeId());
            if (tradeAlreadyExist.isPresent()) {
                Trade temporaryBidList = tradeAlreadyExist.get();
                trade.setCreationDate(temporaryBidList.getCreationDate());
                trade.setCreationName(temporaryBidList.getCreationName());
                trade.setRevisionDate(new Date());
                trade.setRevisionName("REVISION_Trade_" + trade.getAccount() + "_" + trade.getType());
            }
        }
        tradeRepository.save(trade);
    }
}