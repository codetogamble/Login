package la.com.shubham.loginapp;

/**
 * Created by Shubham on 09-07-2015.
 */
public class User {
    private String name,username,password,email;
    private Integer age, UserID = -1;

    public String getEmail() {
        return email;
    }

    public void setUserID(Integer userID) {
        this.UserID = userID;
    }

    public Integer getUserID() {
        return UserID;
    }

    public void setEmail(String name) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public User(String name,Integer age,String username,String password, String email){
        this.name = name;
        this.username = username;
        this.password = password;
        this.age = age;
        this.email = email;

    }
   /* public User(String name,Integer age,String username,String password){
        this.name = name;
        this.username = username;
        this.password = password;
        this.age = age;
        this.email = "";

    }
*/    public User(String username,String password){
        this.username = username;
        this.password = password;
        this.name = "";
        this.age = -1;
        this.email="";
    }
}
