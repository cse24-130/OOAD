import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main JavaFX Application class for Banking System
 */
public class BankingApplication extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root, 800, 500);
        try {
            String css = getClass().getResource("styles.css").toExternalForm();
            if (css != null) {
                scene.getStylesheets().add(css);
            }
        } catch (Exception e) {
            System.err.println("Warning: Could not load stylesheet: " + e.getMessage());
        }
        
        primaryStage.setTitle("Milli Banking - Login");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(500);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

