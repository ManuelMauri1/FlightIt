/* INSERT AEREOPORTO */
insert into aereoporto (id, nome) values(1, 'Ciampino');
insert into aereoporto (id, nome) values(2, 'Fiumicino');
insert into aereoporto (id, nome) values(3, 'Malpensa');

/* INSER VOLO */
insert into volo (id, codice_volo, data_partenza, ora_arrivo, ora_partenza, aereoporto_arrivo_id, aereoporto_partenza_id) values(1, 'RziMLdyfKt', '2023-07-04', '06:20:00', '04:15:00', 2, 3);

insert into volo (id, codice_volo, data_partenza, ora_arrivo, ora_partenza, aereoporto_arrivo_id, aereoporto_partenza_id) values(2, '2HstcHvHPQ', '2023-07-18', '09:15:00', '08:15:00', 3, 2);
