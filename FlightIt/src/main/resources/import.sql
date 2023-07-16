/* INSERT AEREOPORTO */
insert into aereoporto (id, nome) values(1, 'Ciampino');
insert into aereoporto (id, nome) values(2, 'Fiumicino');
insert into aereoporto (id, nome) values(3, 'Malpensa');

/* INSER VOLO */
insert into volo (id, codice_volo, data_partenza, ora_arrivo, ora_partenza, aereoporto_arrivo_id, aereoporto_partenza_id) values(1, 'RziMLdyfKt', '2023-07-04', '06:20:00', '04:15:00', 2, 3);

insert into volo (id, codice_volo, data_partenza, ora_arrivo, ora_partenza, aereoporto_arrivo_id, aereoporto_partenza_id) values(2, '2HstcHvHPQ', '2023-07-18', '09:15:00', '08:15:00', 3, 2);

/* INSERT UTENTE - CREDENTIALS */
insert into utente (id, nome) values(1, 'ManuelMauri');
insert into credentials (id, auth_provider, ruolo, username, utente_id) values(1, 'GITHUB', 'ADMIN', 'ManuelMauri1', 1);

insert into utente values(2, 'Scacchi', '2023-07-04', 'Gianfranco');
insert into credentials values(2, '$2a$10$BNI0mlzc6V7xC2.SR9j8eew2RAjAKlRlxMsUc3OvNExts.3a4G8MC', 'LOCAL', 'ADMIN', 'F', 2);

insert into volo_preferiti_utente values(1, 2);
