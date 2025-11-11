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
	- Tuhoa kaupunki: ADMIN
	
#### Paikat tietyssä kaupungissa - /city/{id}

	- Valitse paikka linkki
	- Tuhoa paikka: ADMIN
	
#### Kommentit tietystä paikasta

	- Lisää kommentti linkki: USER, ADMIN
	- Editoi kommenttia linkki: omistaja, ADMIN
	- Tuhoa kommentti: omistaja, ADMIN

## REST dokumentaatio

Sovellus sisältää REST rajapinnan, jonka polut muodostuvat periaatteella kaikki:`{baseurl}/resources`, yksittäinen: `{baseurl}/resources/{id}`.

[REST dokumentaatio](REST_documentation)

