import java.io.File;
import java.util.Scanner;

public class LoadMenu extends Menu{
    LoadMenu(Game currentGame, String msg){
        super(currentGame, msg);
    }

    @Override
    public Game prompt(Menu start, Menu load, Menu save) {
        displayMsg();
        File saveFolder = new File(Game.getSaveFolder());
        File[] files = saveFolder.listFiles();
        Scanner scanner = new Scanner(System.in);
        if(files == null){
            System.out.println("目前无任何存档记录，按任意键返回当前游戏……");
            scanner.nextLine();
            return currentGame;
        }else{
            int saveNum = 1;
            for(File file: files){
                System.out.println(saveNum + ", " + file.getName().replace(".ser", ""));
                saveNum++;
            }
            System.out.println("\n输入数字：读取相应编号的存档\n" +
                    "输入“M”： 回到当前游戏");
            String input = "";
            while(input.isEmpty()){
                input = scanner.nextLine();
                if("M".equalsIgnoreCase(input)){
                    if(currentGame == null){return start.prompt(start, load, save);}
                    return currentGame;
                }else{
                    try{
                        int loadNum = Integer.parseInt(input) - 1;
                        if(loadNum >= 0 && loadNum < files.length){
                            currentGame = Game.load(files[loadNum].getName());
                        }else{
                            throw new NumberFormatException();
                        }
                    }catch (NumberFormatException e){
                        System.out.println("无效输入，请输入有效存档号码以读取存档。\n若要回到主菜单，请输入“M”……");
                        input = "";
                    }
                }
            }
        }
        return currentGame;
    }
}
