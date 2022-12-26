import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class NewThread extends Thread {
    private Url urls;
    private int num;

    public NewThread(Url urls, int num) {
        this.urls = urls;
        this.num = num;
    }

    @Override
    public void run() {
        while (!urls.isDownloaded()) {
            String str = urls.getUrl();
            byte dataBuffer[] = new byte[1024];
            int bytesRead, fileNumber;
            File file;

            if (str != null) {
                file = getFile(str);

                try (BufferedInputStream input = new BufferedInputStream(new URL(str).openStream());
                     FileOutputStream output = new FileOutputStream(file)) {
                    fileNumber = urls.getUrlsListIndex(str);
                    System.out.println("Thread-" + num + " start download file number " + fileNumber);

                    while ((bytesRead = input.read(dataBuffer)) != -1) {
                        output.write(dataBuffer, 0, bytesRead);
                    }
                    System.out.println("Thread-" + num + " finish download file number " + fileNumber);
                } catch (IOException e) {
                    System.out.println("File not found");
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private File getFile(String str) {
        File file;
        String[] strings = str.split("/");

        file = new File(strings[strings.length - 1]);
        return file;
    }
}
