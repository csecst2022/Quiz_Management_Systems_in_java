
import Project.Connections;
import java.awt.HeadlessException;
import java.sql.*;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Kwizera
 */
public class QuestionsAndAnswers extends Application {
Stage primaryStage=new Stage();
    @Override
    public void start(Stage primaryStage) {

        try {
            StackPane root = new StackPane();
            root.getChildren().add(getAnswers());

            Scene scene = new Scene(root, 600, 400);

            primaryStage.setTitle("Provide the quiz!");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (NullPointerException e) {

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @SuppressWarnings("empty-statement")
    VBox getAnswers() {
        VBox AnswersPane = new VBox();

        AnswersPane.setPadding(new Insets(20, 12.5, 20, 14.5));

        ArrayList<String> RealAnswer = new ArrayList<>();
        
        ArrayList<String> ProposedAnswers = new ArrayList<>();//list of proposed answers
        Button AddQuestion = new Button("Add new question");
        AnswersPane.getChildren().add(AddQuestion);
        TextField questioning = new TextField();// the question to be given
        questioning.setPromptText("Enter the question " );
        
        
        
        
        AddQuestion.setOnAction((ActionEvent e)
                ->//event handeler when start quiz giving is presed
                {
                    AnswersPane.getChildren().add(questioning);
                    String Choices = JOptionPane.showInputDialog("How many proposed answers to this question ?");//promptingteacher to mention the proposed answers to the question
                    int number = Integer.parseInt(Choices);
                    Label NumberOfchoices = new Label("Fill " + number + " proposed answers!");
                    AnswersPane.getChildren().add(NumberOfchoices);
                    Button Save = new Button("Save");//save button to save all information into database
                    
                    ArrayList<CheckBox> choices = new ArrayList<>();
                    ArrayList<TextField> answers = new ArrayList<>();

                    for (int i = 0; i < number; i++) {
                        TextField posibleAnswers = new TextField();//textField where the proposed answers are writen
                        posibleAnswers.setPromptText("Enter the choice " + (i + 1));

                        CheckBox choose = new CheckBox("choose correct answer " + (i + 1));  //checkbox to tick where the real answers are

                        //inputs.add(input);
                        AnswersPane.getChildren().add(choose);
                        choices.add(choose);
                        AnswersPane.getChildren().add(posibleAnswers);
                        answers.add(posibleAnswers);
                        
                    }
                        
                            
                            
                            
                        
                        
                        
                       // while(!endquiz.isPressed())
                              //  {
                        Save.setOnAction((ActionEvent SaveEvent)
                                -> {
                               
                                    int confirm = JOptionPane.showConfirmDialog(null, "Do you want to save this question", "selected", JOptionPane.YES_NO_OPTION);//confirming that you want to save
                                    if (confirm == 0)//if you choose yes
                                    {

                                        String Problem = questioning.getText();//storing the question from textfield to a string
                                        for (int v = 0; v < number; v++) {
                                        ProposedAnswers.add(answers.get(v).getText());//gathering all proposed answers into an array
                                        
                                        if (choices.get(v).isSelected()) //what happens when the answer is selected
                                        {
                                            RealAnswer.add(answers.get(v).getText());//storing all real answers into an array 

                                        }
                                        }
                                        try {
                                             Connection con=Connections.getcon();
                                            Statement statement =con.createStatement(); //creating statement
                                            statement.executeUpdate("INSERT INTO quizes(Question) VALUES ('" + Problem + "');");//sql queries
                                             String coma=" ";
                                            /*StringBuilder getproposedanswers=new StringBuilder();
                                            for (String proposed : ProposedAnswers) {
                                                getproposedanswers.append(coma);
                                                getproposedanswers.append(proposed);
                                               coma=","; 

                                            }
                                           */
                                            statement.executeUpdate("UPDATE quizes SET ProposedAnswers = ('" + ProposedAnswers.toString() + "')where Question='" + Problem + "';");
                                           /* StringBuilder getreal=new StringBuilder();
                                            for (String Real : RealAnswer) {
                                             
                                              getreal.append(coma);
                                               getreal.append(Real);
                                                coma=",";
                                            }
                                            */
                                            statement.executeUpdate("UPDATE quizes SET Answer=('" + RealAnswer.toString() + "')where Question='" + Problem + "';");

                                            JOptionPane.showMessageDialog(null, "Saved successfully !");//if data are saved

                                        } catch (HeadlessException | SQLException ex) {
                                            JOptionPane.showMessageDialog(null, ex.getMessage());//if not saved
                                        }
                                     /* questioning.clear();
                                     ProposedAnswers.clear(); 
                                      RealAnswer.clear(); 
                                      choices.clear();
                                      questioning.setPromptText("Enter an other question ");
                                        for (int v = 0; v < number; v++)
                                       {
                                            
                                       answers.get(v).clear();
                                         choices.get(v).setSelected(false);
                                         
                                   } 
*/ 

                                        
                                    }
                                
                                    //Save.setDisable(true);//setting save button disabled
                                });
                               // }
                               Button Addnew=new Button("Add new");
                               Addnew.setOnAction(add->{
                                    primaryStage.close();
                         StackPane root = new StackPane();
            root.getChildren().add(getAnswers());

            Scene scene = new Scene(root, 600, 400);

            primaryStage.setTitle("Proved the quiz!");
            primaryStage.setScene(scene);
            primaryStage.show();
                               });
                    
Button Exit=new Button("Exit");
Exit.setOnAction(exit->{
  int n=  JOptionPane.showConfirmDialog(null, "Do you realy want to stop giving quiz?");
    if(n==0){
       
    System.exit(0);
    }
    if(n==1)
    { 
       
        JOptionPane.showMessageDialog(null, "Stay tuned to giving quiz!");
    }
});
                    AnswersPane.getChildren().add(Save);
                 
               
AnswersPane.getChildren().add(Addnew);
AnswersPane.getChildren().add(Exit);
                  AddQuestion.setDisable(true);//setting start quiz button disabled
                                
                });
        
        

        return AnswersPane;
    }
}