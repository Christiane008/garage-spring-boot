package com.garage.garage.dto;

import com.garage.garage.entity.StatutFacture;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FactureDTO {
    private Long id;
    private String numero;
    private LocalDate dateEmission;
    private BigDecimal montantTotal;
    private StatutFacture statut;
    private Long clientId;
    private String clientNom;
    private Long interventionId;

    public FactureDTO() {
    }

    public FactureDTO(Long id, String numero, LocalDate dateEmission, BigDecimal montantTotal,
                       StatutFacture statut, Long clientId, String clientNom, Long interventionId) {
        super();
        this.id = id;
        this.numero = numero;
        this.dateEmission = dateEmission;
        this.montantTotal = montantTotal;
        this.statut = statut;
        this.clientId = clientId;
        this.clientNom = clientNom;
        this.interventionId = interventionId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public LocalDate getDateEmission() { return dateEmission; }
    public void setDateEmission(LocalDate dateEmission) { this.dateEmission = dateEmission; }

    public BigDecimal getMontantTotal() { return montantTotal; }
    public void setMontantTotal(BigDecimal montantTotal) { this.montantTotal = montantTotal; }

    public StatutFacture getStatut() { return statut; }
    public void setStatut(StatutFacture statut) { this.statut = statut; }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public String getClientNom() { return clientNom; }
    public void setClientNom(String clientNom) { this.clientNom = clientNom; }

    public Long getInterventionId() { return interventionId; }
    public void setInterventionId(Long interventionId) { this.interventionId = interventionId; }
}
