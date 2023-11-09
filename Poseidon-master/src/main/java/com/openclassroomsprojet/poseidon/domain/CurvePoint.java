package com.openclassroomsprojet.poseidon.domain;

import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * JPA persistent entity. Corresponds to the mysql curve_point table
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Entity
@Table(name = "curve_point")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    @Max(value = 127, message = "curveId should be less than 127.")
    @Min(value = -128, message = "curveId should be upper than -128.")
    @Column(name = "curve_id")
    private int curveId;
    @Column(name = "as_Of_date")
    @DateTimeFormat
    private Date asOfDate;
    @NotNull
    @Min(value = 0, message = "Term should not be less than 0")
    private Double term;
    @NotNull
    private Double value;
    @Column(name = "creation_date")
    @DateTimeFormat
    private Date creationDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurveId() {
        return curveId;
    }

    public void setCurveId(int curveId) {
        this.curveId = curveId;
    }

    public Date getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate(Date asOfDate) {
        this.asOfDate = asOfDate;
    }

    public Double getTerm() {
        return term;
    }

    public void setTerm(Double term) {
        this.term = term;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}