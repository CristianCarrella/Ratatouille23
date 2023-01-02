CREATE TABLE ristorante(
	id_ristorante SERIAL PRIMARY KEY,
	nome VARCHAR(30) NOT NULL,
	telefono VARCHAR(30),
	indirizzo VARCHAR(30),
	logo BYTEA,
	nome_immagine VARCHAR(30),
	id_proprietario INTEGER NOT NULL,
	FOREIGN KEY(id_proprietario) REFERENCES utente(id)
);

CREATE TYPE role AS ENUM ('amministratore', 'supervisore', 'addetto_sala', 'addetto_cucina');

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
	FOREIGN KEY(id_ristorante) REFERENCES ristorante(id)
);


CREATE TABLE avvisi(
	id_avvisi SERIAL PRIMARY KEY,
	id_utente INTEGER NOT NULL,
	id_ristorante INTEGER NOT NULL,
	testo VARCHAR(200) NOT NULL,
	data_ora TIMESTAMP NOT NULL,
	FOREIGN KEY(id_utente) REFERENCES utente(id),
	FOREIGN KEY(id_ristorante) REFERENCES ristorante(id)
);

CREATE TABLE cronologia_lettura_avvisi(
	id_utente INTEGER NOT NULL,
	id_avvisi INTEGER NOT NULL,
	FOREIGN KEY(id_avvisi) REFERENCES avvisi(id),
	FOREIGN KEY(id_utente) REFERENCES utente(id)
);

CREATE TABLE cronologia_nascosti_avvisi(
	id_utente INTEGER NOT NULL,
	id_avvisi INTEGER NOT NULL,
	FOREIGN KEY(id_avvisi) REFERENCES avvisi(id),
	FOREIGN KEY(id_utente) REFERENCES utente(id)
);


CREATE TYPE categoria_p AS ENUM ('frutta', 'verdura', 'carne', 'pesce', 'uova', 'latte_e_derivati', 'cereali_e_derivati', 'legumi', 'condimento', 'altro');

CREATE TYPE unita_di_misura AS ENUM ('kg', 'litri');


CREATE TABLE prodotti(
	id_prodotti SERIAL PRIMARY KEY,
	id_ristorante INTEGER NOT NULL,
	nome VARCHAR(30) NOT NULL,
	stato INTEGER NOT NULL,
	descrizione VARCHAR(200) NOT NULL,
	prezzo REAL NOT NULL,
	quantita REAL NOT NULL,
	unita_misura UNITA_DI_MISURA,
	categoria_prodotti CATEGORIA_P NOT NULL,
	FOREIGN KEY(id_ristorante) REFERENCES ristorante(id),
);

CREATE TABLE categorie_menu(
	id_categorie SERIAL PRIMARY KEY,
	id_ristorante INTEGER NOT NULL,
	nome VARCHAR(30) NOT NULL,
	FOREIGN KEY(id_ristorante) REFERENCES ristorante(id),
);

CREATE TABLE elementi_menu(
	id_elementi SERIAL PRIMARY KEY,
	id_ristorante INTEGER NOT NULL,
	id_categoria INTEGER NOT NULL,
	nome VARCHAR(30) NOT NULL,
	prezzo REAL NOT NULL,
	descrizione VARCHAR(200) NOT NULL,
	allergeni VARCHAR(200) NOT NULL,
	nome_seconda_lingua VARCHAR(30) NOT NULL,
	descrizione_seconda_lingua VARCHAR(200) NOT NULL,
	FOREIGN KEY(id_ristorante) REFERENCES ristorante(id),
	FOREIGN KEY(id_categoria) REFERENCES categorie_menu(id)
);