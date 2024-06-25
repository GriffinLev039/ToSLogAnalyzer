import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class DIRReader {
    public static ArrayList<File> importFilesv1(String path) {
        Path[] pathArr = {};
        ArrayList<File> fileArr = new ArrayList<File>();
        try {
            Stream<Path> fileStream = Files.walk(Paths.get(path));
            pathArr = fileStream.toArray(Path[]::new);
        }
        catch (Exception e) {
            System.out.println("An error occured: " + e);
            e.printStackTrace();
        }
        for (Path aPath : pathArr) {
            File file = new File(aPath.toString());
            fileArr.add(file);
        }
        return fileArr;
    }

    public static File[] importFiles(String path) {
        File dir = new File(path);
        File[] files = dir.listFiles();
        return files;
    }
}
