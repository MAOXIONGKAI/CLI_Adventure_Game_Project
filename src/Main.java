import java.io.*;
import java.util.Scanner;

public class Main {
    static Scanner inputScanner = new Scanner(System.in);

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (InterruptedException | IOException ex) {
            System.out.println("尝试清空命令行时发生错误：未知操作系统");
        }
    }

    public static void hintMenu(Game game, String[] hintArray){
        if(game.getHintCount() > 0){
            System.out.println("======================================本题提示=======================================");
            System.out.println(hintArray[game.getLevel()]);
            game.setHintCount(game.getHintCount() - 1);
        }else{
            System.out.println("对不起，您的提示查看次数已用完。\n剩下的冒险就请自己走完吧（笑\n");
        }
        System.out.printf("当前状态：%d生命值，%d次提示查看机会。\n",
                game.getHealth(), game.getHintCount());
        System.out.println("按任意键继续冒险……");
    }

    public static void main(String[] args){
        Menu startMenu = new StartMenu(null, "0. 里世界说明：冒险过程中会出现一些提示，要通过正确的思考和准确的判断走到最后。\n" +
                "主要角色为4人，其中以第一人称“我”为你自己。其他三人简介：\n" +
                "A.琳，我的女友，是一位优秀的护士。\n" +
                "B.罗非，我的好友，自由搏击教练。\n" +
                "C.艾洛，我的好友，恐怖小说作家。\n" +
                "\n" +
                "**********指令列表**********\n" +
                "输入“A”：确认游戏开始\n" +
                "**********常规指令**********\n" +
                "输入“L”：读取存档\n" +
                "输入“Q”：退出游戏\n" +
                "*********游戏内指令*********\n" +
                "输入“S”：保存存档\n" +
                "输入“H”： 查看提示\n" +
                "（字母指令不区分大小写）");

        String story = "";
        String answer = "";
        String result = "";
        String hint = "";
        //游戏指令和资源加载
        try(InputStream storyStream = Main.class.getResourceAsStream("/Story/Adventure.txt");
            InputStream answerStream = Main.class.getResourceAsStream("/Story/Answer.txt");
            InputStream resultStream = Main.class.getResourceAsStream("/Story/Result.txt");
            InputStream hintStream = Main.class.getResourceAsStream("/Story/Hint.txt");
            BufferedReader storyReader = new BufferedReader(new InputStreamReader(storyStream, "GBK"));
            BufferedReader answerReader = new BufferedReader(new InputStreamReader(answerStream, "GBK"));
            BufferedReader resultReader = new BufferedReader(new InputStreamReader(resultStream, "GBK"));
            BufferedReader hintReader = new BufferedReader(new InputStreamReader(hintStream, "GBK"))){
            String line = "";
            while((line = storyReader.readLine()) != null){
                story += line + "\n";
            }
            while((line = answerReader.readLine()) != null){
                answer += line + "\n";
            }
            while((line = resultReader.readLine()) != null){
                result += line + "\n";
            }
            while((line = hintReader.readLine()) != null){
                hint += line + "\n";
            }
        }catch (IOException e){
            System.out.println("游戏资源文件读取失败……\n按任意键继续……");
            inputScanner.nextLine();
            System.exit(0);
        }

        String[] scene = story.split("=====\n");
        String[] answerArray = answer.split("\n");
        String[] resultArray = result.split("\n=====\n");
        String[] hintArray = hint.split("=====\n");

        Game game = null;
        Menu loadMenu = new LoadMenu(game, "====================================游戏存档列表=====================================");
        game = startMenu.prompt(startMenu, loadMenu, null);
        Menu saveMenu = new SaveMenu(game, "");
        Menu gameOverMenu = new EndMenu(game,
                "============================Game Over: 你死了===============================\n" +
                        "十分不幸，你死在了冒险的路上，但是你的冒险不会止步于此。\n" +
                        "虽然不能原地复活，但是你可以选择如下指令继续旅程，或者休息片刻……\n" +
                        "输入“N”：开始新游戏\n" +
                        "输入“L”：查看存档\n" +
                        "输入“Q”：退出游戏");
        Menu finaleMenu = new EndMenu(game,
                String.format("=================================游戏通关：美好结局==================================\n" +
                        "尊敬的玩家，十分感谢您通关本游戏！\n" +
                        "您最终剩余%d生命值,%d次提示查看机会。\n" +
                        "您可以选择以下指令：\n" +
                        "输入“N”：开始新游戏\n" +
                        "输入“L”：查看游戏存档\n" +
                        "输入“Q”：退出游戏", game.getHealth(), game.getHintCount()));

        Menu quitMenu = new QuitMenu(game, "检测到本次游戏尚未存档，是否确认退出？\n" +
                "输入“S”：保存游戏并退出\n" +
                "输入“Q”： 直接退出\n" +
                "输入“M”： 返回当前游戏");

        String[] part = new String[2];
        while(game.getLevel() <= scene.length){
            clearScreen();
            if(game.getHealth() <= 0){
                game = gameOverMenu.prompt(startMenu, loadMenu, saveMenu);
            }
            if(game.getLevel() == scene.length - 1){
                System.out.println(scene[game.getLevel()]);
                System.out.println("按任意键继续……");
                inputScanner.nextLine();
                game = finaleMenu.prompt(startMenu, loadMenu, saveMenu);
            }
            part = scene[game.getLevel()].split("\n\n");
            System.out.println("===================================================================================");
            if(!game.getSaveName().isEmpty()){
                System.out.printf("「%s」\n", game.getSaveName());
            }
            System.out.println(part[0] + "\n");
            System.out.printf("当前状态：%d生命值，%d次提示查看机会。\n",
                            game.getHealth(), game.getHintCount());
            System.out.print(part[1]);

            String[] results = resultArray[game.getLevel()].split("\n\n");
            String choice = "";
            while(choice.isEmpty()){
                choice = inputScanner.nextLine();
            }
            if(choice.equalsIgnoreCase("Q")){
                game = quitMenu.prompt(startMenu, loadMenu, saveMenu);
                continue;
            }
            if(choice.equalsIgnoreCase("L")){
                game = loadMenu.prompt(startMenu, loadMenu, saveMenu);
                continue;
            }
            if(choice.equalsIgnoreCase("S")){
                saveMenu.prompt(startMenu, loadMenu, saveMenu);
                continue;
            }
            if(choice.equalsIgnoreCase("H")){
                hintMenu(game, hintArray);
                inputScanner.nextLine();
                continue;
            }
            System.out.println("===================================================================================");
            try{
                int number = Integer.parseInt(choice);
                if(number == Integer.parseInt(answerArray[game.getLevel()])){
                    System.out.println(results[number - 1]);
                    game.setLevel(game.getLevel() + 1);
                    if(results[number - 1].startsWith("（奇遇）")){
                        game.setHealth(game.getHealth() + 1);
                    }
                }else if(number > 0 && number <= results.length){
                    System.out.println(results[number - 1]);
                    if(!part[0].startsWith("（特殊）")){
                        game.setHealth(game.getHealth() - 1);
                    }else{
                        game.setLevel(game.getLevel() + 1);
                    }
                }else{
                    throw new NumberFormatException();
                }
            }catch (NumberFormatException e){
                System.out.println("无效输入，请重新输入指令……");
            }

            System.out.println("\n按任意键进行下一步行动……");
            inputScanner.nextLine();
        }
    }
}
