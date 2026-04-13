package CofetarieRaduMariaPaula.repository;

import CofetarieRaduMariaPaula.model.Tort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.Scanner;

/**
 * Clasa responsabila cu gestiunea fisierului unde sunt salvate torturile.
 * Aceasta asigura citirea datelor la pornire si salvarea lor la inchidere.
 */
public class TortRepository {
    private final String FILE_NAME = "torturi.txt";

    /**
     * Incarca datele din fisierul text si le adauga intr-o lista de tip ObservableList
     * @return Lista de produse incarcata din baza de date text
     */
    public ObservableList<Tort> incarcaDate() {
        ObservableList<Tort> lista = FXCollections.observableArrayList();
        File file = new File(FILE_NAME);
        
        // verificam daca exista fisierul 
        if (!file.exists()) {
            System.out.println("Fișierul " + FILE_NAME + " nu a fost găsit. Va fi creat la prima salvare.");
            return lista;
        }
        try (Scanner s = new Scanner(file)) {
            while (s.hasNextLine()) {
                String linie = s.nextLine().trim();
                if (linie.isEmpty()) continue; 

                String[] p = linie.split(";");
                if (p.length == 4) {
                    try {
                        lista.add(new Tort(
                            p[0],                          // Denumire
                            Double.parseDouble(p[1]),      // Gramaj 
                            Double.parseDouble(p[2]),      // Pret Unitate
                            Double.parseDouble(p[3])       // Gramaj Unitate
                        ));
                    } catch (NumberFormatException e) {
                        System.err.println("Eroare la formatarea numerelor pe linia: " + linie);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Eroare la încărcarea datelor: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Suprascrie fisierul text cu noile valori din lista de torturi
     * @param lista Lista de obiecte Tort care trebuie salvata pe disc
     */
    public void salveazaDate(ObservableList<Tort> lista) {
        // suprascriem lista pentru a o actualiza 
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Tort t : lista) {
                pw.println(t.getDenumire() + ";" + 
                           t.getGramaj() + ";" + 
                           t.getPretKg() + ";" + 
                           t.getGramajUnit());
            }
        } catch (Exception e) {
            System.err.println("Eroare la salvarea datelor: " + e.getMessage());
        }
    }
}