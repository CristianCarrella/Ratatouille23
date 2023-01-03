CREATE TABLE ristorante(
	id_ristorante SERIAL PRIMARY KEY,
	nome VARCHAR(30) NOT NULL,
	telefono VARCHAR(30),
	indirizzo VARCHAR(50),
	logo BYTEA,
	nome_immagine VARCHAR(30),
	id_proprietario INTEGER NOT NULL
);

CREATE TYPE role AS ENUM ('admin', 'supervisore', 'addetto_sala', 'addetto_cucina');
CREATE TABLE utente(
	id_utente SERIAL PRIMARY KEY,
	nome VARCHAR(30) NOT NULL,
	cognome VARCHAR(30) NOT NULL,
	data_nascita DATE NOT NULL,
	email VARCHAR(50) NOT NULL,
	password VARCHAR(30) NOT NULL,
	ruolo ROLE NOT NULL,
	isFirstAccess BOOLEAN NOT NULL,
	aggiunto_da VARCHAR(50),
	data_aggiunta DATE,
	id_ristorante INTEGER NOT NULL,
	FOREIGN KEY(id_ristorante) REFERENCES ristorante(id_ristorante)
);

CREATE TABLE avviso(
	id_avviso SERIAL PRIMARY KEY,
	id_utente INTEGER NOT NULL,
	id_ristorante INTEGER NOT NULL,
	testo VARCHAR(200) NOT NULL,
	data_ora TIMESTAMP NOT NULL,
	FOREIGN KEY(id_utente) REFERENCES utente(id_utente),
	FOREIGN KEY(id_ristorante) REFERENCES ristorante(id_ristorante)
);

CREATE TABLE cronologia_lettura_avviso(
	id_utente INTEGER NOT NULL,
	id_avviso INTEGER NOT NULL,
	FOREIGN KEY(id_avviso) REFERENCES avviso(id_avviso),
	FOREIGN KEY(id_utente) REFERENCES utente(id_utente)
);

CREATE TABLE cronologia_nascosti_avviso(
	id_utente INTEGER NOT NULL,
	id_avviso INTEGER NOT NULL,
	FOREIGN KEY(id_avviso) REFERENCES avviso(id_avviso),
	FOREIGN KEY(id_utente) REFERENCES utente(id_utente)
);


CREATE TYPE categoria_p AS ENUM ('frutta', 'verdura', 'carne', 'pesce', 'uova', 'latte_e_derivati', 'cereali_e_derivati', 'legumi', 'altro');

CREATE TYPE unita_di_misura AS ENUM ('kg', 'litri');


CREATE TABLE prodotto(
	id_prodotto SERIAL PRIMARY KEY,
	id_ristorante INTEGER NOT NULL,
	nome VARCHAR(30) NOT NULL,
	stato INTEGER NOT NULL,
	descrizione VARCHAR(200) NOT NULL,
	prezzo REAL NOT NULL,
	quantita REAL NOT NULL,
	unita_misura UNITA_DI_MISURA,
	categoria_prodotto CATEGORIA_P NOT NULL,
	FOREIGN KEY(id_ristorante) REFERENCES ristorante(id_ristorante)
);

CREATE TABLE categorie_menu(
	id_categoria SERIAL PRIMARY KEY,
	id_ristorante INTEGER NOT NULL,
	nome VARCHAR(30) NOT NULL,
	FOREIGN KEY(id_ristorante) REFERENCES ristorante(id_ristorante)
);

CREATE TABLE elementi_menu(
	id_elemento SERIAL PRIMARY KEY,
	id_ristorante INTEGER NOT NULL,
	id_categoria INTEGER NOT NULL,
	nome VARCHAR(30) NOT NULL,
	prezzo REAL NOT NULL,
	descrizione VARCHAR(200) NOT NULL,
	allergeni VARCHAR(200) NOT NULL,
	nome_seconda_lingua VARCHAR(30) NOT NULL,
	descrizione_seconda_lingua VARCHAR(200) NOT NULL,
	FOREIGN KEY(id_ristorante) REFERENCES ristorante(id_ristorante),
	FOREIGN KEY(id_categoria) REFERENCES categorie_menu(id_categoria)
);