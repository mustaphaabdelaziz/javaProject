/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dgsescuela.Etudiants;

import com.jfoenix.controls.JFXButton;
import dgsescuela.DBConnection;
import static dgsescuela.LoginPackage.loginController.adminStage;
import static dgsescuela.LoginPackage.loginController.rootAccueil;
import static dgsescuela.LoginPackage.loginController.sceneAccueil;
import dgsescuela.Modele.ModeleEtudiants;
import dgsescuela.Modele.ModeleEtudiantsStatic;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hdegd
 */
public class FXMLEtudiantsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    //ne3gz nektb kolch

       @FXML
    private ObservableList<ModeleEtudiants> ListeEtudiant;
    
   @FXML
    private TableView<ModeleEtudiants> fxTableEtudiant;
     @FXML
    private TableColumn<ModeleEtudiants, String> fxIdEtudiant;
      @FXML
    private TableColumn<ModeleEtudiants, String> fxDatecolumn;

    @FXML
    private TableColumn<ModeleEtudiants, String> fxNom;

    @FXML
    private TableColumn<ModeleEtudiants, String> fxPrenom;

    @FXML
    private TableColumn<ModeleEtudiants, String> fxTele;

    @FXML
    private TableColumn<ModeleEtudiants, String> fxEmail;
/*@FXML
    private JFXButton fxRechercherButton;*/
@FXML
        private TextField fxRechercher;
    Connection conn;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public static Stage StageEtudiant= new Stage();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        initialisationData();
    }    
    
    
    
     public void initialisationData(){
         
           try {
               //initialisation de la liste d'objets etudiants
               ListeEtudiant = FXCollections.observableArrayList();
               
               //Connection
               conn= DBConnection.EtablirConnection();
               
               // initialisation de la table fx des etudiants
               initTable();
               
               // récupérer les données depuis la bdd
               uploadTableEtudiant();
               
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(FXMLEtudiantsController.class.getName()).log(Level.SEVERE, null, ex);
           }
     }
     
       private void initTable() {

        fxIdEtudiant.setCellValueFactory(new PropertyValueFactory<>("idEtudiant"));
         fxDatecolumn.setCellValueFactory(new PropertyValueFactory<>("DateAjout"));
        fxNom.setCellValueFactory(new PropertyValueFactory<>("NomEtudiant"));
        fxPrenom.setCellValueFactory(new PropertyValueFactory<>("PrenomEtudiant"));
        fxTele.setCellValueFactory(new PropertyValueFactory<>("TelEtudiant"));
        fxEmail.setCellValueFactory(new PropertyValueFactory<>("EmailEtudiant"));
    
    }
       
       public void uploadTableEtudiant(){
           
           try {
               ListeEtudiant.clear();
               
               String sql = "select IdEtud,Date_Ajout,NomEtud,PrenomEtud,Tel,Email from `etudiant`" ;
               
               fxTableEtudiant.getItems().clear();
               pst = conn.prepareStatement(sql);
               rs = pst.executeQuery();
               
               while(rs.next()){
                   ModeleEtudiants newEtudiant = new ModeleEtudiants();
                   newEtudiant.setIdEtudiant(rs.getString(1));
                   newEtudiant.setDateAjout(rs.getString(2));
                   newEtudiant.setNomEtudiant(rs.getString(3));
                   newEtudiant.setPrenomEtudiant(rs.getString(4));
                   newEtudiant.setTelEtudiant(rs.getString(5));
                   newEtudiant.setEmailEtudiant(rs.getString(6));
                   
                   ListeEtudiant.add(newEtudiant);
                   System.out.println("********************* date: "+newEtudiant.getDateAjout());
               }
               fxTableEtudiant.setItems(ListeEtudiant);
               pst.close();
               rs.close();
           } catch (SQLException ex) {
               Logger.getLogger(FXMLEtudiantsController.class.getName()).log(Level.SEVERE, null, ex);
           }
           
       }

       
         ModeleEtudiants CurrentObjet= new ModeleEtudiants();
    ModeleEtudiantsStatic CurrentObjetStatic = new ModeleEtudiantsStatic();
  
    private void selectCondidat(){
          CurrentObjet=(ModeleEtudiants)fxTableEtudiant.getSelectionModel().getSelectedItem();

          
          if(CurrentObjet==null || CurrentObjet.getIdEtudiant().equals(""))
          {
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur :   ");
                alert.setContentText("Veuillez Selectionner un étudiant d'abord!!!");
                alert.showAndWait();
          }else{
           CurrentObjetStatic.setIdEtudiant(CurrentObjet.getIdEtudiant());
               CurrentObjetStatic.setDate(CurrentObjet.getDateAjout());
           CurrentObjetStatic.setNomEtudiant(CurrentObjet.getNomEtudiant());
            CurrentObjetStatic.setPrenomEtudiant(CurrentObjet.getPrenomEtudiant());
           CurrentObjetStatic.setTelEtudiant(CurrentObjet.getTelEtudiant());
           CurrentObjetStatic.setEmailEtudiant(CurrentObjet.getEmailEtudiant());
     
          }

   
    }
