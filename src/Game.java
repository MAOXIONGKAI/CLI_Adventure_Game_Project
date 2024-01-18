import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Game implements Serializable{
    private int health;
    private int hintCount;
    private int level;
    private static String saveFolder = System.getProperty("user.home") +
                                            File.separator +
                                            "GameSave";
    private String saveName = "";
    Game(int health, int hintCount, int level){
        setHealth(health);
        setHintCount(hintCount);
        setLevel(level);
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public int getHintCount() {
        return hintCount;
    }

    public void setHintCount(int hintCount) {
        this.hintCount = hintCount;
    }

    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public static String getSaveFolder() {
        return saveFolder;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public static void save(Game game){
        File saveFolder = new File(getSaveFolder());
        if(!saveFolder.exists()){
            saveFolder.mkdir();
        }
        DateTimeFormatter fileDate = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String saveName = String.format( "%s - Level %d.ser",
                LocalDateTime.now().format(fileDate),
                game.getLevel());
        try(FileOutputStream fileOut = new FileOutputStream(saveFolder + File.separator + saveName);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);){
            game.setSaveName(saveName.replace(".ser", ""));
            objectOut.writeObject(game);
            System.out.println("游戏存档成功！\n按任意键继续……");
            new Scanner(System.in).nextLine();
        }catch(IOException e){
            System.out.println(saveFolder + saveName);
            System.out.println("无法解析存档地址，存档失败");
        }
    }

    public static Game load(String fileName){
        Game load = null;
        try(FileInputStream fileIn = new FileInputStream(saveFolder + File.separator + fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);){
            load = (Game) objectIn.readObject();
        }catch(IOException e){
            System.out.println("文件缺失，存档读取失败");
        }catch(ClassNotFoundException e){
            System.out.println("类缺失：无法找到Game类定义");
        }
        return load;
    }
}
