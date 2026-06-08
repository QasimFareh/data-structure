import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;

public class Main extends Application {

    // ===================== الكلاسات =====================
    private Converter converter = new Converter();
    private Evaluator evaluator = new Evaluator();
    private Validator validator = new Validator();
    private FileManager fileManager = new FileManager();

    // ===================== الـ Data =====================
    private LinkedList<String> operands = new LinkedList<>();
    private LinkedList<String> operators = new LinkedList<>();
    private LinkedList<String> precedence = new LinkedList<>();

    // ===================== الـ Controls =====================
    private RadioButton customMode, conventionalMode;
    private ComboBox<String> inputType, operandsCombo, operatorsCombo;
    private TextField expressionField, outInfix, outPostfix, outPrefix, outResult;
    private Label resultLabel;
    private VBox customPanel;
    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        stage.setTitle("Notation Conversion System");

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");

        root.getChildren().addAll(
                buildModePanel(),
                buildInputPanel(),
                buildOutputPanel(),
                buildCustomPanel(),
                buildReportPanel()
        );

        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);

        Scene scene = new Scene(scroll, 700, 600);
        stage.setScene(scene);
        stage.show();

        // اضبط الـ UI حسب الـ mode الافتراضي
        updateMode();
    }

    // ===================== Mode Panel =====================
    private HBox buildModePanel() {
        HBox box = new HBox(20);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 8; -fx-background-radius: 8;");

        Label modeLabel = new Label("Mode:");
        customMode = new RadioButton("Custom Notation");
        conventionalMode = new RadioButton("Conventional Notation");

        ToggleGroup group = new ToggleGroup();
        customMode.setToggleGroup(group);
        conventionalMode.setToggleGroup(group);
        customMode.setSelected(true);

        group.selectedToggleProperty().addListener((obs, oldVal, newVal) -> updateMode());

        box.getChildren().addAll(modeLabel, customMode, conventionalMode);
        return box;
    }

    // ===================== Input Panel =====================
    private VBox buildInputPanel() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 8; -fx-background-radius: 8;");

        inputType = new ComboBox<>();
        inputType.getItems().addAll("Infix", "Postfix", "Prefix");
        inputType.setValue("Infix");

        expressionField = new TextField();
        expressionField.setPromptText("Enter expression...");

        Button convertBtn = new Button("Convert");
        Button clearBtn = new Button("Clear");

        convertBtn.setOnAction(e -> convert());
        clearBtn.setOnAction(e -> clearAll());

        HBox inputRow = new HBox(10);
        inputRow.setAlignment(Pos.CENTER_LEFT);
        inputRow.getChildren().addAll(new Label("Input type:"), inputType);

        HBox exprRow = new HBox(10);
        exprRow.setAlignment(Pos.CENTER_LEFT);
        exprRow.getChildren().addAll(new Label("Expression:"), expressionField);
        HBox.setHgrow(expressionField, Priority.ALWAYS);

        HBox btnRow = new HBox(10);
        btnRow.getChildren().addAll(convertBtn, clearBtn);

        box.getChildren().addAll(inputRow, exprRow, btnRow);
        return box;
    }

    // ===================== Output Panel =====================
    private VBox buildOutputPanel() {
        VBox box = new VBox(8);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 8; -fx-background-radius: 8;");

        outInfix = new TextField(); outInfix.setEditable(false); outInfix.setPromptText("—");
        outPostfix = new TextField(); outPostfix.setEditable(false); outPostfix.setPromptText("—");
        outPrefix = new TextField(); outPrefix.setEditable(false); outPrefix.setPromptText("—");
        outResult = new TextField(); outResult.setEditable(false); outResult.setPromptText("—");
        resultLabel = new Label("Result:");

        box.getChildren().addAll(
                buildOutputRow("Infix:", outInfix),
                buildOutputRow("Postfix:", outPostfix),
                buildOutputRow("Prefix:", outPrefix),
                buildOutputRow(resultLabel, outResult)
        );

        return box;
    }

    private HBox buildOutputRow(String label, TextField field) {
        return buildOutputRow(new Label(label), field);
    }

    private HBox buildOutputRow(Label label, TextField field) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        label.setMinWidth(60);
        HBox.setHgrow(field, Priority.ALWAYS);
        row.getChildren().addAll(label, field);
        return row;
    }

    // ===================== Custom Panel =====================
    private VBox buildCustomPanel() {
        customPanel = new VBox(10);
        customPanel.setPadding(new Insets(10));
        customPanel.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 8; -fx-background-radius: 8;");

        Label title = new Label("Custom Configuration");
        title.setStyle("-fx-font-weight: bold;");

        operandsCombo = new ComboBox<>();
        operandsCombo.setMaxWidth(Double.MAX_VALUE);
        operandsCombo.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) editItem("operands");
        });

        operatorsCombo = new ComboBox<>();
        operatorsCombo.setMaxWidth(Double.MAX_VALUE);
        operatorsCombo.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) editItem("operators");
        });

        Button addOperand = new Button("+");
        Button loadOperands = new Button("Load File");
        Button addOperator = new Button("+");
        Button loadOperators = new Button("Load File");

        addOperand.setOnAction(e -> addItem("operands"));
        loadOperands.setOnAction(e -> loadFile("operands"));
        addOperator.setOnAction(e -> addItem("operators"));
        loadOperators.setOnAction(e -> loadFile("operators"));

        HBox operandsRow = new HBox(8);
        HBox.setHgrow(operandsCombo, Priority.ALWAYS);
        operandsRow.setAlignment(Pos.CENTER_LEFT);
        operandsRow.getChildren().addAll(new Label("Operands:"), operandsCombo, addOperand, loadOperands);

        HBox operatorsRow = new HBox(8);
        HBox.setHgrow(operatorsCombo, Priority.ALWAYS);
        operatorsRow.setAlignment(Pos.CENTER_LEFT);
        operatorsRow.getChildren().addAll(new Label("Operators:"), operatorsCombo, addOperator, loadOperators);

        Label hint = new Label("Double-click on an item to edit or delete it");
        hint.setStyle("-fx-text-fill: #888; -fx-font-size: 11;");

        customPanel.getChildren().addAll(title, operandsRow, operatorsRow, hint);
        return customPanel;
    }

    // ===================== Report Panel =====================
    private HBox buildReportPanel() {
        HBox box = new HBox();
        Button reportBtn = new Button("Generate Report");
        reportBtn.setOnAction(e -> generateReport());
        box.getChildren().add(reportBtn);
        return box;
    }

    // ===================== Logic =====================
    private void updateMode() {
        boolean isCustom = customMode.isSelected();
        customPanel.setVisible(isCustom);
        customPanel.setManaged(isCustom);
        resultLabel.setVisible(!isCustom);
        outResult.setVisible(!isCustom);
        resultLabel.setManaged(!isCustom);
        outResult.setManaged(!isCustom);

        if (!isCustom) {
            // Conventional: نحط الأوبراندز والأوبراتورز الافتراضية
            operators = new LinkedList<>();
            operators.insert("+"); operators.insert("-");
            operators.insert("*"); operators.insert("/");
            operators.insert("^");

            precedence = new LinkedList<>();
            precedence.insert("+:1"); precedence.insert("-:1");
            precedence.insert("*:2"); precedence.insert("/:2");
            precedence.insert("^:3");
        }
    }

    private void convert() {
        String expr = expressionField.getText().trim();
        if (expr.isEmpty()) {
            showAlert("Error", "Please enter an expression.");
            return;
        }

        boolean isCustom = customMode.isSelected();
        LinkedList<String> ops = isCustom ? operands : buildNumericOperands(expr);
        LinkedList<String> opers = isCustom ? operators : buildConventionalOperators();
        LinkedList<String> prec = isCustom ? precedence : buildConventionalPrecedence();

        String type = inputType.getValue();
        String infix = "", postfix = "", prefix = "";

        // تحويل حسب نوع الـ input
        if (type.equals("Infix")) {
            if (!validator.isValidInfix(expr, ops, opers)) {
                showAlert("Validation Error", "Invalid infix expression!");
                return;
            }
            infix = expr;
            postfix = converter.infixToPostfix(expr, ops, opers, prec);
            prefix = converter.infixToPrefix(expr, ops, opers, prec);

        } else if (type.equals("Postfix")) {
            if (!validator.isValidPostfix(expr, ops, opers)) {
                showAlert("Validation Error", "Invalid postfix expression!");
                return;
            }
            infix = converter.postfixToInfix(expr, ops, opers);
            postfix = expr;
            prefix = converter.infixToPrefix(infix, ops, opers, prec);

        } else if (type.equals("Prefix")) {
            if (!validator.isValidPrefix(expr, ops, opers)) {
                showAlert("Validation Error", "Invalid prefix expression!");
                return;
            }
            infix = converter.prefixToInfix(expr, ops, opers);
            postfix = converter.infixToPostfix(infix, ops, opers, prec);
            prefix = expr;
        }

        outInfix.setText(infix);
        outPostfix.setText(postfix);
        outPrefix.setText(prefix);

        if (!isCustom) {
            double result = evaluator.evaluatePostfix(postfix);
            if (Double.isNaN(result)) {
                outResult.setText("Error");
                showAlert("Evaluation Error", "Could not evaluate expression!\nCheck for division by zero or invalid tokens.");
            } else {
                outResult.setText(String.valueOf(result));
            }
        }
    }

    private void clearAll() {
        expressionField.clear();
        outInfix.clear(); outPostfix.clear();
        outPrefix.clear(); outResult.clear();
    }

    // ===================== Dialog: Add =====================
    private void addItem(String type) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Add " + (type.equals("operands") ? "Operand" : "Operator"));

        TextField valueField = new TextField();
        valueField.setPromptText(type.equals("operands") ? "e.g. A" : "e.g. $");

        VBox content = new VBox(10);
        content.getChildren().addAll(new Label("Value:"), valueField);

        Spinner<Integer> prioritySpinner = null;
        if (type.equals("operators")) {
            prioritySpinner = new Spinner<>(1, 10, 1);
            content.getChildren().addAll(new Label("Priority:"), prioritySpinner);
        }

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Spinner<Integer> finalSpinner = prioritySpinner;
        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) return valueField.getText().trim();
            return null;
        });

        dialog.showAndWait().ifPresent(val -> {
            if (val.isEmpty()) return;
            if (type.equals("operands")) {
                operands.insert(val);
                operandsCombo.getItems().add(val);
            } else {
                int priority = finalSpinner != null ? finalSpinner.getValue() : 1;
                operators.insert(val);
                precedence.insert(val + ":" + priority);
                operatorsCombo.getItems().add(val + " (priority " + priority + ")");
            }
        });
    }

    // ===================== Dialog: Edit =====================
    private void editItem(String type) {
        ComboBox<String> combo = type.equals("operands") ? operandsCombo : operatorsCombo;
        String selected = combo.getValue();
        if (selected == null) return;

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit " + (type.equals("operands") ? "Operand" : "Operator"));

        TextField valueField = new TextField(selected.split(" ")[0]);

        VBox content = new VBox(10);
        content.getChildren().addAll(new Label("Value:"), valueField);

        Spinner<Integer> prioritySpinner = null;
        if (type.equals("operators")) {
            prioritySpinner = new Spinner<>(1, 10, 1);
            content.getChildren().addAll(new Label("Priority:"), prioritySpinner);
        }

        ButtonType deleteBtn = new ButtonType("Delete", ButtonBar.ButtonData.LEFT);
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, deleteBtn, ButtonType.CANCEL);

        Spinner<Integer> finalSpinner = prioritySpinner;
        dialog.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                String newVal = valueField.getText().trim();
                if (newVal.isEmpty()) return;
                // حذف القديم وإضافة الجديد
                if (type.equals("operands")) {
                    operands.delete(selected);
                    operands.insert(newVal);
                    combo.getItems().set(combo.getSelectionModel().getSelectedIndex(), newVal);
                } else {
                    int priority = finalSpinner != null ? finalSpinner.getValue() : 1;
                    operators.delete(selected.split(" ")[0]);
                    operators.insert(newVal);
                    String newEntry = newVal + " (priority " + priority + ")";
                    combo.getItems().set(combo.getSelectionModel().getSelectedIndex(), newEntry);
                }
            } else if (btn == deleteBtn) {
                if (type.equals("operands")) {
                    operands.delete(selected);
                } else {
                    operators.delete(selected.split(" ")[0]);
                }
                combo.getItems().remove(selected);
            }
        });
    }

    // ===================== Load File =====================
    private void loadFile(String type) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Load " + type + " file");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = chooser.showOpenDialog(primaryStage);
        if (file == null) return;

        if (type.equals("operands")) {
            operands = fileManager.readOperands(file.getAbsolutePath());
            operandsCombo.getItems().clear();
            Node<String> current = operands.getHead();
            while (current != null) {
                operandsCombo.getItems().add(current.getData());
                current = current.getNext();
            }
        } else {
            operators = fileManager.readOperators(file.getAbsolutePath());
            precedence = fileManager.readPrecedence(file.getAbsolutePath());
            operatorsCombo.getItems().clear();
            Node<String> current = operators.getHead();
            while (current != null) {
                operatorsCombo.getItems().add(current.getData());
                current = current.getNext();
            }
        }
    }

    // ===================== Generate Report =====================
    private void generateReport() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save Report");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = chooser.showSaveDialog(primaryStage);
        if (file == null) return;

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println("===== Notation Conversion Report =====");
            writer.println("Mode: " + (customMode.isSelected() ? "Custom" : "Conventional"));
            writer.println("Input Type: " + inputType.getValue());
            writer.println("Expression: " + expressionField.getText());
            writer.println("--------------------------------------");
            writer.println("Infix:   " + outInfix.getText());
            writer.println("Postfix: " + outPostfix.getText());
            writer.println("Prefix:  " + outPrefix.getText());
            if (conventionalMode.isSelected())
                writer.println("Result:  " + outResult.getText());
            writer.println("======================================");
            showAlert("Success", "Report saved successfully!");
        } catch (IOException e) {
            showAlert("Error", "Could not save report: " + e.getMessage());
        }
    }

    // ===================== Helpers =====================
    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    // للـ Conventional: نبني الأوبراندز من الأرقام الموجودة بالـ expression
    private LinkedList<String> buildNumericOperands(String expr) {
        LinkedList<String> list = new LinkedList<>();
        for (String token : expr.trim().split("\\s+")) {
            try {
                Double.parseDouble(token);
                list.insert(token);
            } catch (NumberFormatException ignored) {}
        }
        return list;
    }

    private LinkedList<String> buildConventionalOperators() {
        LinkedList<String> list = new LinkedList<>();
        list.insert("+"); list.insert("-");
        list.insert("*"); list.insert("/");
        list.insert("^");
        return list;
    }

    private LinkedList<String> buildConventionalPrecedence() {
        LinkedList<String> list = new LinkedList<>();
        list.insert("+:1"); list.insert("-:1");
        list.insert("*:2"); list.insert("/:2");
        list.insert("^:3");
        return list;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
