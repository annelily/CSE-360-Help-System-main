package cse360Phase1Project;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> Title: Admin Home UI - Phase 2 </p>
 * 
 * <p> Description: A user interface for the Admin home page, allowing management of users within
 * the CSE360 Phase 2 Project. This phase includes functionality for deleting users, resetting accounts, 
 * editing user roles, and enhanced backup and restore operations for help articles. 
 * This is part of the JavaFX application for user management. </p>
 * 
 * <p> Group Tu18: 
 * - Aidan Beardslee
 * - Emily Do
 * - Kyle Zang
 * - Anne Shih
 * - Angie Moroyoqui 
 * </p>
 * 
 * @version 3.00    2024-10-29    Final version
 */


/**
 * This class provides the user interface for managing help articles. 
 * It interacts with the HelpArticleManager and BackupRestoreManager to 
 * allow users to add, update, delete, search, display, backup, and restore articles.
 */
public class HelpArticleUI {
    private HelpArticleManager articleManager;
    private BackupRestoreManager backupRestoreManager;
    private ListView<String> articleListView;
    private UserManagementApp mainApp;

    
    /**
     * Constructor to initialize the HelpArticleUI.
     * Sets up the UI components and event handlers.
     * 
     * @param root   The root Pane to which components will be added.
     * @param app    Reference to the main UserManagementApp.
     * @param manager Instance of HelpArticleManager to manage articles.
     */
    public HelpArticleUI(Pane root, UserManagementApp app, HelpArticleManager manager) {
        this.mainApp = app;
        this.articleManager = manager;
        this.backupRestoreManager = new BackupRestoreManager();

        Label titleLabel = new Label("Help Article Management");
        titleLabel.setLayoutX(200);
        titleLabel.setLayoutY(30);

        articleListView = new ListView<>();
        articleListView.setLayoutX(50);
        articleListView.setLayoutY(120);
        articleListView.setPrefSize(500, 250);

        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.setLayoutX(50);
        searchField.setLayoutY(80);
        searchField.setPrefWidth(300);
        
        MenuButton searchOptions = new MenuButton("Search Filter");
        CheckBox titleCheckBox = new CheckBox("Title");
        CheckBox groupCheckBox = new CheckBox("Group(s)");
        CheckBox levelCheckBox = new CheckBox("Level");
        CheckBox keywordsCheckBox = new CheckBox("Keyword(s)");

        searchOptions.getItems().addAll(
                new CustomMenuItem(titleCheckBox, false),
                new CustomMenuItem(groupCheckBox, false),
                new CustomMenuItem(levelCheckBox, false),
                new CustomMenuItem(keywordsCheckBox, false)
        );

        searchOptions.setLayoutX(370);
        searchOptions.setLayoutY(80);

        Button searchButton = new Button("Search");
        searchButton.setLayoutX(490);
        searchButton.setLayoutY(80);
        searchButton.setOnAction(e -> performSearch(
                searchField.getText(),
                titleCheckBox.isSelected(),
                groupCheckBox.isSelected(),
                levelCheckBox.isSelected(),
                keywordsCheckBox.isSelected()
        ));

        Button addButton = new Button("Add Article");
        addButton.setLayoutX(50);
        addButton.setLayoutY(400);
        addButton.setOnAction(e -> showAddArticleDialog());

        Button updateButton = new Button("Update Article");
        updateButton.setLayoutX(240);
        updateButton.setLayoutY(400);
        updateButton.setOnAction(e -> showUpdateArticleDialog());

        Button deleteButton = new Button("Delete Article");
        deleteButton.setLayoutX(140);
        deleteButton.setLayoutY(400);
        deleteButton.setOnAction(e -> deleteSelectedArticle());

        Button displayButton = new Button("Display Article");
        displayButton.setLayoutX(345);
        displayButton.setLayoutY(400);
        displayButton.setOnAction(e -> displaySelectedArticle());

        Button backupButton = new Button("Backup Articles");
        backupButton.setLayoutX(50);
        backupButton.setLayoutY(450);
        backupButton.setOnAction(e -> backupSelectedArticle());

        Button restoreButton = new Button("Restore Articles");
        restoreButton.setLayoutX(280);
        restoreButton.setLayoutY(450);
        restoreButton.setOnAction(e -> showRestoreDialog());
        
        Button clearBackupButton = new Button("Clear Backup");
        clearBackupButton.setLayoutX(170);
        clearBackupButton.setLayoutY(450);
        clearBackupButton.setOnAction(e -> {
            backupRestoreManager.clearBackup();
            showInfoMessage("Backup cleared successfully!");
        });

        Button backButton = new Button("Back");
        backButton.setLayoutX(400);
        backButton.setLayoutY(450);
        backButton.setOnAction(e -> {
            String role = mainApp.getCurrentUser().getRoles().get(0);
            mainApp.showRoleActionUI(role, mainApp.getCurrentUser());
        });

        root.getChildren().addAll(
                titleLabel, searchOptions, searchField, searchButton, articleListView,
                addButton, updateButton, deleteButton, displayButton,
                backupButton, restoreButton, clearBackupButton, backButton
        );

        populateArticleList();
    }
    
    
    /**
     * Populates the article list view with all articles.
     */
    private void populateArticleList() {
        articleListView.getItems().clear();
        for (HelpArticle article : articleManager.listAllArticles()) {
            articleListView.getItems().add(article.getId() + " - " + article.getTitle());
        }
    }

    
    /**
     * Backs up the selected article.
     */
    private void backupSelectedArticle() {
        String selectedArticle = articleListView.getSelectionModel().getSelectedItem();
        if (selectedArticle == null) {
            showErrorMessage("No article selected.");
            return;
        }

        long articleId = Long.parseLong(selectedArticle.split(" - ")[0]);
        HelpArticle articleToBackup = articleManager.listAllArticles().stream()
                .filter(a -> a.getId() == articleId)
                .findFirst()
                .orElse(null);

        if (articleToBackup != null) {
            backupRestoreManager.backupArticles(List.of(articleToBackup));
            showInfoMessage("Selected article backed up successfully!");
        } else {
            showErrorMessage("Failed to backup article.");
        }
    }
    

