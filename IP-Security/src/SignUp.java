public class SignUp {
    String nume;
    String password;
    String confirmPassword;
    String webmail;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getNume() {
        return nume;
    }

    public String getPassword() {
        return password;
    }

    public String getWebmail() {
        return webmail;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWebmail(String webmail) {
        this.webmail = webmail;
    }

    public boolean sendMail(String webmail){
        return true;
    }
    public boolean validate() {
        return true;
    }
public void activateAccount()  {

}
}
