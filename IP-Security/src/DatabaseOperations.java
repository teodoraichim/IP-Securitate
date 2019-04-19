
public class DatabaseOperations {


    public int countUsersByName(String name) {
        return 0;
    }

    public String getSalt(String username) {
        return "65a5ce4dea98306d4826a7df93b02e4e";
    }

    public int countUsersByMail(String mail) {
        return 0;
    }

    public int countUsersByUsernamePass(String mail, String hash) {
        return 0;
    }

    public void addNewUser(String username, String mail, String salt, String hash, String auth) {
    }

    public int checkAuthCode(String username, String code) {
        return 1;
    }

}
