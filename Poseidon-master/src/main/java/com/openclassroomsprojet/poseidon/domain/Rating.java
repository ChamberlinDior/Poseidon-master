package com.openclassroomsprojet.poseidon.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * JPA persistent entity. Corresponds to the mysql rating table
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "moodys_rating")
    @NotBlank(message = "Moody's rating is mandatory.")
    private String moodysRating;
    @Column(name = "sand_prating")
    @NotBlank(message = "Sand prating is mandatory.")
    private String sandPrating;
    @Column(name = "fitch_rating")
    @NotBlank(message = "Fitch rating is mandatory.")
    private String fitchRating;
    @Column(name = "order_number")
    @NotNull
    @Min(value = 0, message = "order number should not be less than 0")
    private int orderNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMoodysRating() {
        return moodysRating;
    }

    public void setMoodysRating(String moodysRating) {
        this.moodysRating = moodysRating;
    }

    public String getSandPrating() {
        return sandPrating;
    }

    public void setSandPrating(String sandPrating) {
        this.sandPrating = sandPrating;
    }

    public String getFitchRating() {
        return fitchRating;
    }

    public void setFitchRating(String fitchRating) {
        this.fitchRating = fitchRating;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
}