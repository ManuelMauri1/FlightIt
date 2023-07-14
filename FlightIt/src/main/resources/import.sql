/* INSERT AEREOPORTO */
insert into aereoporto (id, nome) values(1, 'Ciampino');
insert into aereoporto (id, nome) values(2, 'Fiumicino');
insert into aereoporto (id, nome) values(3, 'Malpensa');

/* INSERT UTENTE - CREDENTIALS */
insert into utente (id, nome) values(1, 'ManuelMauri');
insert into credentials (id, auth_provider, ruolo, username, utente_id) values(1, 'GITHUB', 'ADMIN', 'ManuelMauri1', 1);

insert into utente (id, nome) values(2, 'Phemt');
insert into credentials (id, auth_provider, ruolo, username, utente_id) values(2, 'GITHUB', 'AUTORIZZATO', 'Phemt00', 2);
