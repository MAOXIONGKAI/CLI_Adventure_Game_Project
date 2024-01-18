import java.io.File;
import java.util.Scanner;

public class SaveMenu extends Menu {
    SaveMenu(Game currentGame, String msg) {
        super(currentGame, msg);
    }

    @Override
    public Game prompt(Menu start, Menu load, Menu save) {
        if (currentGame.getSaveName().isEmpty()) {
            Game.save(currentGame);
        } else {
            System.out.printf("===================检测到本次游戏来自存档「%s」===================\n" +
                    "您可以选择创建或覆盖当前存档，请根据指令做出选择……\n" +
                    "输入“N”：创建新存档并保存游戏\n" +
                    "输入“W”：覆盖当前旧存档\n" +
                    "输入“M”：回到当前游戏\n", currentGame.getSaveName());
            String input = "";
            Scanner scanner = new Scanner(System.in);
            while (input.isEmpty()) {
                input = scanner.nextLine();
                if ("N".equalsIgnoreCase(input)) {
                    Game.save(currentGame);
                } else if ("W".equalsIgnoreCase(input)) {
                    File oldSave = new File(Game.getSaveFolder() +
                                                    File.separator +
                                                    currentGame.getSaveName() +
                                                    ".ser");
                    oldSave.delete();
                    Game.save(currentGame);
                } else if ("M".equalsIgnoreCase(input)) {
                    break;
                } else {
                    System.out.println("无效输入，请重新输入指令...");
                    input = "";
                }
            }

        }
        return currentGame;
    }
}
