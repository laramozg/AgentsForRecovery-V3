insert into application.users (username,
                               password,
                               nick,
                               telegram,
                               confirmed,
                               confirmed_username,
                               blocked,
                               role)
VALUES ('supervisor', '$2a$10$J4KjRrjR1SzK0aSB9ugSFOFuyUNZzWv6cIIjeSW8CT1TmlV15blZO', 'supervisor', '@supervisor', true, true, false, 'SUPERVISOR'),
       ('executor1', '$2a$10$92WKWFbkcadKpFHsLKEscexNZd17x.iLzUUtuBaPK9uoYUs/AllfK', 'executor', '@executor1', true, true, false, 'EXECUTOR'),
       ('customer1', '$2a$10$tEUzUTVex4BF6ZfB99GI4.TWankH01Ziovn3AcKQ7K67bqhh2AYfy', 'customer1', '@customer1', true, true, false, 'CUSTOMER');