package CofetarieRaduMariaPaula.model;

import javafx.beans.property.*;

/**
 * Clasa care defineste obiectul de tip Tort.
 * Folosim proprietati JavaFX pentru ca tabelul sa se updateze singur
 */
public class Tort {
    private StringProperty denumire;
    private DoubleProperty gramaj;      
    private DoubleProperty pretKg;      
    private DoubleProperty gramajUnit;  

    /**
     * Constructor pentru crearea unui tort nou
     * @param denumire Numele produsului
     * @param gramaj Stocul total disponibil in grame
     * @param pretKg Pretul per unitate
     * @param gramajUnit Cat cantareste o bucata intreaga
     */
    public Tort(String denumire, double gramaj, double pretKg, double gramajUnit) {
        this.denumire = new SimpleStringProperty(denumire);
        this.gramaj = new SimpleDoubleProperty(gramaj);
        this.pretKg = new SimpleDoubleProperty(pretKg);
        this.gramajUnit = new SimpleDoubleProperty(gramajUnit);
    }

    public StringProperty denumireProperty() { return denumire; }
    public String getDenumire() { return denumire.get(); }
    
    public DoubleProperty gramajProperty() { return gramaj; }
    public double getGramaj() { return gramaj.get(); }
    public void setGramaj(double value) { gramaj.set(value); }

    public DoubleProperty pretKgProperty() { return pretKg; }
    public double getPretKg() { return pretKg.get(); }
    public void setPretKg(double value) { pretKg.set(value); } // Permite butonului Schimba Pret sa functioneze

    public DoubleProperty gramajUnitProperty() { return gramajUnit; }
    public double getGramajUnit() { return gramajUnit.get(); }
}