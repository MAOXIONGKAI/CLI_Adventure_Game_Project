import java.util.Scanner;

public class StartMenu extends Menu{
    StartMenu(Game currentGame, String msg){
        super(currentGame, msg);
    }
    @Override
    public Game prompt(Menu start, Menu load, Menu save){
        displayMsg();
        String input = "";
        Scanner scanner = new Scanner(System.in);
        while(input.isEmpty()){
            input = scanner.nextLine();
            if("A".equalsIgnoreCase(input)){
                currentGame = new Game(5,5, 0);
            }else if("L".equalsIgnoreCase(input)){
                currentGame = load.prompt(start, load, save);
            }else if("Q".equalsIgnoreCase(input)){
                System.exit(0);
            }else{
                System.out.println("无效输入，请重新输入指令...\n若您确认输入正确，请注意某些游戏指令只能在游戏内使用。");
                input = "";
            }
        }
        return currentGame;
    }
}
