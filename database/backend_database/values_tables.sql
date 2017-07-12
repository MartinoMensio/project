INSERT INTO education_levels (id, value) values
    (1, 'LICENZA ELEMENTARE'),
    (2, 'LICENZA MEDIA'),
    (3, 'DIPLOMA DI ISTRUZIONE SECONDARIA SUPERIORE'),
    (4, 'LAUREA DI PRIMO LIVELLO'),
    (5, 'LAUREA SPECIALISTICA'),
    (6, 'MASTER UNIVERSITARIO DI PRIMO LIVELLO');
INSERT INTO employments (id, value) values
    (1, 'STUDENTE'),
    (2, 'DISOCCUPATO'),
    (3, 'OCCUPATO AUTONOMO'),
    (4, 'OCCUPATO PERMANENTE'),
    (5, 'OCCUPATO TEMPORANEO'),
    (6, 'PENSIONATO');
INSERT INTO fuels (id, value) values
    (1, 'BENZINA'),
    (2, 'DIESEL'),
    (3, 'GPL'),
    (4, 'IBRIDA'),
    (5, 'METANO'),
    (6, 'AUTO ELETTRICA');
INSERT INTO car_sharing_services (id, name) values
    (1, 'ENJOY'),
    (2, 'CAR2GO'),
    (3, 'BLUETORINO');
INSERT INTO travel_documents (id, name, cost) values
    (1, 'GIORNALIERO', 5.00),
    (2, 'CORSA SINGOLA', 1.50),
    (3, 'CARNET 5', 6.50),
    (4, 'CARNET 15', 17.50);
INSERT INTO status (id, value) values
    (1, 'INCOMPLETE'),
    (2, 'COMPLETE'),
    (3, 'BANNED'), -- not used
    (4, 'NOT_VERIFIED');
