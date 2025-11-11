# API dokumentation

## Päätepisteet

Rajapinnat ovat käytettävissä vain kirjautuneilla käyttäjillä.

Käyttäjien oikeudet on jaettu kolmeen kategoriaan: `ADMIN`, `USER` ja `omistaja`.
`ADMIN` ja `USER` ovat käyttäjäryhmiä ja `omistaja` on taas vertailu tunnistautuneen käyttäjän ja tietyn asian kirjoittaneen käyttäjän välillä.
API päätepisteet käyttävät tunnistautumiseen simple authia.

### AppUsers

*Ohjelmiston käyttäjäista*

**/appusers** & **/appusers/{id}

**Metodit: vaaditut oikeudet**
	- GET: ADMIN
	- POST: ADMIN
	- DELETE: ADMIN
	- 