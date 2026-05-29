package fr.ecole.tp.model;

public class Client {
    private final String id;
    private final String nom;
    private final String email;

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
