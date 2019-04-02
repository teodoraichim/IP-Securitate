public class AddAccount {
    String nume;
    String password;
    int permission;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public String getPassword() {
        return password;
    }

    public String getNume() {
        return nume;
    }

    public int getPermission() {
        return permission;
    }
}
