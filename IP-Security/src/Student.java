public class Student implements Users {

    String name;
    int permission;
    SignUp signUp;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setPermission(int permission) {
        this.permission = permission;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPermission() {
        return permission;
    }
    public void signUp(){

    }
}