    /**
     * Displays the restore dialog where users can select an article to restore.
     * If no backup is available, an error message is shown.
     */
    private void showRestoreDialog() {
        List<HelpArticle> backupArticles = backupRestoreManager.loadBackup();

        if (backupArticles.isEmpty()) {
            showErrorMessage("No backup available to restore.");
            return;
        }

        Stage stage = new Stage();
        stage.setTitle("Restore Articles");

        VBox vbox = new VBox(10);
        ListView<String> restoreListView = new ListView<>();
        restoreListView.getItems().addAll(
                backupArticles.stream()
                        .map(article -> article.getId() + " - " + article.getTitle())
                        .collect(Collectors.toList())
        );

        Button restoreButton = new Button("Restore Selected Article");
        restoreButton.setOnAction(e -> {
            String selected = restoreListView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showErrorMessage("No article selected.");
                return;
            }

            long articleId = Long.parseLong(selected.split(" - ")[0]);
            HelpArticle articleToRestore = backupArticles.stream()
                    .filter(a -> a.getId() == articleId)
                    .findFirst()
                    .orElse(null);

            if (articleToRestore != null) {
                articleManager.addArticle(articleToRestore);
                populateArticleList();
                showInfoMessage("Article restored successfully!");
            } else {
                showErrorMessage("Failed to restore article.");
            }
            stage.close();
        });

