package fr.yaya_diallo.gps;

/**
 * Created by yayacky on 04/09/2016.
 */
public class GPSReceive {

    private String id;
    private String nom ;
    private String telephone;
    private String coordonnees;
    private String dateReception;
    private String lieu;

    public GPSReceive()
    {
        super();
    }

    public GPSReceive(String id, String nom, String telephone, String coordonnees, String dateReception, String lieu) {
        this.id = id;
        this.nom = nom;
        this.telephone = telephone;
        this.coordonnees = coordonnees;
        this.dateReception = dateReception;
        this.lieu = lieu;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getCoordonnees() {
        return coordonnees;
    }

    public String getDateReception() {
        return dateReception;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCoordonnees(String coordonnees) {
        this.coordonnees = coordonnees;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setDateReception(String dateReception) {
        this.dateReception = dateReception;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GPSReceive that = (GPSReceive) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (nom != null ? !nom.equals(that.nom) : that.nom != null) return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null)
            return false;
        if (!coordonnees.equals(that.coordonnees)) return false;
        return lieu != null ? lieu.equals(that.lieu) : that.lieu == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nom != null ? nom.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + coordonnees.hashCode();
        result = 31 * result + (lieu != null ? lieu.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GPSReceive{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", telephone='" + telephone + '\'' +
                ", coordonnees='" + coordonnees + '\'' +
                ", dateReception='" + dateReception + '\'' +
                ", lieu='" + lieu + '\'' +
                '}';
    }
}
