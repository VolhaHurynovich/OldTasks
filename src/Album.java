import java.util.ArrayList;

public class Album {
    private String title = "";
    private ArrayList<SongInformation> songs = new ArrayList<>();

    public Album(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void addSong(SongInformation song) {
        songs.add(song);
    }

    public ArrayList<SongInformation> getSongs() {
        return songs;
    }
}
