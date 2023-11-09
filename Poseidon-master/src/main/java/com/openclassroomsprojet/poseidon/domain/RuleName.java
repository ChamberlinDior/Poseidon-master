package com.openclassroomsprojet.poseidon.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * JPA persistent entity. Corresponds to the mysql rule_name table
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Entity
@Table(name = "rule_name")
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "description is mandatory")
    private String description;
    @NotBlank(message = "json is mandatory")
    private String json;
    @NotBlank(message = "template is mandatory")
    private String template;
    @NotBlank(message = "Sql str is mandatory")
    @Column(name = "sql_str")
    private String sqlStr;
    @NotBlank(message = "sqlPart is mandatory")
    @Column(name = "sql_part")
    private String sqlPart;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSqlStr() {
        return sqlStr;
    }

    public void setSqlStr(String sqlStr) {
        this.sqlStr = sqlStr;
    }

    public String getSqlPart() {
        return sqlPart;
    }

    public void setSqlPart(String sqlPart) {
        this.sqlPart = sqlPart;
    }
}