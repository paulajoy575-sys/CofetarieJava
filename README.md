Sistem Gestiune Cofetarie - JavaFX
Acesta este un proiect academic reprezentand o aplicatie de gestiune pentru o cofetarie. Aplicatia ofera o interfata grafica intuitiva pentru doua categorii de utilizatori: administratori cofetari si clienti.
Functionalitati Principale
Modul Administrare - Cofetar
Vizualizare Stocuri: Tabel centralizat cu toate produsele disponibile si cantitatile ramase in grame

Management Produse: Posibilitatea de a adauga sortimente noi in meniu prin definirea numelui, pretului si gramajului unitar

Actualizare Preturi: Functie dedicata pentru schimbarea rapida a pretului per unitate

Aprovizionare: Optiune de a suplimenta stocul existent pentru produsele selectate

Stergere: Eliminarea produselor care nu mai fac parte din oferta curenta

Modul Vanzare - Client
Meniu Interactiv: Vizualizarea listei de torturi si a preturilor calculate automat per portie sau per kilogram

Selectie Dinamica: Alegerea tipului de portie dorit in functie de marimea tortului

Cos de Cumparaturi: Adaugarea produselor intr-o lista temporara cu calcularea automata a totalului de plata

Finalizare Comanda: Scaderea automata a cantitatilor vandute din stocul general si generarea unui mesaj de confirmare

Detalii Tehnice
Tehnologii Utilizate
Java 17 sau versiuni mai noi

JavaFX: Pentru constructia interfetei grafice si gestionarea evenimentelor

Stocare in Fisier Text: Datele sunt salvate in fisierul torturi.txt pentru a asigura persistenta informatiilor

Arhitectura Proiectului
Proiectul respecta modelul de design stratificat pentru o mai buna organizare a codului:

Model: Defineste obiectul Tort si proprietatile acestuia

Repository: Gestioneaza operatiile de citire si scriere in fisierul extern

Service: Contine logica matematica pentru calculul preturilor si al gramajelor

View: Gestioneaza toate elementele grafice si interactiunea cu utilizatorul

Instructiuni de Rulare
Clonati arhiva proiectului in mediul dumneavoastra de dezvoltare preferred precum IntelliJ IDEA sau Eclipse

Asigurati-va ca aveti bibliotecile JavaFX configurate in proiect

Rulati clasa MainLauncher pentru a porni aplicatia fara erori de configurare a modulelor

Date de autentificare implicite:

Cofetar: utilizator: cofetar / parola: parola

Client: utilizator: client / parola: parola
