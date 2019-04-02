public class SignIn {
    String name;
    String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public boolean validate(){
        return true;
    }
}
