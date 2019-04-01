# Documentatie-echipa Sercuritate
## Stocarea parolelor in baza de date
Pentru a proteja credentialele de logare ale utilizatorilor, parolele in baza de date nu vor fi stocare in plain-text ci folosind metoda salted hash.
Va fi aplicata o functie hash pe parola introdusa de utlizator la care a fost concatenat un string random(salt-ul) ce va fi stocat de asemenea in baza de date.
Saltul asigura imposibilitatea aflari parolei folosind atacuri de brute-force cu dictionare.

## Protocolul https
Pentru a asigura o conexiune securizata intre client si server se va folosi protocolul de comunicare securizata https.
Acesta se foloseste de certificate SSL,eliberate de autoritati,care confirma identitatea serverului.
Pe baza acestor certificate se initiaza o procedura numita TLS handshake:
> Cheie publica:folosita la criptarea unui mesaj
> Cheie privata:folosita la decriptarea unui mesaj

-Clientul(browserul) initiaza conexiunea trimitand lista de cifruri (algortmi de criptare) suportate
-Serverul alege cifrul cel mai sigur pe care browserul il suporta si apoi trimite  certifactul ssl (ce include o cheie publica)si comunica acestuia ce cifru a decis sa utilizeze. 
-Clientul verifica certificatul SSL si,daca este valid, creaza un fisier numit pre-Master secret ce va fi folosit pentru generarea unei noi chei private.
-Clientul trimite pre-Master -ul criptat,folosind cheia publica primita anterior ,serverului
-Folosind cheia privata serverul decripteaza pre-Masterul si genereaza pe baza acestuia o cheie privata ce va fi identica cu cea generata de client,intrucat se vor folosi aceleasi metode de criptare(suporatate si de client,si de server) pe aceleasi siruri de biti.
Astfel cheia privata pe baza careia se cripteaza conexiunea https nu este trimsisa pe canalul de comunicatie,ci generata independent in client si in server pe baza unor metode de criptare si mesaje ce au fost impartasite pe canalul de comunicatie.
Toate mesajele trimise ulterior intre client si server vor fi criptate folosind aceasta cheie privata.

In cadrul aplicatiei se pot folosi, initial, certificate SSL locale generate folosind toolul keytool din JDK.
Java suporta realizare de conexiuni https, existand clasa HttpsServer.

## Sistemul de verifiare a email-ului
Pentru a se asigura identitatea unui potential nou utilizator ca student al Facultatii de Informatica, pentru a-si creea un cont acesta trebuie sa confirme accesul la adresa de mail primita de la facultate.Acest lucru se face astfel:
Dupa ce primeste cererea de inregistrare a unui nou utilizator, serverul va verifica daca adresa este valida(de formatul prenume.nume@info.uaic.ro) si daca aceasta adresa de mail a fost deja folosita pentru crearea unui cont(exista deja in baza de date a utilizatorilor).
Daca este cazul de o adresa noua valida,atunci se va genera un cod hash de 32 de caractere in mod random ce va fi salvat alaturi de credentiale in baza de date si va fi folosit pentru creerea unui link dinamic de tipul:
```
http://www.yourwebsite.com/verify.php?email='.$email.'&hash='.$hash.'   
```
Acest link va fi trimis prin email la adresa specificata de user.
In baza de date fiecare utilizator va mai avea un camp "activated" ce va retine statusul de activare al contului.
Odata accesat acest link contul utilizatorului va fi activat.

Pentru trimiterea emailurilor se poate folosi API-ul JavaMail.


## Alte aspecte de securitate ce trebuiesc luate in considerare la nivelul dezvoltarii aplicatiei

### SQL injection
Una dintre cele mai comune tehnici de web hacking.Profita de nesepararea datelor 
de instructiuni in modalitatea initierii de query-uri sql.
Se intampla atunci cand userului ii este cerut input,cum ar fi email-ul sau parola si acesta trimite de fapt instructiuni sql ce vor rula in baza de date,compromitand securitatea si integritatea bazei de date.Un hacker,folosind aceasta metoda poate sa extraga username-urile,parolele si alte date din tabele sau poate sa altereze,sa modifice valorile retinute in diferite campuri.
Pentru a proteja aplicatia de asemenea atacuri ,interogarile bazei de date trebuiesc implementate folosind prepared statements(queriuri parametrizate).Acestea separa in mod clar datele de codul sql.
Exemplu de prepared statements(Java):
```
String custname = request.getParameter("customerName"); 
String query = "SELECT account_balance FROM user_data WHERE user_name = ? ";
PreparedStatement pstmt = connection.prepareStatement( query );
pstmt.setString( 1, custname); 
ResultSet results = pstmt.executeQuery( );
```

Exemplu de query vulnerabil la sql injection(Java):
```
String custname = request.getParameter("customerName"); 
String query = "SELECT account_balance FROM user_data WHERE user_name =  " + custname;
PreparedStatement stmt = connection.createStatement( query );
ResultSet results = stmt.executeQuery( );
```

