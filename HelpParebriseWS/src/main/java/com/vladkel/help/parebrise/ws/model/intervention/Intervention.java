package com.vladkel.help.parebrise.ws.model.intervention;

import javax.persistence.Entity;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity(name = "Intervention")
@XmlRootElement(name = "intervention")
@XmlType(propOrder = { "indice_intervention", "indice_client",
		"indice_vehicule", "date_intervention", "date_facture",
		"numero_facture", "prix_HT", "prix_TTC", "id_tva", "acompte", "remise",
		"franchise", "indice_prestation", "indice_mode_paiement",
		"date_sinistre", "cause_sinistre", "adresse_intervention",
		"bon_de_commande", "date_echeance", "indice_contact",
		"assurance_impression" })
public class Intervention {

	@FormParam("indice_intervention")
	private Integer indice_intervention;
	
	@FormParam("indice_client")
	private Integer indice_client;

	@FormParam("indice_vehicule")
	private Integer indice_vehicule;

	@FormParam("date_intervention")
	private String date_intervention;

	@FormParam("date_facture")
	private String date_facture;

	@FormParam("numero_facture")
	private String numero_facture;

	@FormParam("prix_HT")
	private Double prix_HT;

	@FormParam("prix_TTC")
	private Double prix_TTC;

	@FormParam("id_tva")
	private Integer id_tva;

	@FormParam("acompte")
	private Double acompte;

	@FormParam("remise")
	private Double remise;

	@FormParam("franchise")
	private Double franchise;

	@FormParam("indice_prestation")
	private Integer indice_prestation;

	@FormParam("indice_mode_paiement")
	private Integer indice_mode_paiement;

	@FormParam("date_sinistre")
	private String date_sinistre;

	@FormParam("cause_sinistre")
	private String cause_sinistre;

	@FormParam("adresse_intervention")
	private String adresse_intervention;

	@FormParam("bon_de_commande")
	private String bon_de_commande;

	@FormParam("date_echeance")
	private String date_echeance;

	@FormParam("indice_contact")
	private Integer indice_contact;

	@FormParam("assurance_impression")
	private String assurance_impression;
	

	public Intervention() {
	}

	@XmlElement
	public Integer getIndice_intervention() {
		return indice_intervention;
	}

	public void setIndice_intervention(Integer indice_intervention) {
		this.indice_intervention = indice_intervention;
	}

	@XmlElement
	public Integer getIndice_client() {
		return indice_client;
	}

	public void setIndice_client(Integer indice_client) {
		this.indice_client = indice_client;
	}

	@XmlElement
	public Integer getIndice_vehicule() {
		return indice_vehicule;
	}

	public void setIndice_vehicule(Integer indice_vehicule) {
		this.indice_vehicule = indice_vehicule;
	}

	@XmlElement
	public String getDate_intervention() {
		return date_intervention;
	}

	public void setDate_intervention(String date_intervention) {
		this.date_intervention = date_intervention;
	}

	@XmlElement
	public String getDate_facture() {
		return date_facture;
	}

	public void setDate_facture(String date_facture) {
		this.date_facture = date_facture;
	}

	@XmlElement
	public String getNumero_facture() {
		return numero_facture;
	}

	public void setNumero_facture(String numero_facture) {
		this.numero_facture = numero_facture;
	}

	@XmlElement
	public Double getPrix_HT() {
		return prix_HT;
	}

	public void setPrix_HT(Double prix_HT) {
		this.prix_HT = prix_HT;
	}

	@XmlElement
	public Double getPrix_TTC() {
		return prix_TTC;
	}

	public void setPrix_TTC(Double prix_TTC) {
		this.prix_TTC = prix_TTC;
	}

	@XmlElement
	public Integer getId_tva() {
		return id_tva;
	}

	public void setId_tva(Integer id_tva) {
		this.id_tva = id_tva;
	}

	@XmlElement
	public Double getAcompte() {
		return acompte;
	}

	public void setAcompte(Double acompte) {
		this.acompte = acompte;
	}

	@XmlElement
	public Double getRemise() {
		return remise;
	}

	public void setRemise(Double remise) {
		this.remise = remise;
	}

	@XmlElement
	public Double getFranchise() {
		return franchise;
	}

	public void setFranchise(Double franchise) {
		this.franchise = franchise;
	}

	@XmlElement
	public Integer getIndice_prestation() {
		return indice_prestation;
	}

	public void setIndice_prestation(Integer indice_prestation) {
		this.indice_prestation = indice_prestation;
	}

	@XmlElement
	public Integer getIndice_mode_paiement() {
		return indice_mode_paiement;
	}

	public void setIndice_mode_paiement(Integer indice_mode_paiement) {
		this.indice_mode_paiement = indice_mode_paiement;
	}

	@XmlElement
	public String getDate_sinistre() {
		return date_sinistre;
	}

	public void setDate_sinistre(String date_sinistre) {
		this.date_sinistre = date_sinistre;
	}

	@XmlElement
	public String getCause_sinistre() {
		return cause_sinistre;
	}

	public void setCause_sinistre(String cause_sinistre) {
		this.cause_sinistre = cause_sinistre;
	}

	@XmlElement
	public String getAdresse_intervention() {
		return adresse_intervention;
	}

	public void setAdresse_intervention(String adresse_intervention) {
		this.adresse_intervention = adresse_intervention;
	}

	@XmlElement
	public String getBon_de_commande() {
		return bon_de_commande;
	}

	public void setBon_de_commande(String bon_de_commande) {
		this.bon_de_commande = bon_de_commande;
	}

	@XmlElement
	public String getDate_echeance() {
		return date_echeance;
	}

	public void setDate_echeance(String date_echeance) {
		this.date_echeance = date_echeance;
	}

	@XmlElement
	public Integer getIndice_contact() {
		return indice_contact;
	}

	public void setIndice_contact(Integer indice_contact) {
		this.indice_contact = indice_contact;
	}

	@XmlElement
	public String getAssurance_impression() {
		return assurance_impression;
	}

	public void setAssurance_impression(String assurance_impression) {
		this.assurance_impression = assurance_impression;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Intervention n° : " + indice_intervention + "\n");
		sb.append("\t" + indice_client + "\n");
		sb.append("\t" + indice_vehicule + "\n");
		sb.append("\t" + date_intervention + "\n");
		sb.append("\t" + date_facture + "\n");
		sb.append("\t" + numero_facture + "\n");
		sb.append("\t" + prix_HT + "\n");
		sb.append("\t" + prix_TTC + "\n");
		sb.append("\t" + id_tva + "\n");
		sb.append("\t" + acompte + "\n");
		sb.append("\t" + remise + "\n");
		sb.append("\t" + franchise + "\n");
		sb.append("\t" + indice_prestation + "\n");
		sb.append("\t" + indice_mode_paiement + "\n");
		sb.append("\t" + date_sinistre + "\n");
		sb.append("\t" + cause_sinistre + "\n");
		sb.append("\t" + adresse_intervention + "\n");
		sb.append("\t" + bon_de_commande + "\n");
		sb.append("\t" + date_echeance + "\n");
		sb.append("\t" + indice_contact + "\n");
		sb.append("\t" + assurance_impression + "\n");
		return sb.toString();
	}

}
