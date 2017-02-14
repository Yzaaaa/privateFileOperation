/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatefileoperation;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import static privatefileoperation.Constants.*;

/**
 * FXML Controller class
 *
 * @author Yzaaaa
 */
public class StartMenuController implements Initializable {

    @FXML
    private Button btnRenameTwitterjpg;
    @FXML
    private Button btnShuffleFilename;
    @FXML
    private Label lblFilename;
    @FXML
    private Label lblRename;

    Stage thisStage;

    void setThisStage(Stage stage) {
        thisStage = stage;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onShuffle(ActionEvent event) throws IOException {
        //フォルダのファイル全てを読み込み
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(TEST_FILE))) {
            for (Path oldPath : directoryStream) {
                //ランダムな名前の生成とリネーム
                String extension = FilenameUtils.getExtension(oldPath.toString());
                String randAlpha = RandomStringUtils.randomAlphanumeric(15);
                Path newPath = Paths.get(TEST_FILE + randAlpha + "." + extension);
                Files.move(oldPath, newPath);
            }
            lblFilename.setText("Success");
        } catch (FileAlreadyExistsException e) {
            System.err.println("既に同名のファイルがあります");
        } catch (NoSuchFileException e) {
            System.err.println("ファイルが見つかりません");
        }
    }

    @FXML
    private void onRename(ActionEvent event) throws IOException {
        //ディレクトリ選択画面の用意
        Stage newStage = new Stage();
        newStage.initOwner(thisStage);
        Path initPath = Paths.get(TEST_FILE);

        final DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("ディレクトリ選択");
        dc.setInitialDirectory(initPath.toFile());

        //ディレクトリ選択
        try{
        Path importFile = dc.showDialog(thisStage).toPath();
        //選択に成功したら処理続行
        if (importFile != null) {
            lblRename.setText(importFile.toString());
        }
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(importFile)) {
            for (Path oldPath : ds) {
                String name = oldPath.getFileName().toString();
                //" orig"がつくファイルだった場合削除してリネーム
                if (name.endsWith(" orig") || name.endsWith(" orig.jpg") != false) {
                    Path newPath = Paths.get(TEST_FILE + name.substring(0, name.length() - 5));
                    Files.move(oldPath, newPath);
                    System.out.println(name);
                }
            }
        }catch (FileAlreadyExistsException e){
            System.err.println("同名のファイルが存在しています\n"
                    + "ファイルのリネームを中断します");
        }

    }catch (NullPointerException e){
        System.err.println("フォルダの選択に失敗しました\n" + e);
    }

}
}
