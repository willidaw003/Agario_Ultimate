/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cj
 */
public class GameState {
    
    static boolean isMenu = true, isPlay = false, isPause = false;

    public static void setIsMenu(boolean isMenu) {
        GameState.isMenu = isMenu;
    }

    public static void setIsPlay(boolean isPlay) {
        GameState.isPlay = isPlay;
    }

    public static void setIsPause(boolean isPause) {
        GameState.isPause = isPause;
    }

    public static void toggleMenu() {
        isMenu = !isMenu;
    }
    
    public static void togglePlay() {
        isPlay = !isPlay;
    }
    
    public static void togglePause() {
        isPause = !isPause;
    }
    
}
