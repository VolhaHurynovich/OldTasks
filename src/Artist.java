import java.util.ArrayList;

public class Artist {

    private String name = "";
    private ArrayList<Album> albums = new ArrayList<>();

    public Artist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addSong(SongInformation song) {
        boolean artistIsExists = false;
        for (int j = 0; j < albums.size(); j++) {
            if (albums.get(j).getTitle().toLowerCase().equals(song.getAlbum().toLowerCase())) {
                albums.get(j).addSong(song);
                artistIsExists = true;
            }
        }
        if (!artistIsExists) {
            Album helpArtist = new Album(song.getAlbum());
            helpArtist.addSong(song);
            albums.add(helpArtist);
        }
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }
}
