package rocks.zipcode.calcskin;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rocks.zipcode.calcskin.CalcEngine;
import sun.print.CUPSPrinter;

import java.util.HashMap;
import java.util.Map;

// a simple JavaFX calculator.
public class CalcSkin extends Application {

    public static void main(String[] args){
        launch(args);
    }
    private static final String[][] template = {
            { "7", "8", "9", "/" },
            { "4", "5", "6", "*" },
            { "1", "2", "3", "-" },
            { "0", "c", "=", "+" },
            { "√", "^", "~", "²"}
    };

    private final Map<String, Button> accelerators = new HashMap<>();

    private DoubleProperty previousValue = new SimpleDoubleProperty();
    private DoubleProperty currentValue = new SimpleDoubleProperty();
    private CalcEngine calcEngine = new CalcEngine();

    private enum Op { NOOP, ADD, SUBTRACT, MULTIPLY, DIVIDE, SQUAREROT, SQUARE, EXPONENT, INVERT}

    private Op curOp   = Op.NOOP;
    private Op stackOp = Op.NOOP;

    public static void launchCalc(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        final TextField screen  = createScreen();
        final TilePane  buttons = createButtons();

        stage.setTitle("Calc");
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        stage.setScene(new Scene(createLayout(screen, buttons)));
        stage.show();
    }

    private VBox createLayout(TextField screen, TilePane buttons) {
        final VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: silver; -fx-padding: 20; -fx-font-size: 20;");
        layout.getChildren().setAll(screen, buttons);
        handleAccelerators(layout);
        screen.prefWidthProperty().bind(buttons.widthProperty());
        return layout;
    }

    private void handleAccelerators(VBox layout) {
        layout.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                Button activated = accelerators.get(keyEvent.getText());
                if (activated != null) {
                    activated.fire();
                }
            }
        });
    }

    private TextField createScreen() {
        final TextField screen = new TextField();
        screen.setStyle("-fx-background-color: aquamarine;");
        screen.setAlignment(Pos.CENTER_RIGHT);
        screen.setEditable(false);
        screen.textProperty().bind(Bindings.format("%.0f", currentValue));
        return screen;
    }

    private TilePane createButtons() {
        TilePane buttons = new TilePane();
        buttons.setVgap(7);
        buttons.setHgap(7);
        buttons.setPrefColumns(template[0].length);
        for (String[] r: template) {
            for (String s: r) {
                buttons.getChildren().add(createButton(s));
            }
        }
        return buttons;
    }

    private Button createButton(final String s) {
        Button button = makeStandardButton(s);

        if (s.matches("[0-9]")) {
            makeNumericButton(s, button);
        } else {
            final ObjectProperty<Op> triggerOp = determineOperand(s);
            if (triggerOp.get() != Op.NOOP) {
                makeOperandButton(button, triggerOp);
            } else if ("c".equals(s)) {
                makeClearButton(button);
            } else if ("=".equals(s)) {
                makeEqualsButton(button);
            }
        }

        return button;
    }

    private ObjectProperty<Op> determineOperand(String s) {
        final ObjectProperty<Op> triggerOp = new SimpleObjectProperty<>(Op.NOOP);
        switch (s) {
            case "+": triggerOp.set(Op.ADD);      break;
            case "-": triggerOp.set(Op.SUBTRACT); break;
            case "*": triggerOp.set(Op.MULTIPLY); break;
            case "/": triggerOp.set(Op.DIVIDE);   break;
            case "²": triggerOp.set(Op.SQUARE);   break;
            case "√": triggerOp.set(Op.SQUAREROT);  break;
            case "^": triggerOp.set(Op.EXPONENT);   break;
            case "~": triggerOp.set(Op.INVERT);     break;
        }
        return triggerOp;
    }

    private void makeOperandButton(Button button, final ObjectProperty<Op> triggerOp) {
        button.setStyle("-fx-base: lightgray;");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                curOp = triggerOp.get();
            }
        });
    }

    private Button makeStandardButton(String s) {
        Button button = new Button(s);
        button.setStyle("-fx-base: beige;");
        accelerators.put(s, button);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        return button;
    }

    private void makeNumericButton(final String s, Button button) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (curOp == Op.NOOP) {
                    currentValue.set(currentValue.get() * 10 + Integer.parseInt(s));
                } else {
                    previousValue.set(currentValue.get());
                    currentValue.set(Integer.parseInt(s));
                    stackOp = curOp;
                    curOp = Op.NOOP;
                }
            }
        });
    }

    private void makeClearButton(Button button) {
        button.setStyle("-fx-base: mistyrose;");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                currentValue.set(0);
            }
        });
    }

    private void makeEqualsButton(Button button) {
        button.setStyle("-fx-base: ghostwhite;");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                switch (stackOp) {
                    case ADD:      currentValue.set(calcEngine.addition(previousValue.get(), currentValue.get())); break;
                    case SUBTRACT: currentValue.set(calcEngine.subtraction(previousValue.get(), currentValue.get())); break;
                    case MULTIPLY: currentValue.set(calcEngine.multiplication(previousValue.get(), currentValue.get())); break;
                    case DIVIDE:   currentValue.set(calcEngine.division(previousValue.get(), currentValue.get())); break;
                    case SQUARE:   currentValue.set(calcEngine.square(previousValue.get())); break;
                    case SQUAREROT: currentValue.set(calcEngine.squareroot(previousValue.get()));   break;
                    case EXPONENT:  currentValue.set(calcEngine.exponent(previousValue.get(), currentValue.get())); break;
                    case INVERT:    currentValue.set(calcEngine.invert(previousValue.get()));   break;

                }
            }
        });
    }
}


