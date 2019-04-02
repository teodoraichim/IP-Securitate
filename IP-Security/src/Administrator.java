public class Administrator implements Users {
    String name;
    int permission;

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
}
