package com.security;


import java.sql.*;

public class SQL_func{

    // Conectarea cu baza de date
    private Connection connect() {
        String url = "jdbc:sqlite:/home/silviu/JavaProjects/BD_Gestiunea";
        Connection conn = null;
        try {

            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    //Selectam toate formulele de calcul din baza de date
    public String selectFormula() {
        String result = "";
        String query = " Select denumire_materie, formula_calcul from materii";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                result += rs.getString("denumire_materie") + "  " + rs.getString("formula_calcul") + " | ";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    //Selectam doar o singura formula din baza de date indicata de id-ul materiei
    public String selectFormula(String id) {
        String result = "";
        String query = " Select formula_calcul from materii where id_materie=";
        query += id;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            result = (rs.getString("formula_calcul") + "\t");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    //Facem update-ul campului valori_note din baza de date pentru un anumit student la o anumita materie
    public void updateNote(String id_s, String id_m, String note) {
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

    //Selectam toate notele existente in baza de date
    public String selectNote() {
        String result = "";
        String query = "Select id_materie,id_student,valori_note from materii";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                result += rs.getString("id_student") + "  " + rs.getString("id_materie") + " " + rs.getString("valori_note") + " | ";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    //Selectam notele unui anumit student la o anumita materie
    public String selectNote(String id_s, String id_m) {
        String result = "";
        String query = "Select valori_note from materii where id_student=" + id_s + " and id_materie=" + id_m;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            result = rs.getString("valori_note");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    //Facem update-ul formulei din tabela materii pe baza id-ul materiei
    public void updateFormula(String id_m, String formula) {
        String query = "Update materii set formula_calcul = ? where id_materie = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, formula);
            pstmt.setString(2, id_m);
            pstmt.executeUpdate();
            System.out.println("Succes!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Inseram in tabela materii o intrare noua
    public void insertMaterii(String id_m, String id_s, String nume, String note, String formula) {
        String query = "Insert into materii(id_materie,id_student,denumire_materie,valori_note,formula_calcul) VALUES (?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id_m);
            pstmt.setString(2, id_s);
            pstmt.setString(3, nume);
            pstmt.setString(4, note);
            pstmt.setString(5, formula);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String selectCatalog(String id_m, String id_p) {
        String result = "";
        String query = "Select s.id_student,s.nume,s.prenume,s.grupa,m.valori_note from studenti s join materii m on s.id_student = m.id_student join profesori p on m.id_materie = p.id_materie where m.id_materie=" + id_m + " and p.id_profesor=" + id_p;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                result += rs.getString("id_student") + "  " + rs.getString("nume") + " " + rs.getString("prenume") + " " + rs.getString("grupa") + " " + rs.getString("valori_note") + " | ";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public String selectDenumireMaterii(String id_p) {
        String result = "";
        String query = "Select denumire_materie from profesori where id_profesor=" + id_p;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                result += rs.getString("denumire_materie") + " | ";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public void insertProfesori(String id_p, String nume, String prenume, String id_m, String den_m) {
        String query = "Insert into profesori(id_profesor,nume,prenume,id_materie,denumire_materie) VALUES (?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id_p);
            pstmt.setString(2, nume);
            pstmt.setString(3, prenume);
            pstmt.setString(4, id_m);
            pstmt.setString(5, den_m);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertProfesori(String id_p, String den_m) {
        SQL_func test = new SQL_func();
        String query = "Insert into profesori(id_profesor,nume,prenume,id_materie,denumire_materie) VALUES (?,?,?,?,?)";
        String aux = test.getNumePrenumeProf(id_p);
        String[] numePrenume = aux.split(" ");
        String nume = numePrenume[0];
        String prenume = numePrenume[1];
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id_p);
            pstmt.setString(2, nume);
            pstmt.setString(3, prenume);
            pstmt.setInt(4, getMaxId());
            pstmt.setString(5, den_m);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void confirmUser(String username)
    {

    }
    //Returneaza nummarul de utilizatori cu acelasi nume din baza de date.
    public int countUsersByName(String name) {
        int result = 0;
        String query = "select username from utilizatori where username like '" + name + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next())
                result++;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    //Returneaza valoarea din coloana salt_parola a tabelei utilizatori din baza de date.
    public String getSalt(String name) {
        String result = "";
        String query = "select salt_parola from utilizatori where username like '" + name + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                result += rs.getString("salt_parola") + " | ";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    public boolean verificareLogin(String username)
    {
        return false;
    }
    //Returneaza numarul de utilizatori cu acelasi mail din baza de date.
    public int countUsersByMail(String mail) {

        int result = 0;
        String query = "select email from utilizatori where email like '" + mail + "'";
        System.out.println("-"+query+'-');
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next())
                result++;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(result);
        return result;
    }

    //Returneaza numarul de utilizatori cu acelasi username si cu acelasi password din baza de date.
    public int countUsersByUsernamePass(String username, String pass) {
        int result = 0;
        String query = "select username from utilizatori where username like '%" + username + "' and parola like '%" + pass + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next())
                result++;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    //Returneaza numarul de rezultate din tabela verificare cu acelasi username si cu acelasi cod din baza de date.
    public int checkAuthCode(String username, String auth_code) {
        int result = 0;
        String query = "select * from verificare where username like '" + username + "' and cod like '" + auth_code + "'";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next())
                result++;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public void addNewUser(String id_u, String username, String email, String pas, String salt, String nrt, String tip_u, String ver) {
        String query = "Insert into utilizatori(id_utilizator,username,email,parola,salt_parola,numar_telefon,tip_utilizator,verificare) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id_u);
            pstmt.setString(2, username);
            pstmt.setString(3, email);
            pstmt.setString(4, pas);
            pstmt.setString(5, salt);
            pstmt.setString(6, nrt);
            pstmt.setString(7, tip_u);
            pstmt.setString(8, ver);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getMaxId() {
        int result = 0;
        String query = "Select max(id_materie) as maxID from profesori";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            // result=rs.getInt("maxID");
            if (rs.next())
                result = rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result + 1;
    }

    public String getNumePrenumeProf(String id_p) {
        String result = "";
        String query = "Select nume,prenume from profesori where id_profesor=" + id_p;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            result += rs.getString("nume") + " " + rs.getString("prenume");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
