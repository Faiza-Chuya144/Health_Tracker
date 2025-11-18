package seu.edu.bd.healthtracker.model;

public class Recommendation {
    private String title;
    private String desc;
    private String priority; // "low", "medium", or "high"


    public Recommendation() {}
    public Recommendation(String title, String desc, String priority) {
        this.title = title;
        this.desc = desc;
        this.priority = priority;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
