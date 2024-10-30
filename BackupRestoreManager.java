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
 * This class manages the backup and restore functionalities for help articles 
 * as part of Phase 2 of the project. It provides features to back up articles 
 * to a file, restore articles from the backup, and clear old backups when needed.
 */

public class BackupRestoreManager {
    private static final String BACKUP_FILE = "backup_articles.dat";
    
    /**
     * Constructor for BackupRestoreManager.
     * Automatically clears any existing backup on startup to prevent data conflicts.
     */
    public BackupRestoreManager() {
        // Optionally clear backup on startup
        clearBackup();
    }

    public void clearBackup() {
        File backupFile = new File(BACKUP_FILE);
        if (backupFile.exists()) {
            if (backupFile.delete()) {
                System.out.println("Old backup cleared.");
            } else {
                System.err.println("Failed to clear old backup.");
            }
        }
    }

    /**
     * Backs up the provided list of new help articles by merging them 
     * with the existing backup.
     * 
     * @param newArticles List of new articles to be added to the backup.
     */
    public void backupArticles(List<HelpArticle> newArticles) {
        List<HelpArticle> existingBackup = loadBackup();  // Load existing articles
        existingBackup.addAll(newArticles);  // Append new articles

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(BACKUP_FILE))) {
            out.writeObject(existingBackup);
            System.out.println("Backup completed successfully.");
        } catch (IOException e) {
            System.err.println("Backup failed: " + e.getMessage());
        }
    }

    /**
     * Restores and returns the list of help articles from the backup file.
     * If the backup file is missing or cannot be read, an empty list is returned.
     * 
     * @return List of help articles restored from the backup.
     */
    public List<HelpArticle> loadBackup() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(BACKUP_FILE))) {
            return (List<HelpArticle>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("No backup found or failed to load: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
