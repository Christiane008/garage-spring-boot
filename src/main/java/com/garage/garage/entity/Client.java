package com.garage.garage.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(name = "adresse_email", nullable = false, unique = true, length = 150)
    private String email;

    private String telephone;
    private String adresse;

    @OneToMany(mappedBy = "proprietaire", cascade = CascadeType.PERSIST)
    private List<Vehicule> vehicules = new ArrayList<>();

    // Côté inverse de Facture.client (relation "reçoit" du diagramme de classes)
    @OneToMany(mappedBy = "client")
    private List<Facture> factures = new ArrayList<>();

    public Client() {
    }

    public Client(String nom, String prenom, String email, String telephone) {
        super();
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
    }

    public void ajouterVehicule(Vehicule vehicule) {
        vehicules.add(vehicule);
        vehicule.setProprietaire(this);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public List<Vehicule> getVehicules() { return vehicules; }
    public void setVehicules(List<Vehicule> vehicules) { this.vehicules = vehicules; }

    public List<Facture> getFactures() { return factures; }
    public void setFactures(List<Facture> factures) { this.factures = factures; }
}
