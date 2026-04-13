package CofetarieRaduMariaPaula.service;

import CofetarieRaduMariaPaula.model.Tort;

/**
 * Clasa care se ocupa de calculele matematice ale cofetariei
 * Aici stabilim pretul final si cat gramaj scadem din vitrina
 */
public class CofetarieService {

    /**
     * Calculeaza cat are de platit clientul in functie de ce a ales
     * @param t Produsul selectat de exemplu Tort Diplomat
     * @param mod Cum se vinde la felie, bucata sau tort intreg
     * @param cantitate Cate bucati sau felii vrea clientul.
     * @return Pretul total in lei
     */
    public double calculeazaCost(Tort t, String mod, int cantitate) {
        if (mod.equals("Bucată") || mod.equals("Tort Întreg")) {
            // pretul este per unitate de ex un Lava Cake 220g intreg sau un tort de 1200g
            return cantitate * t.getPretKg();
        } else { 
            // daca e felie calculam pretul raportat la 150g
            return cantitate * (t.getPretKg() / t.getGramajUnit()) * 150;
        }
    }

    /**
     * Calculeaza cate grame trebuie scoase din stoc dupa vanzare
     * @param t Tortul din care se taie sau se vinde
     * @param mod Daca s-a vandut o felie sau tot tortul
     * @param cantitate Numarul de portii vandute
     * @return Totalul de grame care se va scadea din baza de date
     */
    public double calculeazaGramajDeScazut(Tort t, String mod, int cantitate) {
        if (mod.equals("Bucată") || mod.equals("Tort Întreg")) {
            // scadem toata unitatea de exemplu 1200g
            return cantitate * t.getGramajUnit();
        } else {
            // daca e felie scadem fix 150g per bucata
            return cantitate * 150;
        }
    }
}