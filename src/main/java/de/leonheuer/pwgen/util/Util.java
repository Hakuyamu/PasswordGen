package de.leonheuer.pwgen.util;

import de.leonheuer.pwgen.controller.HomeController;
import org.apache.commons.lang.RandomStringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringJoiner;

public class Util {

    public static void generate(@NotNull HomeController ct) {

        int amount = (int) ct.sAmount.getValue();
        int length = (int) ct.sLength.getValue();

        long before = System.currentTimeMillis();
        StringJoiner passwords = new StringJoiner("\n");
        for (int i = 0; i < amount; i++) {
            passwords.add(RandomStringUtils.random(length, getPossibleChars(ct)));
            ct.pbProgress.setProgress((i + 1.0d) / amount);
        }
        long result = System.currentTimeMillis() - before;

        ct.lTime.setText("Time: " + result + "ms");
        ct.taOutput.setText(passwords.toString());
    }

    private static @NotNull String getPossibleChars(@NotNull HomeController ct) {
        StringBuilder sb = new StringBuilder();
        if (ct.cbCaps.isSelected()) {
            sb.append(CharacterSet.CAPS.getChars());
        }
        if (ct.cbSmall.isSelected()) {
            sb.append(CharacterSet.SMALL.getChars());
        }
        if (ct.cbNums.isSelected()) {
            sb.append(CharacterSet.NUMBERS.getChars());
        }
        if (ct.cbChars.isSelected()) {
            sb.append(CharacterSet.CHARS.getChars());
        }
        return sb.toString();
    }

    public static @NotNull String generateSingle(@NotNull HomeController ct) {
        int length = (int) ct.sLength.getValue();
        return RandomStringUtils.random(length, getPossibleChars(ct));
    }

    public static void addToStartup() throws IOException {
        String path = Util.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decoded = URLDecoder.decode(path, StandardCharsets.UTF_8);
        decoded = decoded.substring(1);
        decoded = decoded.replaceAll("/", "\\\\");
        File startupFile = new File("C:\\Users\\" + System.getProperty("user.name") +
                "\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup", "PasswordGen.bat");
        if (!startupFile.exists()) {
            //noinspection ResultOfMethodCallIgnored
            startupFile.createNewFile();
        }
        FileWriter writer = new FileWriter(startupFile);
        writer.write("start javaw -Xmx200m -jar " + decoded);
        writer.close();
    }

    public static void removeFromStartup() throws IOException {
        Files.deleteIfExists(Paths.get("C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\StartUp\\PasswordGen.bat"));
    }

}
