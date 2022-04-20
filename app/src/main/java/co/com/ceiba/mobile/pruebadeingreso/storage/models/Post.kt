package co.com.ceiba.mobile.pruebadeingreso.storage.models;

public class Post {
    public int id;
    public int userId;
    public String title;
    public String body;

    public Post(int id, int userId, String title, String body) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }
}
