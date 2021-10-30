package de.leonheuer.pwgen.manager;


import de.leonheuer.pwgen.PasswordGen;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataManager {

    private final String path = System.getProperty("user.home") + "\\AppData\\Roaming\\PasswordGen";
    private boolean caps;
    private boolean small;
    private boolean numbers;
    private boolean chars;
    private boolean windows;
    private int length;
    private int amount;

    public DataManager() {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File pref = new File(path, "preferences.json");
        if (!pref.exists()) {
            return;
        }

        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(pref)) {
            JSONObject data = (JSONObject) parser.parse(reader);
            caps = Boolean.parseBoolean((String) data.get("caps"));
            small = Boolean.parseBoolean((String) data.get("small"));
            numbers = Boolean.parseBoolean((String) data.get("numbers"));
            chars = Boolean.parseBoolean((String) data.get("chars"));
            windows = Boolean.parseBoolean((String) data.get("windows"));
            length = new Long((long) data.get("length")).intValue();
            amount = new Long((long) data.get("amount")).intValue();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void save() {
        File pref = new File(path, "preferences.json");
        if (!pref.exists()) {
            try {
                pref.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        try (FileWriter writer = new FileWriter(path + "\\preferences.json")) {
            JSONObject data = new JSONObject();
            data.put("caps", Boolean.toString(caps));
            data.put("small", Boolean.toString(small));
            data.put("numbers", Boolean.toString(numbers));
            data.put("chars", Boolean.toString(chars));
            data.put("windows", Boolean.toString(windows));
            data.put("length", length);
            data.put("amount", amount);
            writer.write(data.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isCaps() {
        return caps;
    }

    public void setCaps(boolean caps) {
        this.caps = caps;
    }

    public boolean isSmall() {
        return small;
    }

    public void setSmall(boolean small) {
        this.small = small;
    }

    public boolean isNumbers() {
        return numbers;
    }

    public void setNumbers(boolean numbers) {
        this.numbers = numbers;
    }

    public boolean isChars() {
        return chars;
    }

    public void setChars(boolean chars) {
        this.chars = chars;
    }

    public boolean isWindows() {
        return windows;
    }

    public void setWindows(boolean windows) {
        this.windows = windows;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
