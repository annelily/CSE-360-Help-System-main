package cse360Phase1Project;

import java.io.Serializable;


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
 * The HelpArticle class represents an individual help article, 
 * containing detailed information such as the title, description, keywords, 
 * and content. It implements the Serializable interface, allowing 
 * instances of this class to be serialized and stored.
 */
public class HelpArticle implements Serializable {
    private long id;
    private String title;
    private String shortDescription;
    private String[] keywords;
    private String body;
    private String[] references;
    private String[] groups;
    private String level;
    private boolean sensitive;
    
    /**
     *Constructor to initialize a HelpArticle instance with all its properties
     * 
     * @param id               Unique identifier for the article
     * @param title            Title of the article
     * @param shortDescription Brief summary of the article
     * @param keywords         Array of keywords for search and filtering
     * @param body             Full content of the article
     * @param references       Array of references cited in the article
     * @param groups           Array of groups or categories the article belongs to
     * @param level            Difficulty or importance level of the article
     * @param sensitive        Boolean indicating if the article contains sensitive content
     */
    public HelpArticle(long id, String title, String shortDescription, String[] keywords, 
                       String body, String[] references, String[] groups, String level, boolean sensitive) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.keywords = keywords;
        this.body = body;
        this.references = references;
        this.groups = groups;
        this.level = level;
        this.sensitive = sensitive;
    }

    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getShortDescription() { return shortDescription; }
    public String[] getKeywords() { return keywords; }
    public String getBody() { return body; }
    public String[] getReferences() { return references; }
    public String[] getGroups() { return groups; }
    public String getLevel() { return level; }
    public boolean isSensitive() { return sensitive; }

    public void setTitle(String title) { this.title = title; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }
    public void setKeywords(String[] keywords) { this.keywords = keywords; }
    public void setBody(String body) { this.body = body; }
    public void setReferences(String[] references) { this.references = references; }
    public void setGroups(String[] groups) { this.groups = groups; }
    public void setLevel(String level) { this.level = level; }
    public void setSensitive(boolean sensitive) { this.sensitive = sensitive; }
}
