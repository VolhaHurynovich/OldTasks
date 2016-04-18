import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

public class SongInformation {

    private String mArtist = "Unknown artist";
    private String mTitle = "Unknown title";
    private String mAlbum = "Unknown album";
    private long mDuration = 0;
    private String path;

    public SongInformation() {
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        artist = validateText(artist);
        if (artist.length() > mArtist.length()) {
            mArtist = artist;
        }
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        title = validateText(title);
        if (title.length() > mTitle.length()) {
            mTitle = title;
        }
    }

    public String getAlbum() {
        return mAlbum;
    }

    public void setAlbum(String album) {
        album = validateText(album);
        if (album.length() > mAlbum.length()) {
            mAlbum = album;
        }
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        duration = validateNumber(duration);
        if (duration > mDuration) {
            mDuration = duration;
        }
    }

    protected String validateText(String text) {
        if (text == null) {
            return "";
        } else {
            return text.trim();
        }
    }

    protected long validateNumber(long number) {
        if (number < 0) {
            return 0;
        } else {
            return number;
        }
    }

    public static SongInformation fromFile(String filePath) {
        final SongInformation out = new SongInformation();
        out.path = filePath;
        try {
            final Mp3File songData = new Mp3File(filePath);
            out.setDuration(songData.getLengthInSeconds());

            if (songData.hasId3v2Tag()) {
                ID3v2 songTags = songData.getId3v2Tag();
                out.setArtist(songTags.getArtist());
                out.setTitle(songTags.getTitle());
                out.setAlbum(songTags.getAlbum());
            }

            if (songData.hasId3v1Tag()) {
                ID3v1 songTags = songData.getId3v1Tag();
                out.setArtist(songTags.getArtist());
                out.setTitle(songTags.getTitle());
                out.setAlbum(songTags.getAlbum());
            }
        } catch (Exception e) {
        }

        return out;
    }

    public String getPath() {
        return path;
    }
}