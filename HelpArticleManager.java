package cse360Phase1Project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
 * The HelpArticleManager class implements a Singleton pattern to manage help articles.
 * It provides methods for adding, updating, removing, and listing help articles. 
 * Articles are stored in a file for persistence and loaded upon initialization.
 */
public class HelpArticleManager {
    private static HelpArticleManager instance;
    private List<HelpArticle> articles;
    private static final String FILE_NAME = "help_articles.dat";

    /**
     * Private constructor to prevent external instantiation.
     * Initializes the articles list and loads articles from the file.
     */
    private HelpArticleManager() {
        this.articles = new ArrayList<>();
        loadArticles();
    }

    /**
     * Provides a global point of access to the singleton instance.
     * 
     * @return The singleton instance of HelpArticleManager.
     */
    public static HelpArticleManager getInstance() {
        if (instance == null) {
            instance = new HelpArticleManager();
        }
        return instance;
    }

    
    /**
     * Adds a new article to the list and saves the updated list to the file.
     * 
     * @param article The new HelpArticle to be added.
     */
    public void addArticle(HelpArticle article) {
        articles.add(article);
        saveArticles();
    }

    
    /**
     * Updates an existing article by its ID and saves the updated list to the file.
     * 
     * @param id             The ID of the article to be updated.
     * @param updatedArticle The updated HelpArticle object.
     */
    public void updateArticle(long id, HelpArticle updatedArticle) {
        for (int i = 0; i < articles.size(); i++) {
            if (articles.get(i).getId() == id) {
                articles.set(i, updatedArticle);
                saveArticles();
                return;
            }
        }
    }

    
    /**
     * Removes an article by its ID and saves the updated list to the file.
     * 
     * @param id The ID of the article to be removed.
     */
  
    public void removeArticle(long id) {
        articles.removeIf(article -> article.getId() == id);
        saveArticles();
    }

    /**
     * Returns a list of all help articles.
     * 
     * @return A copy of the list of all HelpArticle objects.
     */
    public List<HelpArticle> listAllArticles() {
        return new ArrayList<>(articles);
    }

    /**
     * Saves the current list of articles to the file for persistence.
     */
    private void saveArticles() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(articles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Saves the current list of articles to the file for persistence.
     */
    private void loadArticles() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            articles = (List<HelpArticle>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            articles = new ArrayList<>();
        }
    }
}
