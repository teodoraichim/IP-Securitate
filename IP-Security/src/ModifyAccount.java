public class ModifyAccount {
    String accountName;
    String newName;

    int newPermission;

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public void setNewPermission(int newPermission) {
        this.newPermission = newPermission;
    }

    public int getNewPermission() {
        return newPermission;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getNewName() {
        return newName;
    }
}
