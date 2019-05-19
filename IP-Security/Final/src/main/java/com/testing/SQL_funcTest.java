package com.testing;

import com.database.SQL_func;
import com.security.HelpFunctions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SQL_funcTest extends HelpFunctions {
   SQL_func test= new SQL_func("D:\\Downloads\\IP-Securitate-master\\IP-Securitate-master\\IP-Security\\Final\\BD_Gestiunea");
   SQL_func test1 = new SQL_func("D:\\Downloads\\IP-Securitate-master\\IP-Securitate-master\\IP-Security\\Final\\BD_Gestiunea");
    @Test
    void selectFormula() {
     assertEquals(test.selectFormula(),test1.selectFormula());
    }

    @Test
    void updateNote() {
     test.updateNote("1","1","5");
     System.out.println("");
    }

    @Test
    void selectNote() {
     assertNotNull(test.selectNote());

    }

    @Test
    void selectNote1() {
     assertNotNull(test.selectNote("1","1"));
    }

    @Test
    void updateFormula() {
     test.updateFormula("1","l1+l2+l3");
     System.out.println("");
    }

    @Test
    void insertMaterii() {
     test.insertMaterii("1","1","Ingineria programarii avansat","5");

    }

    @Test
    void selectCatalog() {
     assertNotNull(test.selectCatalog("1","1"));
    }

    @Test
    void selectDenumireMaterii() {
     assertNotNull(test.selectDenumireMaterii("1"));
    }

    @Test
    void insertProfesori() {
     test.insertProfesori("122","Mihai", "Daniel", "122","Lol","t1+l1+l2");

    }


    @Test
    void countUsersByName() {
     assertNotNull(test.countUsersByName("Daniel"));
    }

    @Test
    void getSalt() {
     assertNotNull(test.getSalt("daniel.mihai"));
    }

    @Test
    void countUsersByMail() {
     assertNotNull(test.countUsersByMail("teodora.ichim"));
    }

    @Test
    void countUsersByUsernamePass() {
     assertNotNull(test.countUsersByUsernamePass("daniel.mihai","parola123"));
    }

    @Test
    void getMaxIdMaterie() {
     assertNotNull(test.getMaxIdMaterie());
    }

    @Test
    void getNumePrenumeProf() {
     assertNotNull(test.getNumePrenumeProf("1"));
     System.out.println("");
    }

    @Test
    void getMaxIdUtilizator() {
     assertNotNull(test.getMaxIdUtilizator());
    }

    @Test
    void verificareLogIn() {
     assertNotNull(test.verificareLogIn("daniel.mihai"));
    }

    @Test
    void selectAntet() {
     assertNotNull(test.selectAntet("1"));
    }

    @Test
    void updateAntet() {
     test.updateAntet("AntetNou","1");
     System.out.println("");
    }

    @Test
    void insertNewStudent() {
     test.insertNewStudent("733 eminesu mihai b1 eminesu.mihai@info.uaic.ro");
     System.out.println("");
    }

    @Test
    void insertUserByMail() {
     test.insertUserByMail("trololo.trololoi@info.uaic.ro");
    }

    @Test
    void createSalt() {
     assertNotNull(test.createSalt());
    }

    @Test
    void selectCriterii() {
     assertNotNull(test.selectCriterii("1"));
     System.out.println("");
    }

    @Test
    void updateCriterii() {
     test.updateCriterii("1","6");
     System.out.println("");
    }

    @Test
    void updatePromovare() {
     test.updatePromovare("5+","1","1");
     System.out.println("");
    }


}