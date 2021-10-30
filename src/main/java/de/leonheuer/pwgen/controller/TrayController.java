package de.leonheuer.pwgen.controller;

import de.leonheuer.pwgen.PasswordGen;
import de.leonheuer.pwgen.util.Util;
import javafx.application.Platform;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

public class TrayController {

    private final PasswordGen main;
    private final SystemTray tray;
    private final TrayIcon icon;

    public TrayController(PasswordGen main) throws IOException, AWTException {
        this.main = main;
        Toolkit.getDefaultToolkit();

        tray = SystemTray.getSystemTray();
        Image img = ImageIO.read(main.getClass().getClassLoader().getResource("icon.png"));
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
