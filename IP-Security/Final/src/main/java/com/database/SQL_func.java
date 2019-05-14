package com.database;
import java.sql.*;
import java.util.Random;

/**
 * Clasa care contine functii cu operatii pentru baza de date
 */
public class SQL_func {
    String way;

    /**
     * Constructor cu rol de plasare a path-ului
     * @param path Un String cu path-ul bazei de date
     */
    public SQL_func(String path)
    {
        way=path;
    }
    /**
     *  Functia care creeaza conexiunea cu baza de date
     * @return Returneaza conexiunea
     */
    private Connection connect() {
        String url = "jdbc:sqlite:" + way;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    /**
     * Functia care returneaza formula de calcul pentru toate materiile
     * @return Returneaza un string cu materii si formule
     */
    public String selectFormula() {
        String result = "";
        String query = " Select formula_calcul from profesori";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                result+= rs.getString("formula_calcul") + " | ";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    /**
     * Functia care returneaza formula pentru o anumita materie
     * @param id Parametrul care identifica materia
     * @return Returneaza un string cu formula materiei
     */
    public String selectFormula(String id) {
        String result = "";
        String query = " Select formula_calcul from profesori where id_materie=";
        query+=id;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            result =(rs.getString("formula_calcul") + "\t");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    /**
     * Functia care face update-ul campului valori_note din baza de date pentru un anumit student la o anumita materie
     * este evitata posibilitatea de SQL injection folosind query precompilat
     * @param id_s Parametrul care identifica id-ul studentului
     * @parma id_m Parametrul care identifica id-ul materie
     * @param note Parametrul care identifica nota ce va fi setata
     */
    public void updateNote(String id_s, String id_m, String note)
    {
        String query = "Update materii set valori_note= ? where id_student= ? and id_materie= ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, note);
            pstmt.setString(2, id_s);
            pstmt.setString(3, id_m);
            // update
            pstmt.executeUpdate();
            System.out.println("Succes!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Functia care returneaza toate notele din baza de date(asociate cu o materie si un student)
     * @return Returneaza un string cu studentii, materiile si notele
     */
    public String selectNote(){
        String result="";
        String query="Select id_materie,id_student,valori_note from materii";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                result+= rs.getString("id_student") +"  " + rs.getString("id_materie") + " " + rs.getString("valori_note")+ " | ";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    /**
     * Functia care returneaza notele unui anumit student la o anumita materie
     * @param id_s Parametrul care identifica id-ul studentului
     * @param id_m Parametrul care identifica id-ul materie
     * @return Returneaza un string cu notele studentului respectiv la materia respectiva
     */
    public String selectNote(String id_s,String id_m){
        String result="";
        String query="Select valori_note from materii where id_student=" + id_s+ " and id_materie=" + id_m;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            result = rs.getString("valori_note");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    /**
     * Functia care face update-ul campului formula_calcul din tabela materii pe baza id-ului materiei
     * este evitata posibilitatea de SQL injection folosind query precompilat
     * @param id_m Parametrul care identifica id-ul materiei
     * @param formula Parametrul care identifica noua formula
     */
    public void updateFormula(String id_m,String formula)
    {
        String query="Update profesori set formula_calcul = ? where id_materie = ?";
        try ( Connection conn =this.connect();
              PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1,formula);
            pstmt.setString(2,id_m);
            pstmt.executeUpdate();
            System.out.println("Succes!");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Functia care insereaza o materie noua in baza de date
     * este evitata posibilitatea de SQL injection folosind query precompilat
     * @param id_m Parametrul care identifica id-ul materiei
     * @param id_s Parametrul care identifica id-ul studentului
     * @param nume Parametrul care identifica numele materiei
     * @param note Parametrul care identifica valorile notelor
//     * @param formula Parametrul care identifica formula de calcul
     */
    public void insertMaterii(String id_m,String id_s,String nume,String note){
        String query = "Insert into materii(id_materie,id_student,denumire_materie,valori_note,situatiePromovare) VALUES (?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id_m);
            pstmt.setString(2, id_s);
            pstmt.setString(3, nume);
            pstmt.setString(4, note);
            pstmt.setString(5, "");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Functia care returneaza notele si informatiile unui student in functie de materie si profesorul ce preda materia respectiva
     * @param id_m Parametrul care identifica id-ul materiei
     * @param id_p Parametrul care identifica id-ul profesorului
     * @return Returneaza un string cu id-ul, numele, prenumele, grupa si notele studentului respectiv
     */
    public String selectCatalog(String id_m, String id_p){
        String result="";
        String query="Select s.id_student,s.nume,s.prenume,s.grupa,m.valori_note from studenti s join materii m on s.id_student = m.id_student join profesori p on m.id_materie = p.id_materie where m.id_materie=" + id_m + " and p.id_profesor=" + id_p;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                result+= rs.getString("id_student") +"  " + rs.getString("nume") + " " + rs.getString("prenume")+ " " + rs.getString("grupa") + " " + rs.getString("valori_note") + " | ";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    /**
     * Functia care returneaza denumirea materiei predate de profesorul dat ca parametru
     * @param id_p Parametrul care identifica id-ul profesorului
     * @return Returneaza un string cu denumirea materiei
     */
    public String selectDenumireMaterii(String id_p){
        String result="";
        String query="Select denumire_materie from profesori where id_profesor=" + id_p;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                result+= rs.getString("denumire_materie")  + " | ";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    /**
     * Functia care insereaza valori pentru campurile tabelei profesori
     * @param id_p Parametrul care identifica id-ul profesorului
     * @param nume Parametrul care identifica numele profesorului
     * @param prenume Parametrul care identifica prenumele profesorului
     * @param id_m Parametrul care identifica id-ul materiei
     * @param den_m Parametrul care identifica materia predate de catre profesorul respectiv
     */
    public void insertProfesori(String id_p, String nume,String prenume, String id_m,String den_m,String formula)
    {
        String query = "Insert into profesori(id_profesor,nume,prenume,id_materie,denumire_materie,formula_calcul,antet,criteriiPromovare) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id_p);
            pstmt.setString(2, nume);
            pstmt.setString(3, prenume);
            pstmt.setString(4, id_m);
            pstmt.setString(5, den_m);
            pstmt.setString(6, formula);
            pstmt.setString(7, "");
            pstmt.setString(8, "");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Functia care insereaza in profesori si genereaza automat id-ul si cauta numele si prenumele din intrarile deja inserate
     * @param id_p Parametrul care identifica id-ul profesorului
     * @param den_m Parametrul care identifica materia predate de catre profesorul respectiv
     */
    public void insertProfesori(String id_p,String den_m)
    {

        String query = "Insert into profesori(id_profesor,nume,prenume,id_materie,denumire_materie,formula_calcul,antet,criteriiPromovare) VALUES (?,?,?,?,?,?,?,?)";
        String aux=getNumePrenumeProf(id_p);
        String[] numePrenume=aux.split(" ");
        String nume=numePrenume[0];
        String prenume = numePrenume[1];
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id_p);
            pstmt.setString(2, nume);
            pstmt.setString(3, prenume);
            pstmt.setInt(4, getMaxIdMaterie());
            pstmt.setString(5, den_m);
            pstmt.setString(6, "");
            pstmt.setString(7, "");
            pstmt.setString(8, "");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Functia care numara utilizatorii cu acelasi username
     * @param name Parametrul care identifica username-ul unui utilizator
     * @return Returneaza un numar ce reprezinta utilizatorii cu acelasi username
     */
    public int countUsersByName(String name) {
        int result = 0;
        String query = "select username from utilizatori where username like '" + name + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next())
                result ++;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    /**
     * Functia care returneaza valoarea din coloana salt_parola a tabelei utilizatori din baza de date
     * @param name Parametrul care identifica name-ul utilizatorului
     * @return Returneaza valoarea din coloana salt_parole
     */
    public String getSalt(String name) {
        String result = "";
        String query = "select salt_parola from utilizatori where username like '" + name +"'" ;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                result+= rs.getString("salt_parola");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    /**
     * Functia care numara utilizatorii cu aceeasi adresa de mail din baza de date
     * @param mail Parametrul care identifica adresa de mail a utilizatorului
     * @return Returneaza numarul de utilizatori cu adresa de mail identica din baza de date
     */
    public int countUsersByMail(String mail) {
        int result = 0;
        String query = "select email from utilizatori where email like '" + mail + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next())
                result ++;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    /**
     * Functia care numara utilizatorii cu username-ul si parola identice
     * @param username Parametrul care identifica username-ul utilizatorului
     * @param pass Parametrul care identifica parola utilizatorului
     * @return Returneaza numarul de persoane cu acelasi username si parola
     */
    public int countUsersByUsernamePass(String username,String pass) {
        int result = 0;
        String query = "select username from utilizatori where username like '%" + username + "' and parola like '%" +pass + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next())
                result ++;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    /**
     * Functia care numara rezultatele din tabela verificare ce au username si auth_code identice
     * @param username Parametrul care identifica usernameul utilizatorului
     * @param auth_code Parametrul care identifica codul de autentificare din baza de date
     * @return Returneaza numarul de rezultate din tabela verificare cu acelasi username si cu acelasi cod din baza de date
     */
    public int checkAuthCode(String username, String auth_code) {
        int result = 0;
        String query = "select * from verificare where username like '" + username + "' and cod like '" +auth_code + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next())
                result ++;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    /**
     * Functia care insereaza un nou utilizator in baza de date
//     * @param id_u Parametrul care identifica id-ul utilizatorului
     * @param username Parametrul care identifica username-ul utilizatorului
     * @param email Parametrul care identifica adresa de mail a utilizatorului
     * @param pas Parametrul care identifica parola utilizatorului
     * @param salt Parametrul care identifica valoarea din campul salt_parola a utilizatorului
//     * @param nrt Parametrul care identifica numarul de telefon al utilizatorului
//     * @param tip_u Parametrul care identifica tipul utilizatorului
//     * @param ver Parametrul care identifica valoarea din campul verificare a utilizatorului
     */
    public void addNewUser(String username,String email,String pas,String salt ,String auth)
    {
        String query = "Insert into utilizatori(id_utilizator,username,email,parola,salt_parola,numar_telefon,tip_utilizator,verificare) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, getMaxIdUtilizator());
            pstmt.setString(2, username);
            pstmt.setString(3, email);
            pstmt.setString(4, pas);
            pstmt.setString(5, salt);
            pstmt.setString(6, "");
            pstmt.setString(7, "Student");
            pstmt.setString(8, "0");
            pstmt.executeUpdate();
            addNewU(username,auth);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Functia care insereaza un set de valori in tabela verificare
     * @param username Parametrul care identifica username-ul utilizatorului
     * @param auth Parametrul care identifica valoarea din campul cod
     */
    public void addNewU(String username,String auth)
    {
        String query="Insert into verificare(username,cod) VALUES (?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1,username);
            pstmt.setString(2,auth);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Functia care genereaza id-ul nou pentru o intrare noua in tabela profesori
     * @return Returneaza valoarea id-ului nou
     */
    public int getMaxIdMaterie()
    {
        int result=0;
        String query="Select id_materie as maxID from profesori";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next())
            {
                Integer aux =Integer.parseInt(rs.getString("maxID"));
                if( aux > result)
                    result = aux;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result+1;
    }
    /**
     * Functia care returneaza numele si prenumele profesorului identificat dupa id_p
     * @param id_p Parametrul care identifica id-ul profesorului
     * @return Returneaza un string ce contine numele si prenumelui profesorului
     */
    public String getNumePrenumeProf(String id_p){
        String result="";
        String query="Select nume,prenume from profesori where id_profesor=" + id_p;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            result+= rs.getString("nume")+ " "  +  rs.getString("prenume");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * Functia care schimba valoarea din campul verificare pentru un user dat ca parametru (din 0 face -1 daca userul a fost verificat )
     * @param username Parametrul care identifica username-ul unui utilizator
     */
    public void confirmUser(String username){
        String query="Update utilizatori set verificare = ? where username = ?";
        try ( Connection conn =this.connect();
              PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1,"1");
            pstmt.setString(2,username);
            pstmt.executeUpdate();
            System.out.println("Succes!");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Functia care genereaza un id nou pentru un utilizator nou
     * @return Returneaza valoarea id-ului nou
     */
    public Integer getMaxIdUtilizator()
    {
        int result=0;
        String query="Select id_utilizator as maxID from utilizatori";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next())
            {
                Integer aux =Integer.parseInt(rs.getString("maxID"));
                if( aux > result)
                    result = aux;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result+1;
    }

    /**
     * Functia care verifica daca un utilizator este logat
     * @param username Parametrul are identifica username-ul utilizatorului
     * @return False daca utilizatorul nu este logat, true daca este
     */
    public boolean verificareLogIn(String username)
    {
        String query="Select verificare from utilizatori where username like '" + username + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if(rs.getInt("verificare") == 0)
                return false;
            else
                return true;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;

    }

    /**
     * Functia care returneaza antetul din tabela profesori in functie de id_m
     * @param id_m Parametrul care identifica id-ul materiei
     * @return Returneaza un string ce contine antetul
     */
    public String selectAntet(String id_m)
    {
        String result = "";
        String query = " Select antet from profesori where id_materie=";
        query+=id_m;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            result =(rs.getString("antet") + "\t");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * Functia care updateaza antetul din tabela profesori
     * @param antet Parametrul care identifica antetul
     * @param id_m Parametrul care identifica id-ul materiei
     */
    public void updateAntet(String antet,String id_m)
    {
        String query="Update profesori set antet = ? where id_materie = ?";
        try ( Connection conn =this.connect();
              PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1,antet);
            pstmt.setString(2,id_m);
            pstmt.executeUpdate();
            System.out.println("Succes!");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Functia care adauga un nou student in baza de date
     * @param student e un String de forma id,nume,prenume,grupa.email
     */
    public void insertNewStudent(String student)
    {
        String date[]=student.split(" ");
        String id = date[0];
        String nume = date[1];
        String prenume = date[2];
        String grupa = date[3];
        String eMail = date[4];
        String query = "Insert into studenti(id_student,nume,prenume,email,grupa) VALUES (?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            pstmt.setString(2, nume);
            pstmt.setString(3, prenume);
            pstmt.setString(4, grupa);
            pstmt.setString(5, eMail);
            pstmt.executeUpdate();
            System.out.println("Succes!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        insertUserByMail(eMail);
    }

    /**
     * Functia care adauga un user in baza de date pe baza eMail-ului
     * @param eMail e un String de forma *****@*****; ex : victor.paval@info.uaic.ro
     */
    public void insertUserByMail(String eMail) {
        String[] user = eMail.split("@");
        String username = user[0];
        String query = "Insert into utilizatori(id_utilizator,username,email,parola,salt_parola,numar_telefon,tip_utilizator,verificare) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, getMaxIdUtilizator());
            pstmt.setString(2, username);
            pstmt.setString(3, eMail);
            pstmt.setString(4, createSalt());
            pstmt.setString(5, createSalt());
            pstmt.setString(6, "");
            pstmt.setString(7, "Student");
            pstmt.setString(8, "0");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creaza un salt provizoriu pntru a adauga un user dupa mail
     * @return salt-ul creat
     */
    public String createSalt(){
    String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    StringBuilder salt = new StringBuilder();
    Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
        int index = (int) (rnd.nextFloat() * SALTCHARS.length());
        salt.append(SALTCHARS.charAt(index));
    }
    String saltStr = salt.toString();
        return saltStr;

    }
    public String selectCriterii(String id){
        String result = "";
        String query = " Select criteriiPromovare from profesori where id_materie=";
        query+=id;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            result =(rs.getString("criteriiPromovare  ") + "\t");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    public void updateCriterii(String id_m, String criterii){
        String query="Update profesori set criteriiPromovare = ? where id_materie = ?";
        try ( Connection conn =this.connect();
              PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1,criterii);
            pstmt.setString(2,id_m);
            pstmt.executeUpdate();
            System.out.println("Succes!");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updatePromovare(String promovare,String id_s,String id_m){
        String query = "Update materii set situatiePromovare= ? where id_student= ? and id_materie= ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, promovare);
            pstmt.setString(2, id_s);
            pstmt.setString(3, id_m);
            // update
            pstmt.executeUpdate();
            System.out.println("Succes!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void addSession(String username,String session_id,String last_activity)
    {
        String query = "Insert into sessions(session_id,username,last_activity) VALUES (?,?,?)";
        System.out.println("session:"+session_id);
        System.out.println("last:"+last_activity);
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, session_id);
            pstmt.setString(2, username);
            pstmt.setString(3, last_activity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteSession(String session_id)
    {
        String query = "DELETE FROM sessions WHERE session_id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, session_id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public int countSession(String session_id)
    {
        int result = 0;
        String query = "select username from sessions where session_id= " + session_id;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next())
                result ++;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    public String getTime(String s_id)
    {
        String result = "";
        String query = " Select last_activity from sessions where session_id=";
        query+=s_id;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while(rs.next())
                result+=(rs.getString("last_activity") + "\t");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    public String getUsername(String s_id)
    {
        String result = "";
        String query = " Select username from sessions where session_id=";
        query+=s_id;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while(rs.next())
                result+=(rs.getString("username") + "\t");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    public void updateSessionActivity(String s_id,String new_time)
    {
        String query = "Update sessions set last_activity= ? where session_id=?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, new_time);
            pstmt.setString(2, s_id);
            // update
            pstmt.executeUpdate();
            System.out.println("Succes!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public String getAccessLevel(String username)
    {
        String result="None";
        String query="select tip_utilizator from utilizatori where username=? and verificare=1";
        try ( Connection conn =this.connect();
              PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1,username);
            ResultSet rs=pstmt.executeQuery();
            while(rs.next())
            {
                result=rs.getString("tip_utilizator");
            }
            System.out.println("Succes!");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
