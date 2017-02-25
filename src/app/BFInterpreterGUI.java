package app;

import javafx.event.ActionEvent;
import javafx.application.Application;
        import javafx.event.EventHandler;
        import javafx.geometry.Insets;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.control.TextArea;
        import javafx.scene.layout.VBox;
        import javafx.scene.paint.Color;
        import javafx.stage.Stage;
import model.BFInterpreter;

public class BFInterpreterGUI extends Application implements EventHandler<ActionEvent>{
    private final int WIDTH = 600;
    private final int HEIGHT = 300;

    private BFInterpreter bf = new BFInterpreter();

    private Label labelInput;
    private VBox inputBox;
    private TextArea inputField; // allows multi-line text wrapping
    private Button buttonInterpret;

    private Label labelError;
    private Label labelOutput;
    private TextArea outputField;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Brainfuck Interpreter");
        primaryStage.setResizable(true);

        labelInput = new Label("Input:");

        inputBox = new VBox();
        inputBox.setSpacing(10);

        inputField = new TextArea();
        inputField.setPrefHeight(HEIGHT/3);
        inputField.setWrapText(true);
        inputField.setFocusTraversable(false); // so that prompt text appears at launch
        inputField.setPromptText("Enter your command here!");

        buttonInterpret = new Button("Interpret");
        buttonInterpret.setOnAction(this);

        inputBox.getChildren().addAll(inputField,buttonInterpret);

        // label for displaying errors and exceptions
        labelError = new Label();
        labelError.setPadding(new Insets(2,2,2,2));
        labelError.setText("");
        labelError.setTextFill(Color.RED);

        labelOutput = new Label("Output:");

        outputField = new TextArea();
        outputField.setEditable(false);
        outputField.setWrapText(true);
        outputField.setPromptText("Output will be displayed here.");
        outputField.setPrefWidth(WIDTH);

        VBox root = new VBox();
        root.setPadding(new Insets(10,10,10,10));
        root.getChildren().addAll(labelInput, inputBox, labelError,labelOutput,outputField);

        Scene scene = new Scene(root,WIDTH,HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource()==buttonInterpret){
            // clear text
            labelError.setText("");
            outputField.setText("");

            try {
                bf.setString(inputField.getText());
                bf.interpret();
                String result = bf.getOutput();
                outputField.setText(result);
            }
            catch(Exception e){
                labelError.setText("Error: " + e.getMessage());
            }
        }
    }
}
