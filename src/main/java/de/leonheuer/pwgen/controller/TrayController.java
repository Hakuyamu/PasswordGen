package de.leonheuer.pwgen.controller;

import de.leonheuer.pwgen.PasswordGen;
import de.leonheuer.pwgen.util.Util;
import javafx.application.Platform;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class TrayController {

    private final PasswordGen main;
    private final SystemTray tray;
    private final TrayIcon icon;

    public TrayController(@NotNull PasswordGen main) throws IOException, AWTException {
        this.main = main;
        Toolkit.getDefaultToolkit();

        tray = SystemTray.getSystemTray();
        URL trayIcon = main.getClass().getClassLoader().getResource("tray_icon.png");
        if (trayIcon == null) {
            throw new FileNotFoundException("tray_icon.png could not be loaded from resources. Does it exist?");
        }
        Image img = ImageIO.read(trayIcon);
        icon = new TrayIcon(img);
        icon.addActionListener(event -> Platform.runLater(this::copyToClip));

        PopupMenu popup = new PopupMenu();

        MenuItem showItem = new MenuItem("Show");
        showItem.setFont(Font.decode(null).deriveFont(Font.BOLD));
        showItem.addActionListener(event -> Platform.runLater(this::showApp));
        popup.add(showItem);

        MenuItem copyItem = new MenuItem("Generate and copy");
        copyItem.addActionListener(event -> Platform.runLater(this::copyToClip));
        popup.add(copyItem);

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(event -> Platform.runLater(this::exit));
        popup.add(exitItem);

        icon.setPopupMenu(popup);
        tray.add(icon);
    }

    private void showApp() {
        main.getStage().show();
        main.getStage().toFront();
    }

    private void copyToClip() {
        HomeController ct = main.getHomeController();
        String pw = Util.generateSingle(ct);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(pw), null);
    }

    private void exit() {
        tray.remove(icon);
        Platform.exit();
    }

}
