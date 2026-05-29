package fr.ecole.tp.model;

public class Client {
    private String id;
    private String nom;
    private String email;

    public Client(String id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Client{id=" + id + ", nom=" + nom + ", email=" + email + "}";
    }
}
