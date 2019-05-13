public class Access {
    public sql_func funct=new sql_func();
    public boolean isStudent(){
        return funct.getAccessLevel()=="Student";


    }
    public boolean isProfesor(){
        return funct.getAccessLevel()=="Profesor";


    }
    public boolean isSecretar(){
        return funct.getAccessLevel()=="Secretar";


    }
    public boolean isAdministrator(){
        return funct.getAccessLevel()=="Administrator";

    }

}
