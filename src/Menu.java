public abstract class Menu {
    String msg;
    static Game currentGame;
    Menu(Game game, String msg){
        currentGame = game;
        this.msg = msg;
    }
    protected void displayMsg(){
        System.out.println(msg);
    }

    public abstract Game prompt(Menu start, Menu load, Menu save);
}
