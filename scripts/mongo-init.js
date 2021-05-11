db = db.getSiblingDB('playermodule');
db.createUser(
    {
        user: 'root',
        pwd: '123',
        roles: [{ role: 'readWrite', db: 'playermodule' }],
    },
);
db.createCollection('players');

db = db.getSiblingDB('gamemodule');
db.createUser(
    {
        user: 'root',
        pwd: '123',
        roles: [{ role: 'readWrite', db: 'gamemodule' }],
    },
);
db.createCollection('sessions');