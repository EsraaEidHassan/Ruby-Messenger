package controller;
// abdelfata7 start
import common.ClientInterface;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

 // abdelfata7 end

// khaled start

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader; 
import common.ServerInterface;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.User;

//khaled end
public class FrontController implements Initializable {
    // abdelfata7 start
    @FXML
    private Label noAccount;
    
    @FXML
    private TextField username;
    

    @FXML
    private TextField password;
    
    @FXML
    private AnchorPane mainAnchorPane;
    
    @FXML
    private Rectangle rectangle;
    Image img = new Image("logo.png");
    

    

    // abdelfata7 end
    
    // khaled start
    private FXMLLoader loader;
    private Stage mainStage;
    private Scene scene;
    private Parent root;
    private ServerInterface serverRef;
    //khaled end

    //Esraa Hassan
    //boolean serverAcceptedTheConnection ;
    //boolean paused = false;
    //Alert mylert;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // abdelfata7 start
        noAccount.getStyleClass().add("label");
        username.getStyleClass().add("username");
        mainAnchorPane.getStyleClass().add("mainAnchorPane");
        password.getStyleClass().add("password");
        rectangle.setFill(new ImagePattern(img));
        
    
        // abdelfata7 end
        
        // khaled start
        loader = new FXMLLoader();
        try{
            Registry reg = LocateRegistry.getRegistry(2000);
            serverRef = (ServerInterface) reg.lookup("chat");
        }
        catch (RemoteException | NotBoundException ex) {
            showServerError();
        }
        //khaled end
    }
    
    // abdelfata7 start
    
    
    // abdelfata7 end
    
    // khaled start
    @FXML
    public void signInAction(){
        
           
        String userName = this.username.getText();
        String password = this.password.getText();
        if(userName.trim().equals("") || password.trim().equals("") ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("login error");
            alert.setContentText("you must type your username and password to sign in");
            alert.showAndWait();
        }
        else{
            try{
                User user = serverRef.signInUser(userName, password);
                if(user != null){
                    mainStage =(Stage) username.getScene().getWindow();
                    root = loader.load(getClass().getResource("/fxml/UserMainScene.fxml").openStream());
                    MainSceneController mainController = loader.<MainSceneController>getController();
                    ClientInterface clientImpl = new ClientImplementation(mainController);
                    clientImpl.setUser(user);

                    // Esraa Hassan
                    this.serverRef.register(clientImpl);
                    // khaled
                    //send client object to contacts scene controller
                    mainController.setClient(clientImpl);
                    mainController.setServer(serverRef);
                    System.out.println(clientImpl.getUser().getUsername());
                    scene = new Scene(root);
                    mainStage.setScene(scene);
                                
                    /*Task task = new Task<Void>() { // old code (accept connection)
                        @Override public Void call() {
                            try {
                                //updateMessage("Waiting for the server . . .");
                                System.out.println("server is deciding to accept or reject your connection ..... ");
                                paused = true;
                                while(!serverRef.getDecidedState()){
                                    //wait until server decide
                                }
                                // srever done deciding
                                serverAcceptedTheConnection = serverRef.getAcceptedState();
                                System.out.println("done deciding");
                                //updateMessage("Finished.");
                                return null;
                            } catch (RemoteException ex) {
                                Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            return null;
                        }
                        @Override protected void cancelled() {
                            super.cancelled();
                        }
                    };
                    //ProgressBar bar = new ProgressBar();
                    //bar.progressProperty().bind(task.progressProperty());
                    task.setOnSucceeded((e) -> {
                        if(serverAcceptedTheConnection){
                            System.out.println("Sever accepted your connection");
                            try {
                                //send client object to contacts scene controller
                                mainController.setClient(clientImpl);
                                mainController.setServer(serverRef);
                                System.out.println(clientImpl.getUser().getUsername());
                                scene = new Scene(root);
                                mainStage.setScene(scene);
                            } catch (IOException ex) {
                                Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }else{
                            System.out.println("Sever rejected your connection");
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Server Refused your connection");
                            alert.setContentText("Please try again later");
                            alert.showAndWait();
                        }
                       task.cancel();
                    });
                    new Thread(task).start();*/

                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("login error");
                    alert.setContentText("invalid userName or password");
                    alert.showAndWait();
                }
            }
            catch(RemoteException  ex){
                showServerError();
            } catch (IOException ex) {
                Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
    }
    @FXML
    public void signUpAction(){
            try {
                /*change scene to sign-up scene*/
                root = loader.load(getClass().getResource("/fxml/signup.fxml").openStream());
                SignupController sUpController = loader.<SignupController>getController();
                sUpController.setServer(serverRef);
                // Esraa Hassan start
                sUpController.populateCountriesInComboBox(); // I worte it here as I want the server to be initialized first
                // Esraa Hassan end
                scene = new Scene(root);
                mainStage =(Stage) this.username.getScene().getWindow();
                mainStage.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    private void showServerError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("server error");
        alert.setContentText("server is down !");
        alert.showAndWait();
    }
    //khaled end
}
