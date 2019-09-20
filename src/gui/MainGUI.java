package gui;

import compresor.Comprimir;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author l
 */
public class MainGUI extends Application {

    private Stage primary;
    private TextArea text;
    private long textSize = 0;
    File mainFile;
    File ciphertexzipfile;
    Comprimir compresor = new Comprimir();

    public void MainGUI() {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        //CipherTexZip
        Menu archivo = new Menu("Archivo");
        MenuItem comprimir = new MenuItem("Comprimir");
        MenuItem descomprimir = new MenuItem("Descomprimir");
        MenuItem salir = new MenuItem("Salir");

        archivo.getItems().addAll(
                comprimir,
                descomprimir,
                new SeparatorMenuItem(),
                salir
        );

        Menu ayuda = new Menu("Ayuda");
        MenuItem ayuda_ = new MenuItem("Ayuda");
        MenuItem acerca = new MenuItem("Acerca de");

        ayuda.getItems().addAll(
                ayuda_,
                new SeparatorMenuItem(),
                acerca
        );

        MenuBar texzip_menubar = new MenuBar();
        texzip_menubar.getMenus().addAll(
                archivo,
                ayuda
        );

        Button choosefile = new Button("Seleccionar archivo");
        Label dragfile = new Label("Arrastre el archivo");

        BorderPane texzip = new BorderPane();
        texzip.setTop(texzip_menubar);
        texzip.setBottom(choosefile);
        texzip.setCenter(dragfile);
        dragfile.setMinWidth(texzip.getWidth());
        dragfile.setMinHeight(texzip.getHeight());

        Tab texziptab = new Tab("CipherTexZip");
        texziptab.setContent(texzip);;

        dragfile.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("over");
                if (event.getGestureSource() != dragfile.getText()
                        && event.getDragboard().hasString()) {
                    dragfile.setTextFill(Color.GRAY);
                    event.acceptTransferModes(TransferMode.ANY);
                }
                event.consume();
            }
        }
        );

        dragfile.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("exited");
                dragfile.setTextFill(Color.BLACK);

                event.consume();
            }
        }
        );

        dragfile.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("dropped");
                Dragboard db = event.getDragboard();
                System.out.println(db.hasFiles());
                if (db.hasFiles()) {
                    List<File> l = db.getFiles();
                    if (l.get(l.size()-1).getName().endsWith(".txt") || l.get(l.size()-1).getName().endsWith(".jj")) {
                        ciphertexzipfile = l.get(l.size()-1);
                        //provicional
                        System.out.println("file recived - 134");
                        Platform.runLater(() -> compresor.Comprimir(ciphertexzipfile));
                    }
                }
                
                event.setDropCompleted(true);
                event.consume();
            }
        });

        dragfile.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("lets see");
                event.consume();
            }
        }
        );

        //JEdit
        Menu fileMenu = new Menu("Archivo");
        MenuItem newMenuItem = new MenuItem("Nuevo");
        MenuItem openMenuItem = new MenuItem("Abrir...");
        MenuItem saveMenuItem = new MenuItem("Guardar");
        MenuItem saveAsMenuItem = new MenuItem("Guardar como..");
        MenuItem exitMenuItem = new MenuItem("Salir");

        fileMenu.getItems().addAll(
                newMenuItem,
                openMenuItem,
                saveMenuItem,
                saveAsMenuItem,
                new SeparatorMenuItem(),
                exitMenuItem
        );

        Menu editMenu = new Menu("Editar");
        MenuItem undoMenuItem = new MenuItem("Deshacer");
        MenuItem cutMenuItem = new MenuItem("Cortar");
        MenuItem copyMenuItem = new MenuItem("Copiar");
        MenuItem pasteMenuItem = new MenuItem("Pagar");
        MenuItem deleteMenuItem = new MenuItem("Borrar");
        MenuItem selectAllMenuItem = new MenuItem("Seleccionar todo");

        editMenu.getItems().addAll(
                undoMenuItem,
                new SeparatorMenuItem(),
                cutMenuItem,
                copyMenuItem,
                pasteMenuItem,
                deleteMenuItem,
                new SeparatorMenuItem(),
                selectAllMenuItem
        );

        Menu helpMenu = new Menu("Ayuda");
        MenuItem aboutMenuItem = new MenuItem("Acerca de");

        helpMenu.getItems().addAll(
                aboutMenuItem
        );

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(
                fileMenu,
                editMenu,
                helpMenu
        );

        menuBar.prefWidthProperty().bind(stage.widthProperty());

        text = new TextArea();
        text.setPrefRowCount(10);
        text.setPrefColumnCount(100);
        text.setWrapText(true);
        text.setPrefWidth(150);

        BorderPane rootjedit = new BorderPane();
        rootjedit.setTop(menuBar);

        undoMenuItem.setOnAction(event -> {
            text.undo();
        });

        copyMenuItem.setOnAction(event -> {
            text.copy();
        });

        cutMenuItem.setOnAction(event -> {
            text.cut();
        });

        pasteMenuItem.setOnAction(event -> {
            text.paste();
        });

        selectAllMenuItem.setOnAction(event -> {
            text.selectAll();
        });

        deleteMenuItem.setOnAction(event -> {
            IndexRange selection = text.getSelection();
            text.deleteText(selection);
        });

        aboutMenuItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Acerca de JEdit");
            alert.setHeaderText("JEdit");
            alert.setContentText("JEdit V1 \nElaborado con JavaFX");

            alert.showAndWait();
        });

        exitMenuItem.setOnAction(actionEvent -> Platform.exit());

        newMenuItem.setOnAction((event) -> {

            if (!checkTextStatus()) {
                return;
            }

            text.setText("");
            primary.setTitle("JEdit");
            textSize = 0;
            mainFile = null;
        });

        saveMenuItem.setOnAction((event) -> {

            save();

        });

        saveAsMenuItem.setOnAction((event) -> {

            saveAs();

        });

        openMenuItem.setOnAction((event) -> {

            if (!checkTextStatus()) {
                return;
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Abrir archivo");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt")
            );

            File file = fileChooser.showOpenDialog(primary);

            if (file == null) {
                return;
            }

            mainFile = file;

            primary.setTitle("JEdit | " + file.getName());
            try ( BufferedReader br = new BufferedReader(new FileReader(file))) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                String everything = sb.toString();
                text.setText(everything);
                textSize = text.getText().length();
                text.positionCaret((int) textSize);

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
        rootjedit.setCenter(text);

        newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        openMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        exitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));

        undoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        cutMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        copyMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        pasteMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));
        selectAllMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        deleteMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));

        Tab jedit = new Tab("JEdit");
        jedit.setContent(rootjedit);

        //union
        TabPane root = new TabPane(texziptab, jedit);
        root.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        Scene scene = new Scene(root, 800, 550);
        stage.setScene(scene);
        stage.show();
    }

    //CipherTexZip
    //Jedit
    private void save() {
        if (mainFile == null) {
            saveAs();
        } else {
            saveFile(mainFile);
        }
    }

    private void saveAs() {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        fileChooser.setTitle("Guardar archivo");

        File file = fileChooser.showSaveDialog(primary);

        if (file == null) {
            return;
        }
        mainFile = file;

        saveFile(file);
    }

    private void saveFile(File file) {
        primary.setTitle("JEdit | " + file.getName());

        textSize = text.getText().length();

        try ( Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), "utf-8"))) {

            String[] split = text.getText().split("\n");
            for (String string : split) {
                writer.append(string);
                writer.append(System.lineSeparator());
            }

        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean checkTextStatus() {
        if (textSize != text.getText().length()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);

            alert.setTitle("JEdit");
            alert.setHeaderText("Desea guardar los cambios ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                save();

            } else if (result.get() == ButtonType.CANCEL) {
                return false;
            }

        }
        return true;
    }

}
