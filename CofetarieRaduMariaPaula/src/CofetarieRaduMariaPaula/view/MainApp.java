package CofetarieRaduMariaPaula.view;

import CofetarieRaduMariaPaula.model.Tort;
import CofetarieRaduMariaPaula.repository.TortRepository;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Clasa principala a aplicatiei care gestioneaza interfata grafica JavaFX
 * Aici se face trecerea intre ecranul de login si cele de utilizator
 */
public class MainApp extends Application {
    private TortRepository repo = new TortRepository();
    private ObservableList<Tort> listaProduse;
    private ObservableList<ElementCos> cosCumparaturi = FXCollections.observableArrayList();
    private Stage primaryStage;
    private Label lblTotal = new Label("Total de plată: 0.00 lei");

    private final String ACCENT_COLOR = "#d81b60";
    private final String BG_COLOR = "#fff5f8";

    /**
     * Metoda care porneste aplicatia si incarca datele din repo
     * @param stage Fereastra principala a aplicatiei
     */
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.listaProduse = repo.incarcaDate();
        afiseazaLogin();
    }

    /**
     * Creeaza bara de meniu cu optiuni de logout si exit
     * @return Obiectul MenuBar configurat
     */
    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menuApp = new Menu("_Sistem");
        MenuItem itemLogout = new MenuItem("_Logout");
        itemLogout.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
        itemLogout.setOnAction(e -> afiseazaLogin());
        MenuItem itemExit = new MenuItem("_Ieșire");
        itemExit.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        itemExit.setOnAction(e -> { repo.salveazaDate(listaProduse); System.exit(0); });
        menuApp.getItems().addAll(itemLogout, new SeparatorMenuItem(), itemExit);
        menuBar.getMenus().add(menuApp);
        return menuBar;
    }

    /**
     * Genereaza interfata pentru autentificarea utilizatorului
     */
    private void afiseazaLogin() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: " + BG_COLOR);
        Label lblTitlu = new Label("Cofetăria Paulei");
        lblTitlu.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: " + ACCENT_COLOR);
        
        VBox form = new VBox(10);
        form.setMaxWidth(300);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: white; -fx-background-radius: 12;");
        
        TextField txtUser = new TextField(); txtUser.setPromptText("Utilizator (client/cofetar)");
        PasswordField txtPass = new PasswordField(); txtPass.setPromptText("Parolă");
        Button btnLogin = new Button("Autentificare");
        btnLogin.setMaxWidth(Double.MAX_VALUE);
        btnLogin.setStyle("-fx-background-color: " + ACCENT_COLOR + "; -fx-text-fill: white; -fx-font-weight: bold;");
        
        btnLogin.setOnAction(e -> {
            if (txtPass.getText().equals("parola")) {
                if (txtUser.getText().equalsIgnoreCase("cofetar")) afiseazaInterfataCofetar();
                else if (txtUser.getText().equalsIgnoreCase("client")) afiseazaInterfataClient();
                else new Alert(Alert.AlertType.ERROR, "Utilizator necunoscut!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Parolă incorectă!").show();
            }
        });
        
        form.getChildren().addAll(txtUser, txtPass, btnLogin);
        layout.getChildren().addAll(lblTitlu, form);
        primaryStage.setScene(new Scene(layout, 1150, 850));
        primaryStage.show();
    }

    /**
     * Construieste interfata dedicata cofetarului pentru management stoc
     */
    private void afiseazaInterfataCofetar() {
        VBox root = new VBox(15);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10, 20, 20, 20));
        root.setStyle("-fx-background-color: " + BG_COLOR);

        Label lblAdmin = new Label("PANOU ADMINISTRARE");
        lblAdmin.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: " + ACCENT_COLOR);

        TableView<Tort> tabelAdm = new TableView<>();
        tabelAdm.setPrefHeight(250); 
        tabelAdm.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        
        TableColumn<Tort, String> c1 = new TableColumn<>("Produs");
        c1.setCellValueFactory(new PropertyValueFactory<>("denumire"));
        TableColumn<Tort, Double> c2 = new TableColumn<>("Stoc (g)");
        c2.setCellValueFactory(new PropertyValueFactory<>("gramaj"));
        TableColumn<Tort, Double> c3 = new TableColumn<>("Preț Unitate");
        c3.setCellValueFactory(new PropertyValueFactory<>("pretKg"));
        TableColumn<Tort, String> cPortie = new TableColumn<>("Preț/Porție");
        cPortie.setCellValueFactory(d -> new SimpleStringProperty(String.format("%.2f lei", 
            (d.getValue().getGramajUnit() < 400) ? d.getValue().getPretKg() : (d.getValue().getPretKg() / d.getValue().getGramajUnit()) * 150)));
        TableColumn<Tort, String> cKg = new TableColumn<>("Preț/Kg");
        cKg.setCellValueFactory(d -> new SimpleStringProperty(String.format("%.2f lei", 
            (d.getValue().getPretKg() / d.getValue().getGramajUnit()) * 1000)));

        tabelAdm.getColumns().add(c1);
        tabelAdm.getColumns().add(c2);
        tabelAdm.getColumns().add(c3);
        tabelAdm.getColumns().add(cPortie);
        tabelAdm.getColumns().add(cKg);
        tabelAdm.setItems(listaProduse);

        HBox containerChenare = new HBox(60);
        containerChenare.setAlignment(Pos.CENTER);
        containerChenare.setPadding(new Insets(40, 0, 0, 0)); 

        String boxStyle = "-fx-background-color: white; -fx-background-radius: 15; -fx-padding: 25;";
        double elementWidth = 220.0;

        VBox pnlAdd = new VBox(15);
        pnlAdd.setStyle(boxStyle); pnlAdd.setMinWidth(350); pnlAdd.setAlignment(Pos.CENTER);
        Label titluAdd = new Label("ADĂUGARE PRODUS NOU"); titluAdd.setStyle("-fx-font-weight: bold;");
        TextField tN = new TextField(); tN.setPromptText("Nume Produs"); tN.setMaxWidth(elementWidth);
        TextField tG = new TextField(); tG.setPromptText("Gramaj Unitate (g)"); tG.setMaxWidth(elementWidth);
        TextField tP = new TextField(); tP.setPromptText("Preț Unitate (lei)"); tP.setMaxWidth(elementWidth);
        Button btnAdd = new Button("✚ Salvează în Meniu");
        btnAdd.setMinWidth(elementWidth); btnAdd.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-weight: bold;");
        
        btnAdd.setOnAction(e -> {
            try {
                listaProduse.add(new Tort(tN.getText(), 0.0, Double.parseDouble(tP.getText()), Double.parseDouble(tG.getText())));
                tN.clear(); tG.clear(); tP.clear();
            } catch (Exception ex) {}
        });
        pnlAdd.getChildren().addAll(titluAdd, tN, tG, tP, btnAdd);

        VBox pnlMod = new VBox(15);
        pnlMod.setStyle(boxStyle); pnlMod.setMinWidth(350); pnlMod.setAlignment(Pos.CENTER);
        Label titluMod = new Label("MODIFICARE SELECȚIE"); titluMod.setStyle("-fx-font-weight: bold;");
        TextField tVal = new TextField(); tVal.setPromptText("Valoare"); tVal.setMaxWidth(elementWidth);
        Button bPlus = new Button("✚ Adaugă Unități");
        Button bMinus = new Button("➖ Elimină Bucată");
        Button bPret = new Button("💰 Schimbă Preț");

        for(Button b : new Button[]{bPlus, bMinus, bPret}) {
            b.setMinWidth(elementWidth); b.setStyle("-fx-font-weight: bold;");
        }

        bPlus.setOnAction(e -> {
            Tort s = tabelAdm.getSelectionModel().getSelectedItem();
            if(s!=null && !tVal.getText().isEmpty()) { s.setGramaj(s.getGramaj() + Double.parseDouble(tVal.getText())*s.getGramajUnit()); tabelAdm.refresh(); }
        });
        bMinus.setOnAction(e -> {
            Tort s = tabelAdm.getSelectionModel().getSelectedItem();
            if(s!=null) {
                double scade = (s.getGramajUnit() < 400) ? s.getGramajUnit() : 150;
                s.setGramaj(Math.max(0.0, s.getGramaj() - scade));
                tabelAdm.refresh();
            }
        });
        bPret.setOnAction(e -> {
            Tort s = tabelAdm.getSelectionModel().getSelectedItem();
            if(s!=null && !tVal.getText().isEmpty()) { s.setPretKg(Double.parseDouble(tVal.getText())); tabelAdm.refresh(); }
        });
        pnlMod.getChildren().addAll(titluMod, tVal, bPlus, bMinus, bPret);
        containerChenare.getChildren().addAll(pnlAdd, pnlMod);

        Button bDel = new Button("❌ Șterge Produs");
        bDel.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
        bDel.setOnAction(e -> {
            Tort s = tabelAdm.getSelectionModel().getSelectedItem();
            if(s!=null) {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Ștergeți definitiv produsul \"" + s.getDenumire() + "\"?");
                a.setHeaderText(null);
                if(a.showAndWait().get() == ButtonType.OK) listaProduse.remove(s);
            }
        });
        HBox footer = new HBox(bDel); footer.setAlignment(Pos.BOTTOM_LEFT); footer.setPadding(new Insets(30, 0, 0, 0));

        root.getChildren().addAll(createMenuBar(), lblAdmin, tabelAdm, containerChenare, footer);
        primaryStage.setScene(new Scene(root, 1150, 850));
    }

    /**
     * Construieste interfata pentru clienti si gestionarea cosului
     */
    private void afiseazaInterfataClient() {
        VBox layoutPrincipal = new VBox(15);
        layoutPrincipal.setAlignment(Pos.TOP_CENTER);
        layoutPrincipal.setPadding(new Insets(20));
        layoutPrincipal.setStyle("-fx-background-color: " + BG_COLOR);

        Label lblStelute = new Label("* Comanda torturile tale preferate *");
        lblStelute.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + ACCENT_COLOR);

        TableView<Tort> tabelProduse = new TableView<>();
        tabelProduse.setPrefHeight(250);
        tabelProduse.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        
        TableColumn<Tort, String> colN = new TableColumn<>("Produs");
        colN.setCellValueFactory(new PropertyValueFactory<>("denumire"));
        TableColumn<Tort, Double> colS = new TableColumn<>("Stoc (g)");
        colS.setCellValueFactory(new PropertyValueFactory<>("gramaj"));
        TableColumn<Tort, Double> colP = new TableColumn<>("Preț Unitate (lei)");
        colP.setCellValueFactory(new PropertyValueFactory<>("pretKg"));
        TableColumn<Tort, String> colKg = new TableColumn<>("Preț/Kg");
        colKg.setCellValueFactory(d -> new SimpleStringProperty(String.format("%.2f lei", 
            (d.getValue().getPretKg() / d.getValue().getGramajUnit()) * 1000)));
        TableColumn<Tort, String> colPortie = new TableColumn<>("Preț/Porție");
        colPortie.setCellValueFactory(d -> new SimpleStringProperty(String.format("%.2f lei", 
            (d.getValue().getGramajUnit() < 400) ? d.getValue().getPretKg() : (d.getValue().getPretKg() / d.getValue().getGramajUnit()) * 150)));

        tabelProduse.getColumns().add(colN);
        tabelProduse.getColumns().add(colS);
        tabelProduse.getColumns().add(colP);
        tabelProduse.getColumns().add(colKg);
        tabelProduse.getColumns().add(colPortie);
        tabelProduse.setItems(listaProduse);

        HBox zonaOptiuni = new HBox(20);
        zonaOptiuni.setAlignment(Pos.CENTER);
        ComboBox<String> comboP = new ComboBox<>(); comboP.setPromptText("Alege porția");
        TextField txtQ = new TextField(); txtQ.setPromptText("Cantitate"); txtQ.setPrefWidth(100);
        Button btnAddCos = new Button("🛒 Adaugă în Coș");
        btnAddCos.setStyle("-fx-background-color: " + ACCENT_COLOR + "; -fx-text-fill: white; -fx-font-weight: bold;");

        tabelProduse.getSelectionModel().selectedItemProperty().addListener((obs, v, n) -> {
            if(n != null) {
                comboP.getItems().clear();
                if(n.getGramajUnit() < 400) comboP.getItems().add("Bucată ("+n.getGramajUnit()+"g)");
                else comboP.getItems().addAll("Felie (150g)", "Tort Întreg ("+n.getGramajUnit()+"g)");
            }
        });

        btnAddCos.setOnAction(e -> {
            Tort s = tabelProduse.getSelectionModel().getSelectedItem();
            if(s != null && comboP.getValue() != null && !txtQ.getText().isEmpty()) {
                try {
                    int q = Integer.parseInt(txtQ.getText());
                    double gP = comboP.getValue().contains("Felie") ? 150 : s.getGramajUnit();
                    double pP = comboP.getValue().contains("Felie") ? (s.getPretKg()/s.getGramajUnit())*150 : s.getPretKg();
                    if(s.getGramaj() >= gP * q) {
                        cosCumparaturi.add(new ElementCos(s, comboP.getValue(), q, pP * q, gP * q));
                        actualizeazaTotal();
                        txtQ.clear();
                    } else new Alert(Alert.AlertType.WARNING, "Stoc insuficient!").show();
                } catch (Exception ex) {}
            }
        });
        zonaOptiuni.getChildren().addAll(new Label("Opțiuni:"), comboP, new Label("Cantitate:"), txtQ, btnAddCos);

        VBox zonaCos = new VBox(5);
        zonaCos.setMaxWidth(600);
        Label lblCosTitlu = new Label("COȘUL TĂU:"); 
        lblCosTitlu.setStyle("-fx-font-weight: bold; -fx-text-fill: " + ACCENT_COLOR);

        TableView<ElementCos> tabelCosClient = new TableView<>();
        tabelCosClient.setPrefHeight(180);
        tabelCosClient.setPlaceholder(new Label("Coșul este gol"));
        TableColumn<ElementCos, String> colDetalii = new TableColumn<>("Detalii Comandă");
        colDetalii.setCellValueFactory(d -> new SimpleStringProperty(String.format("%s (%s) x %d - %.2f lei", 
                                d.getValue().tort.getDenumire(), d.getValue().tipPortie, d.getValue().cantitate, d.getValue().pretTotal)));
        
        tabelCosClient.getColumns().add(colDetalii);
        tabelCosClient.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        tabelCosClient.setItems(cosCumparaturi);

        Button btnStergeCos = new Button("❌ Șterge din Coș");
        btnStergeCos.setOnAction(e -> {
            ElementCos s = tabelCosClient.getSelectionModel().getSelectedItem();
            if(s != null) {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Ștergeți din coș?");
                a.setHeaderText(null);
                if(a.showAndWait().get() == ButtonType.OK) { cosCumparaturi.remove(s); actualizeazaTotal(); }
            }
        });

        zonaCos.getChildren().addAll(lblCosTitlu, tabelCosClient, btnStergeCos);
        lblTotal.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: " + ACCENT_COLOR);
        Button btnFinalizare = new Button("✅ CUMPĂRARE");
        btnFinalizare.setMinWidth(400);
        btnFinalizare.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");
        btnFinalizare.setOnAction(e -> finalizeazaVanzarea());

        layoutPrincipal.getChildren().addAll(createMenuBar(), lblStelute, tabelProduse, zonaOptiuni, zonaCos, lblTotal, btnFinalizare);
        primaryStage.setScene(new Scene(layoutPrincipal, 1150, 850));
    }

    /**
     * Calculeaza suma totala din cosul de cumparaturi
     */
    private void actualizeazaTotal() {
        double t = cosCumparaturi.stream().mapToDouble(e -> e.pretTotal).sum();
        lblTotal.setText(String.format("Total de plată: %.2f lei", t));
    }

    /**
     * Scade produsele din stoc si afiseaza bonul final
     */
    private void finalizeazaVanzarea() {
        if(cosCumparaturi.isEmpty()) return;
        StringBuilder sb = new StringBuilder("Cumpărare reușită!\n\nProduse cumpărate:\n");
        double tot = 0;
        for(ElementCos e : cosCumparaturi) {
            sb.append("- ").append(e.tort.getDenumire()).append(" (").append(e.tipPortie).append(") x")
              .append(e.cantitate).append(" - ").append(String.format("%.2f lei", e.pretTotal)).append("\n");
            tot += e.pretTotal;
            e.tort.setGramaj(Math.max(0.0, e.tort.getGramaj() - e.gramajDeScazut));
        }
        sb.append(String.format("\nTotal de plată: %.2f lei", tot));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Finalizare");
        alert.setHeaderText(null); 
        alert.setContentText(sb.toString());
        alert.showAndWait();

        cosCumparaturi.clear(); 
        actualizeazaTotal();
    }

    /**
     * Clasa interna pentru obiectele din cosul de cumparaturi
     */
    public static class ElementCos {
        public Tort tort; public String tipPortie; public int cantitate; public double pretTotal; public double gramajDeScazut;
        /**
         * Constructor pentru un element de cos
         * @param t Obiectul Tort ales
         * @param tp Tipul portiei
         * @param c Cantitatea selectata
         * @param p Pretul total calculat
         * @param g Gramajul ce va fi scazut
         */
        public ElementCos(Tort t, String tp, int c, double p, double g) {
            this.tort=t; this.tipPortie=tp; this.cantitate=c; this.pretTotal=p; this.gramajDeScazut=g;
        }
    }

    public static void main(String[] args) { launch(args); }
}