        vbox.getChildren().addAll(new Label("Select an article to restore:"), restoreListView, restoreButton);
        Scene scene = new Scene(vbox, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Searches through articles based on user input and selected filters.
     * Displays matching articles in the list view.
     *
     * @param query         The search query entered by the user.
     * @param searchTitle   Whether to search by title.
     * @param searchGroup   Whether to search by group(s).
     * @param searchLevel   Whether to search by level.
     * @param searchKeywords Whether to search by keywords.
     */
    private void performSearch(String query, boolean searchTitle, boolean searchGroup, boolean searchLevel, boolean searchKeywords) {
        List<HelpArticle> filteredArticles = articleManager.listAllArticles().stream()
                .filter(article -> matchesSearch(article, query, searchTitle, searchGroup, searchLevel, searchKeywords))
                .collect(Collectors.toList());

        articleListView.getItems().clear();
        for (HelpArticle article : filteredArticles) {
            articleListView.getItems().add(article.getId() + " [Level: " + article.getLevel() + "] - " + article.getTitle());
        }
    }
    

    /**
     * Checks if the given article matches the search query based on selected filters.
     */
    private boolean matchesSearch(HelpArticle article, String query, boolean searchTitle, boolean searchGroup, boolean searchLevel, boolean searchKeywords) {
        String[] queries = query.split(",\\s*");
        return (searchTitle && containsAllKeywords(article.getTitle(), queries)) ||
               (searchGroup && containsAllKeywords(String.join(" ", article.getGroups()), queries)) ||
               (searchLevel && containsAllKeywords(article.getLevel(), queries)) ||
               (searchKeywords && containsAllKeywords(String.join(" ", article.getKeywords()), queries));
    }

    private boolean containsAllKeywords(String field, String[] queries) {
        for (String query : queries) {
            if (!field.contains(query)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Displays the selected article in a new window with all its details.
     */
    private void displaySelectedArticle() {
        String selectedArticle = articleListView.getSelectionModel().getSelectedItem();
        if (selectedArticle == null) {
            showErrorMessage("No article selected.");
            return;
        }

        long articleId = Long.parseLong(selectedArticle.split(" - ")[0].split(" ")[0]);
        HelpArticle article = articleManager.listAllArticles().stream()
                .filter(a -> a.getId() == articleId)
                .findFirst()
                .orElse(null);

        if (article == null) {
            showErrorMessage("Article not found.");
            return;
        }

        Stage stage = new Stage();
        stage.setTitle("Article Details");

        VBox vbox = new VBox(10);  

        vbox.getChildren().addAll(
                new Label("ID: " + article.getId()),
                new Label("Level: " + article.getLevel()),  
                new Label("Groups: " + String.join(", ", article.getGroups())),  
                new Label("Title: " + article.getTitle()),
                new Label("Short Description: " + article.getShortDescription()),
                new Label("Keywords: " + String.join(", ", article.getKeywords())),
                new Label("Body: " + article.getBody()),
                new Label("References: " + String.join(", ", article.getReferences()))
        );

        Scene scene = new Scene(vbox, 600, 400); 
        stage.setScene(scene);
        stage.show();
    }



    private void showAddArticleDialog() {
        Stage stage = new Stage();
        stage.setTitle("Add Help Article");

        VBox vbox = new VBox(10);

        TextField titleField = new TextField();
        titleField.setPromptText("Title");

        TextField shortDescriptionField = new TextField();
        shortDescriptionField.setPromptText("Short Description");

        TextArea bodyField = new TextArea();
        bodyField.setPromptText("Article Body");

        TextField keywordsField = new TextField();
        keywordsField.setPromptText("Keywords (comma-separated)");

        TextField groupField = new TextField();
        groupField.setPromptText("Groups (comma-separated)");

        TextField referencesField = new TextField();
        referencesField.setPromptText("References (comma-separated)");

        TextField levelField = new TextField();
        levelField.setPromptText("Level");

        Button addButton = new Button("Add Article");
        addButton.setOnAction(e -> {
            String title = titleField.getText();
            String shortDescription = shortDescriptionField.getText();
            String body = bodyField.getText();
            String[] keywords = keywordsField.getText().split(",");
            String[] groups = groupField.getText().split(",");
            String[] references = referencesField.getText().split(",");
            String level = levelField.getText();

            HelpArticle article = new HelpArticle(
                    System.currentTimeMillis(), title, shortDescription, keywords, body, 
                    references, groups, level, false
            );

            articleManager.addArticle(article);
            populateArticleList();
            stage.close();
        });

        vbox.getChildren().addAll(
                new Label("Add Help Article"), titleField, shortDescriptionField, bodyField,
                keywordsField, groupField, referencesField, levelField, addButton
        );

        Scene scene = new Scene(vbox, 600, 600);
        stage.setScene(scene);
        stage.show();
    }


    private void showUpdateArticleDialog() {
        String selectedArticle = articleListView.getSelectionModel().getSelectedItem();
        if (selectedArticle == null) {
            showErrorMessage("No article selected.");
            return;
        }

        long articleId = Long.parseLong(selectedArticle.split(" - ")[0].split(" ")[0]);
        HelpArticle article = articleManager.listAllArticles().stream()
                .filter(a -> a.getId() == articleId)
                .findFirst()
                .orElse(null);

        if (article == null) {
            showErrorMessage("Article not found.");
            return;
        }

        Stage stage = new Stage();
        stage.setTitle("Update Help Article");

        VBox vbox = new VBox(10);  

        TextField titleField = new TextField(article.getTitle());
        TextField shortDescriptionField = new TextField(article.getShortDescription());
        TextArea bodyField = new TextArea(article.getBody());
        TextField keywordsField = new TextField(String.join(", ", article.getKeywords()));
        TextField groupField = new TextField(String.join(", ", article.getGroups()));
        TextField referencesField = new TextField(String.join(", ", article.getReferences()));
        TextField levelField = new TextField(article.getLevel());  

        Button updateButton = new Button("Update Article");
        updateButton.setOnAction(e -> {
            article.setTitle(titleField.getText());
            article.setShortDescription(shortDescriptionField.getText());
            article.setBody(bodyField.getText());
            article.setKeywords(keywordsField.getText().split(","));
            article.setGroups(groupField.getText().split(","));
            article.setReferences(referencesField.getText().split(","));
            article.setLevel(levelField.getText());  

            articleManager.updateArticle(articleId, article);
            populateArticleList();
            stage.close();
        });

       
        vbox.getChildren().addAll(
                new Label("Update Help Article"),
                new Label("Title"), titleField,
                new Label("Short Description"), shortDescriptionField,
                new Label("Body"), bodyField,
                new Label("Keywords (comma-separated)"), keywordsField,
                new Label("Groups (comma-separated)"), groupField,
                new Label("References (comma-separated)"), referencesField,
                new Label("Level"), levelField, 
                updateButton
        );

        Scene scene = new Scene(vbox, 600, 600);  
        stage.setScene(scene);
        stage.show();
    }

    
    private void deleteSelectedArticle() {
        String selectedArticle = articleListView.getSelectionModel().getSelectedItem();
        if (selectedArticle == null) {
            showErrorMessage("No article selected.");
            return;
        }

        long articleId = Long.parseLong(selectedArticle.split(" - ")[0].split(" ")[0]);
        articleManager.removeArticle(articleId);
        populateArticleList();
    }

    /**
     * Displays an error message in an alert dialog.
     *
     * @param message The error message to display.
     */
    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.show();
    }

    /**
     * Displays an informational message in an alert dialog.
     *
     * @param message The message to display.
     */
    private void showInfoMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.show();
    }
}