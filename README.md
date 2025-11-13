# Back-end ohjelmointi harjoitustyö

## Suomen Kaupungit

Selaa Suomen maakuntia, kaupunkeja ja paikkoja, sekä kommentoi paikkoja.

### Käyttäjäoikeudet

Käyttäjien oikeudet on jaettu kolmeen kategoriaan: `ADMIN`, `USER` ja `omistaja`.
`ADMIN` ja `USER` ovat käyttäjäryhmiä ja `omistaja` on taas vertailu tunnistautuneen käyttäjän ja tietyn asian kirjoittaneen käyttäjän välillä.

### Sivut

#### Etusivu - /

*Lista Suomen maakunnista*

	- Valitse maakunta linkki
	- Lisää maakunta linkki: ADMIN
	- Muokkaa maakuntaa linkki: ADMIN
	- Tuhoa maakunta: ADMIN
	- Tyhjennä tietokanta: ADMIN
	- Uudelleenalusta tietokanta: ADMIN
	
#### Lisää maakunta(ADMIN) - /region/add

	- Täytä maakunnan tiedot ja tallenna

#### Muokkaa maakuntaa(ADMIN) - /region/edit/{id}

	- Täytä maakunnan tiedot ja tallenna
	
#### Kaupungit tietyssä maakunnassa - /region/{id}

	- Valitse kaupunki linkki
	- Lisää kaupunki linkki: ADMIN
	- Muokkaa kaupunkia linkki: ADMIN
	- Tuhoa kaupunki: ADMIN

#### Lisää kaupunki(ADMIN) - /city/add

	- Täytä kaupungin tiedot ja tallenna
	
#### Muokkaa kaupunkia(ADMIN) - /city/{id}

	- Täytä kaupungin tiedot ja tallenna
	
#### Paikat tietyssä kaupungissa - /city/{id}

	- Valitse paikka linkki
	- Lisää paikka linkki: ADMIN
	- Muokkaa paikkaa linkki: ADMIN
	- Tuhoa paikka: ADMIN
	
#### Kommentit tietystä paikasta - /location/{id}

	- Lisää kommentti linkki: USER, ADMIN
	- Editoi kommenttia linkki: omistaja, ADMIN
	- Tuhoa kommentti: omistaja, ADMIN
	
#### Lisää kommentti(ADMIN, USER) - /location/{locationId}/comment/add

	- Täytä kommentin tiedot ja tallenna
	
#### Muokkaa kommenttia(omistaja) - /location/{locationId}/comment/{commentId}

	- Täytä kommentin tiedot ja tallenna
	
#### Alustava tietokantasuunnittelu

*Suunnitelma tehty pohjaksi ohjelman rakenteelle, mutta sitä ei ole päivitetty ajankohtaiseksi.*

![Tietokantamalli](/resources/DB_design.png)

## REST dokumentaatio

Sovellus sisältää REST rajapinnan, jonka polut muodostuvat periaatteella kaikki:`{baseurl}/resources`, yksittäinen: `{baseurl}/resources/{id}`.

[REST dokumentaatio](REST_documentation.md)

