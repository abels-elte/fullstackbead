# notes

## Specifikacio

Tech stack:
- React frontend
- Springboot backend
- Mongodb

Funkciok:
- User auth
- Jegyzetek letrehozasa / szerkesztese / torlese
- A jegyzeteket mappakba lehet organizalni
- A felhasznalo tudja importalni/exportalni a jegyzeteit egy zip fileban

API endpoint-ok:
- /auth
    - POST /auth/sign-in, egy json-t var a request body-ban amiben a {username, password} mezok talalatoak. Ez alapjan autentikalja a felhasznalot es ad egy JWT-t tokent neki ami 10 napig ervenyes
    - POST /auth/sign-up, egy json-t var a request body-ban amiben a {username, password} mezok talalatoak. Ez alapjan regisztralja a felhasznalot az alkalmazasban.
- /notes
    - GET /notes, vissza adja egy json tobben a felhasznalo osszes jegyzetet
    - POST /notes, egy json-t var a request body-ban amiben a {title, content} mezok talalhatok, ezen felul opcionalisan megadhato a parentId mezo amivel egy adott mappahoz rendelheto a jegyzet. Letrehozasa utan vissza adja az elmentett jegyzetet.
    - GET /notes/{id}, egy jegyzet id alapjan visszad adja annak a jegyzetnek az adatait.
    - POST /notes/{id}, egy jegyzet id-t es a request body a {title, content} mezok varja, ezek alapjan frissiti az adatbazisban az adott id jegyzetet.
    - DELETE /notes/{id}, egy jegyzet id-t var es torli az adatbazisbol
- /folders
    - GET /folders, vissza adja json formatumban a felhasznalo osszes mappajat es a benne talalhato jegyzeteket
    - POST /folders, a request body-ban json formatumban varja {name} mezot, letrehoz az adatbazisban egy ilyen nevu mappat.
    - GET /folders/{id}, visszater json formatumban egy adott mappaval
    - POST /folders/{id}, egy mappa id es a request body a {name} mezoket varja, ezek alapjan frissiti az adatbazisban az adott id-val rendelkezo mappa-t.
    - DELETE /folders/{id}, egy mappa id-t var es torli az adatbazisbol
- /export
    - GET metodus: vissza adja a felhasznalo jegyzeteit egy export.zip fileban.
- /import
    - POST metodus: egy file-t fogad a formdata "file" mezojeben. A zip file-ban talalhato txt fileokat importalja a felhasznalo jegyzetei koze. 