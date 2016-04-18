import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributes;
import java.util.ArrayList;
import java.util.Scanner;


public class Runner {
    final static String CONST_EXTENSION = "mp3";
    final static String Path_toWtite_Const = "MyNewHTML.html";
    static ArrayList<File> filesAfterSearch = new ArrayList<>();

    public static void main(String[] args) {
        try {
            File fileHTML;
            String inputDirectory;
            ArrayList<File> directoriesForSearch = new ArrayList<>();
            Scanner scn = new Scanner(System.in);
            fileHTML = new File(Path_toWtite_Const);


            System.out.println("Enter12345 directories for search " + CONST_EXTENSION + " files (For example: D:\\music\\for test\\).\nEnter 'stop' for end");
            int countDirectory = 1;
            do {
                System.out.println("Enter directory " + countDirectory + ":");
                inputDirectory = scn.nextLine();
                if (!(inputDirectory.equalsIgnoreCase("stop"))) {
                    File fileForHelp = new File(inputDirectory);
                    if (!fileForHelp.exists()) {
                        System.out.println("The     directory doesn't exist. Enter any correct directory");
                        continue;
                    }
                    if (!directoriesForSearch.contains(fileForHelp)) {
                        directoriesForSearch.add(fileForHelp);
                    } else  {
                        System.out.println("This directory has been entered earlier");
                        continue;
                    }
                    countDirectory++;
                }
            } while (!(inputDirectory.equalsIgnoreCase("stop")));

            for (int i = 0; i < directoriesForSearch.size(); i++) {
                searchFiles(directoriesForSearch.get(i));
                System.out.println("Directory for search: "  + directoriesForSearch.get(i).getAbsolutePath());
            }
            execute(fileHTML);
            System.out.println("The file is successfully created: " + fileHTML.getAbsolutePath());
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException:" + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("NullPointerException:" + e.getMessage());
        } catch (Error e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
        }

    }


    public static void execute(File fileHTML) throws IOException {
        ArrayList<Artist> artists = new ArrayList<Artist>();
        for (int i = 0; i < filesAfterSearch.size(); i++) {
            SongInformation helpSong = SongInformation.fromFile(filesAfterSearch.get(i).getPath());
            boolean artistIsExists = false;
            for (int j = 0; j < artists.size(); j++) {
                if (artists.get(j).getName().toLowerCase().equals(helpSong.getArtist().toLowerCase())) {
                    artists.get(j).addSong(helpSong);
                    artistIsExists = true;
                }
            }
            if (!artistIsExists) {
                Artist helpArtist = new Artist(helpSong.getArtist());
                helpArtist.addSong(helpSong);
                artists.add(helpArtist);
            }
        }
        OutputStreamWriter out =
                new OutputStreamWriter(new FileOutputStream(fileHTML), "UTF-8");
        out.write("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\">\n" +
                "<title>Page Cataloguer</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h2> My mp3-cataloguer</h2>\n");
        for (Artist artist : artists) {
            out.write("<h3>" + artist.getName() + "</h3>\n");
            ArrayList<Album> albums = artist.getAlbums();
            for (Album album : albums) {
                out.write("<h4 style=\"padding-left: 20px;\">" + album.getTitle() + "</h4>\n");
                ArrayList<SongInformation> songs = album.getSongs();
                for (SongInformation song : songs) {
                    out.write("<h5 style=\"padding-left: 40px;\">" + song.getTitle() +
                            "<a style=\"padding-left: 10px;\">" + (song.getDuration() / 3600) + ":" + (song.getDuration() / 60) + ":" + (song.getDuration() % 60) +
                            "</a>" +
                            "<a style=\"padding-left: 10px;\" href=\"file:///" + song.getPath() + "\">link to the song</a></h5>\n");
                }
            }
        }
        out.write("</body>\n" +
                "<html>\n");
        out.close();
    }

    public static void searchFiles(File dir) {
        Path myPath = dir.toPath();
        try {
            DosFileAttributes attr = Files.readAttributes(myPath, DosFileAttributes.class);
            if (attr.isSymbolicLink() || (myPath.compareTo(myPath.toRealPath()) != 0)) {
                System.out.format("Symbolic link: %s", myPath);
                return;
            }
        } catch (IOException e) {
            System.err.println("DOS file attributes not supported:" + e);
        }
        if (dir.isFile()) {
            if (dir.getAbsolutePath().endsWith(CONST_EXTENSION)) {
                filesAfterSearch.add(dir);
            }
        } else {
            File[] subFiles = dir.listFiles();
            for (File file : subFiles) {
                if (file.isFile()) {
                    if (file.getAbsolutePath().endsWith(CONST_EXTENSION)) {
                        filesAfterSearch.add(file);
                    }
                } else {
                    searchFiles(file);
                }
            }
        }
    }
}

