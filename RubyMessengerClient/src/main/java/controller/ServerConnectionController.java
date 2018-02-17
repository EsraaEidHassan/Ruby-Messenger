package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import common.ServerInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author khaled
 */
public class ServerConnectionController implements Initializable {

    private Stage mStage;
    private double xOffset;
    private double yOffset;
    
    private FXMLLoader loader;
    private Map<String , String> servers;
    private ServerInterface serverRef;
    @FXML
    private JFXTextField ipField;
    @FXML
    private JFXButton connectBtn;
    @FXML
    private JFXButton closeImgBtn;
    @FXML
    private JFXButton minimizeImgBtn;
    @FXML
    private AnchorPane paneBar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initController();
                loader = new FXMLLoader();
                connectBtn.requestFocus();
                /*getServers();
                /////fill combo box with servers//////
                Iterator it = servers.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    serversBox.getItems().add(pair.getKey());
                    it.remove();
                }*/
            }
        });
        
    }    
    
    @FXML
    public void holdChatWindow(MouseEvent event) {
        xOffset = mStage.getX() - event.getScreenX();
        yOffset = mStage.getY() - event.getScreenY();
    }

    @FXML
    public void dragChatWindow(MouseEvent event) {
        mStage.setX(event.getScreenX() + xOffset);
        mStage.setY(event.getScreenY() + yOffset);
    }
    @FXML
    public void connect(){
        if(ipField.getText().trim().equals("") || ipField.getText().trim().length()<8){ //temporary validation
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("connection error");
            alert.setContentText("you must write valid server ip");
            alert.showAndWait();
        }
        else{
            String ServerIp = ipField.getText().trim();
            //get registry of that ip --> get serverRef -->send it to frontController
            Registry reg;
            try {
                reg = LocateRegistry.getRegistry(ServerIp,2000);
                serverRef = (ServerInterface) reg.lookup("chat");
            } catch (RemoteException | NotBoundException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("connection error");
                alert.setContentText("this server is down");
                alert.showAndWait();
            }
            if(serverRef != null){
                try {
                    Parent root = loader.load(getClass().getResource("/fxml/Login.fxml").openStream());
                    FrontController controller = loader.<FrontController>getController();
                    controller.setServer(serverRef);
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add("styles/styles.css");
                    mStage = (Stage) paneBar.getScene().getWindow();
                    mStage.close();
                    Stage login = new Stage();
                    login.initStyle(StageStyle.UNDECORATED);
                    login.setScene(scene);
                    login.show();
                } catch (IOException ex) {
                    Logger.getLogger(ServerConnectionController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    private void initController() {
        mStage = (Stage) paneBar.getScene().getWindow();
        connectBtn.requestFocus();
         
        closeImgBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mStage.close();
            }
        });

        minimizeImgBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mStage.setIconified(true);
                connectBtn.requestFocus();
            }
        });
        
    }
    /*private void getServers(){
        servers = new HashMap<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuild = factory.newDocumentBuilder();
            FileInputStream file = (FileInputStream)this.getClass().getClassLoader().getResourceAsStream("/Resources/xml/servers.xml");
            Document doc = docBuild.parse(file);
            NodeList serverNodes =  doc.getChildNodes();
            for(int i=0; i<serverNodes.getLength(); i++){
                Node server = serverNodes.item(i);
                if(server.getNodeType() == Node.ELEMENT_NODE){
                    String serverName = server.getAttributes().item(0).getNodeValue();
                    String serverIp = server.getAttributes().item(1).getNodeValue();
                    servers.put(serverName,serverIp);
                }
                
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(ServerConnectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }*/
}