/*------------------------------------------------Fenetre----------------------------------------*/

   public void FenetreEnseignant() throws IOException {

        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Enseignants/FXMLEnseignants.fxml"));
        sceneAccueil = new Scene(rootAccueil);

            adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);

    }

   
   public void FenetreEtudiant() throws IOException {

        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Etudiants/FXMLEtudiants.fxml"));
        sceneAccueil = new Scene(rootAccueil);

            adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);


    }

   
   public void FenetreFormation() throws IOException {


        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Formations/FXMLFormations.fxml"));
        sceneAccueil = new Scene(rootAccueil);

      
            adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);


    }
   
   
   public void FenetreFrais() throws IOException {


        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/caisse/FXMLCaisse.fxml"));
        sceneAccueil = new Scene(rootAccueil);

      
            adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);
    }

   @FXML
   public void FenetreInscription() throws IOException  {


        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Inscriptions/FXMLInscriptions.fxml"));
        sceneAccueil = new Scene(rootAccueil);

      
            adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);

    }
   
   
   public void FenetreStatistiques() throws IOException {


        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/presence/FXMLPresence.fxml"));
        sceneAccueil = new Scene(rootAccueil);

      
            adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);



    }

   
   
   public void FenetreEmploisDuTemps() throws IOException {


        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/preinscription/FXMLPInscriptions.fxml"));
        sceneAccueil = new Scene(rootAccueil);

      
            adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);

    }

   public void FenetreAccueil() throws IOException {


        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/AccueilPackage/FXMLAccueil.fxml"));
        sceneAccueil = new Scene(rootAccueil);

      
            adminStage.setTitle("EmploisDuTemps");
            adminStage.setScene(sceneAccueil);
            adminStage.show();

    }
   
   /*------------------------------------------------Fenetre----------------------------------------*/
   
/*-------------------------------------------------Méthodes----------------------------------------*/

   public void AjouterEtudiant() throws IOException {
      
       Parent newRoot;
       Scene newScene;
       newRoot = FXMLLoader.load(getClass().getResource("/dgsescuela/Etudiants/FXMLAjouter.fxml"));
       newScene = new Scene(newRoot);

       StageEtudiant= new Stage();;
       StageEtudiant.setTitle("Etudiant");
       StageEtudiant.setScene(newScene);
       StageEtudiant.showAndWait();
       
       

    }
    
     public void SupprimerEtudiant() throws IOException {

   selectCondidat();
   
        Parent newRoot;
        Scene newScene;
       newRoot = FXMLLoader.load(getClass().getResource("/dgsescuela/Etudiants/FXMLConfermationSuppression.fxml"));
       newScene = new Scene(newRoot);

       StageEtudiant= new Stage();;
       StageEtudiant.setTitle("Etudiant");
       StageEtudiant.setScene(newScene);
       StageEtudiant.showAndWait();

    }
     
     public void ModifierEtudiant() throws IOException {



    selectCondidat();
   
       Parent newRoot;
       Scene newScene;
       newRoot = FXMLLoader.load(getClass().getResource("/dgsescuela/Etudiants/FXMLModifier.fxml"));
       newScene = new Scene(newRoot);

       StageEtudiant= new Stage();;
       StageEtudiant.setTitle("Etudiant");
       StageEtudiant.setScene(newScene);
       StageEtudiant.showAndWait();

    } 

     @FXML
     public void Recherche() throws SQLException{

        
        if(fxRechercher==null || fxRechercher.getText().equals(""))
        {
            System.out.println("hhhhh");
            uploadTableEtudiant();
            
        }else{
        ListeEtudiant.clear();
            RechercheDonnee();
    
  
        }
    }


 @FXML
     public void RechercheDonnee() throws SQLException{       
          String sql="select IdEtud,Date_Ajout,NomEtud,PrenomEtud,Tel,Email from etudiant where IdEtud ='"+fxRechercher.getText().toLowerCase()+"' OR NomEtud= '"+fxRechercher.getText().toLowerCase()+"' OR PrenomEtud= '"+fxRechercher.getText().toLowerCase()+"' OR Tel= '"+fxRechercher.getText().toLowerCase()+"' OR Date_Ajout = '"+fxRechercher.getText()+"' OR Email = '"+fxRechercher.getText()+"'";
            
        try {
            
       fxTableEtudiant.getItems().clear();
        pst = conn.prepareStatement(sql);
        rs = pst.executeQuery();
        while (rs.next()) {
            ModeleEtudiants Etudiant = new ModeleEtudiants();
            
            
            Etudiant.setIdEtudiant(rs.getString(1));
            Etudiant.setDateAjout(rs.getString(2));
            Etudiant.setNomEtudiant(rs.getString(3));
            Etudiant.setPrenomEtudiant(rs.getString(4));
            Etudiant.setTelEtudiant(rs.getString(5));
            Etudiant.setEmailEtudiant(rs.getString(6));
           
            
            
            ListeEtudiant.add(Etudiant);
            fxTableEtudiant.setItems(ListeEtudiant);
        }
            System.out.println("l'étudiant est la ");
            pst.close();
           rs.close();
          
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLEtudiantsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
     
     
}