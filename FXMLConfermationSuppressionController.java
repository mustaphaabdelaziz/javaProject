/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dgsescuela.Etudiants;


import com.jfoenix.controls.JFXButton;
import static dgsescuela.LoginPackage.loginController.rootAccueil;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dgsescuela.AccueilPackage.FXMLAccueilController;
import dgsescuela.DBConnection;
import static dgsescuela.LoginPackage.loginController.adminStage;
import static dgsescuela.LoginPackage.loginController.rootAccueil;
import static dgsescuela.LoginPackage.loginController.sceneAccueil;
import dgsescuela.Modele.ModeleEtudiantsStatic;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author unknown
 */
public class FXMLConfermationSuppressionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Connection conn;
    PreparedStatement pst = null;
    ResultSet rs = null;

    @FXML
    private JFXTextField Nom_utilisateur;

    @FXML
    private JFXPasswordField mot_passe;

    @FXML
    private Button btnClose;

    ModeleEtudiantsStatic CurrentFournisseurStatic = new ModeleEtudiantsStatic();

    @FXML
    private void SupprimerCondidat() {
        try {
            
            String sql = "DELETE  FROM `etudiant`  WHERE IdEtud ='" + CurrentFournisseurStatic.getIdEtudiant()+ "'";
            
           pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            pst.close();
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();

        }
    }
@FXML
void SupprimerInscription (){
    String sqlS=null;
    String v=null;
    try {
       
            String s="Delete from inscription where IDInscription ="+CurrentFournisseurStatic.getIdEtudiant()+"'";
             pst = conn.prepareStatement(s);
            pst.executeUpdate();
            pst.close();
            rs.close();
             } catch (SQLException ex) {
            ex.printStackTrace();

        }
    SupprimerCondidat();
}
    @FXML
    void Valider() {
        //on peut eviter cette methode pour eviter la duplication
        SupprimerInscription();
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO

            conn = DBConnection.EtablirConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLConfermationSuppressionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void quit() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();

    }

   private void actualiser() throws IOException{
       
       quit();
         
            rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Etudiants/FXMLEtudiants.fxml"));
            sceneAccueil = new Scene(rootAccueil);

            adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);


    }
   
   
    private void ConfermationSuppression() throws SQLException, IOException{
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Attention");
            alert.setHeaderText("Risque de Suppression des Données  !!!");
            alert.setContentText("Cette action va supprimer le Condidat N°{{ " + CurrentFournisseurStatic+ "}}");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() != ButtonType.OK) {} 
            else {
                Valider();
                actualiser();

                pst.close();
                rs.close();
            }
    }
    
    @FXML
    void Confermer(MouseEvent event) throws SQLException, IOException {

        
          try {
          String sql = "SELECT * FROM `Users` WHERE (Nom='"+Nom_utilisateur.getText()+"' or Email='"+Nom_utilisateur.getText()+"') and password='"+mot_passe.getText()+"'";
            pst = conn.prepareStatement(sql);
          rs = pst.executeQuery();
      // if email et psw juste appeler confirmationSupprission
          if(rs.next()){
            ConfermationSuppression();
          }
          else{

               Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur :   ");
                alert.setContentText("Le mot de passe ou l'utilisateur sont incorrect!!!");
                alert.showAndWait();

          }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
          

        pst.close();
        rs.close();

    }

}
