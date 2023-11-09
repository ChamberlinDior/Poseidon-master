package com.openclassroomsprojet.poseidon.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Date;

/**
 * JPA persistent entity. Corresponds to the mysql trade table
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Entity
@Table(name = "trade")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trade_id")
    private Integer tradeId;
    @NotBlank(message = "Account is mandatory")
    private String account;
    @NotBlank(message = "Type is mandatory")
    private String type;
    @Column(name = "buy_quantity")
    @Min(value = 0, message = "Buy quantity should not be less than 0")
    private Double buyQuantity;
    @Column(name = "sell_quantity")
    @Min(value = 0, message = "Sell quantity should not be less than 0")
    private Double sellQuantity;
    @Column(name = "buy_price")
    @Min(value = 0, message = "Buy price should not be less than 0")
    private Double buyPrice;
    @Column(name = "sell_price")
    @Min(value = 0, message = "Sell price should not be less than 0")
    private Double sellPrice;
    @Column(name = "trade_date")
    @DateTimeFormat
    private Timestamp tradeDate;
    private String security;
    private String status;
    private String trader;
    private String benchmark;
    private String book;
    @Column(name = "creation_name")
    private String creationName;
    @Column(name = "creation_date")
    @DateTimeFormat
    private Date creationDate;
    @Column(name = "revision_name")
    private String revisionName;
    @Column(name = "revision_date")
    @DateTimeFormat
    private Date revisionDate;
    @Column(name = "deal_name")
    private String dealName;
    @Column(name = "deal_type")
    private String dealType;
    @Column(name = "source_list_id")
    private String sourceListId;
    private String side;

    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(Double buyQuantity) {
        this.buyQuantity = buyQuantity;
    }

    public Double getSellQuantity() {
        return sellQuantity;
    }

    public void setSellQuantity(Double sellQuantity) {
        this.sellQuantity = sellQuantity;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Timestamp tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public String getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getCreationName() {
        return creationName;
    }

    public void setCreationName(String creationName) {
        this.creationName = creationName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getRevisionName() {
        return revisionName;
    }

    public void setRevisionName(String revisionName) {
        this.revisionName = revisionName;
    }

    public Date getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Date revisionDate) {
        this.revisionDate = revisionDate;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getSourceListId() {
        return sourceListId;
    }

    public void setSourceListId(String sourceListId) {
        this.sourceListId = sourceListId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}