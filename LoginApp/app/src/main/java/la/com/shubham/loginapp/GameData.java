package la.com.shubham.loginapp;

/**
 * Created by Shubham on 30-07-2015.
 */
public class GameData {

    private Integer GameID, HighScore = 0 , timesPlayed;
    private String username;

    public Integer getGameID() {
        return GameID;
    }

    public void setGameID(Integer gameID) {
        GameID = gameID;
    }

    public Integer getHighScore() {
        return HighScore;
    }

    public void setHighScore(Integer highScore) {
        HighScore = highScore;
    }

    public Integer getTimesPlayed() {
        return timesPlayed;
    }

    public void setTimesPlayed(Integer timesPlayed) {
        this.timesPlayed = timesPlayed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public GameData(String username,Integer GameID){
    this.username = username;
    this.GameID = GameID;
}
    public GameData(String username, Integer GameID, Integer HighScore){
        this.username = username;
        this.GameID = GameID;
        this.HighScore = HighScore;
    }
}
