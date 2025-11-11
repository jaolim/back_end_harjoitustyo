# API dokumentation

## Päätepisteet

Rajapinnat ovat käytettävissä vain kirjautuneilla käyttäjillä.

Käyttäjien oikeudet on jaettu kolmeen kategoriaan: `ADMIN`, `USER` ja `omistaja`.
`ADMIN` ja `USER` ovat käyttäjäryhmiä ja `omistaja` on taas vertailu tunnistautuneen käyttäjän ja tietyn asian kirjoittaneen käyttäjän välillä.
API päätepisteet käyttävät tunnistautumiseen simple authia.

### Päätepisteet, metodit ja vaaditut oikeudet


**/appusers** & **/appusers/{id}

	- GET: ADMIN
	- POST: ADMIN
	- DELETE: ADMIN
	- PUT: ADMIN
	
**/regions** & **/regions/{id}

	- GET: USER, ADMIN
	- POST: ADMIN
	- DELETE: ADMIN
	- PUT: ADMIN
	
**/cities** & **/cities/{id}

	- GET: USER, ADMIN
	- POST: ADMIN
	- DELETE: ADMIN
	- PUT: ADMIN
	
**/locations** & **/locations/{id}**

	- GET: USER, ADMIN
	- POST: ADMIN
	- DELETE: ADMIN
	- PUT: ADMIN
	
**/comments** & **/comments/{id}**

	- GET: USER, ADMIN
	- POST: USER, ADMIN
	- DELETE: ADMIN, omistaja
	- PUT: ADMIN, omistaja