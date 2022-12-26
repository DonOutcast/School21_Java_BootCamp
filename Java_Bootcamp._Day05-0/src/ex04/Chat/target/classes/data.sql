INSERT INTO chat.user(name, password) VALUES
    ('Kate','123'), ('Jack','123'), ('Wer','123'), ('Lory','123'), ('Bill','123'), ('Nick','123'),
    ('User7', '123'), ('User8', '123'), ('User9', '123'), ('User10', '123'), ('User11', '123'), ('User12', '123'),
    ('User13', '123'), ('User14', '123'), ('User15', '123')
ON CONFLICT DO NOTHING;

INSERT INTO chat.chatroom(title, owner) VALUES
    ('Chat1', (SELECT id FROM chat.user WHERE name = 'Kate')),
    ('Chat2', (SELECT id FROM chat.user WHERE name = 'Jack')),
    ('Chat3', (SELECT id FROM chat.user WHERE name = 'Wer')),
    ('Chat4', (SELECT id FROM chat.user WHERE name = 'Lory')),
    ('Chat5', (SELECT id FROM chat.user WHERE name = 'Bill')),
    ('Chat6', (SELECT id FROM chat.user WHERE name = 'Nick')),
    ('Chat7', (SELECT id FROM chat.user WHERE name = 'Kate')),
    ('Chat8', (SELECT id FROM chat.user WHERE name = 'Jack')),
    ('Chat9', (SELECT id FROM chat.user WHERE name = 'Wer')),
    ('Chat10', (SELECT id FROM chat.user WHERE name = 'Lory')),
    ('Chat11', (SELECT id FROM chat.user WHERE name = 'Kate')),
    ('Chat12', (SELECT id FROM chat.user WHERE name = 'Jack'))
ON CONFLICT DO NOTHING;

INSERT INTO chat.message (author, room, text) VALUES
    ((SELECT id FROM chat.user WHERE name = 'Kate'), (SELECT id FROM chat.chatroom WHERE title = 'Chat1'), 'Msg1'),
    ((SELECT id FROM chat.user WHERE name = 'Jack'), (SELECT id FROM chat.chatroom WHERE title = 'Chat2'), 'Msg2'),
    ((SELECT id FROM chat.user WHERE name = 'Wer'), (SELECT id FROM chat.chatroom WHERE title = 'Chat3'), 'Msg3'),
    ((SELECT id FROM chat.user WHERE name = 'Lory'), (SELECT id FROM chat.chatroom WHERE title = 'Chat4'), 'Msg4'),
    ((SELECT id FROM chat.user WHERE name = 'Bill'), (SELECT id FROM chat.chatroom WHERE title = 'Chat5'), 'Msg5'),
    ((SELECT id FROM chat.user WHERE name = 'Nick'), (SELECT id FROM chat.chatroom WHERE title = 'Chat6'), 'Msg6')
ON CONFLICT DO NOTHING;

INSERT INTO chat.user_chatroom (user_id, chat_id) VALUES
    ((SELECT id FROM chat.user WHERE name = 'Kate'), (SELECT id FROM chat.chatroom WHERE title = 'Chat1')),
    ((SELECT id FROM chat.user WHERE name = 'Kate'), (SELECT id FROM chat.chatroom WHERE title = 'Chat7')),
    ((SELECT id FROM chat.user WHERE name = 'Kate'), (SELECT id FROM chat.chatroom WHERE title = 'Chat11')),
    ((SELECT id FROM chat.user WHERE name = 'Kate'), (SELECT id FROM chat.chatroom WHERE title = 'Chat12')),
    ((SELECT id FROM chat.user WHERE name = 'Kate'), (SELECT id FROM chat.chatroom WHERE title = 'Chat3')),
    ((SELECT id FROM chat.user WHERE name = 'Jack'), (SELECT id FROM chat.chatroom WHERE title = 'Chat2')),
    ((SELECT id FROM chat.user WHERE name = 'Jack'), (SELECT id FROM chat.chatroom WHERE title = 'Chat8')),
    ((SELECT id FROM chat.user WHERE name = 'Jack'), (SELECT id FROM chat.chatroom WHERE title = 'Chat12')),
    ((SELECT id FROM chat.user WHERE name = 'Jack'), (SELECT id FROM chat.chatroom WHERE title = 'Chat3')),
    ((SELECT id FROM chat.user WHERE name = 'Jack'), (SELECT id FROM chat.chatroom WHERE title = 'Chat4')),
    ((SELECT id FROM chat.user WHERE name = 'Wer'), (SELECT id FROM chat.chatroom WHERE title = 'Chat3')),
    ((SELECT id FROM chat.user WHERE name = 'Wer'), (SELECT id FROM chat.chatroom WHERE title = 'Chat9')),
    ((SELECT id FROM chat.user WHERE name = 'Wer'), (SELECT id FROM chat.chatroom WHERE title = 'Chat10')),
    ((SELECT id FROM chat.user WHERE name = 'Lory'), (SELECT id FROM chat.chatroom WHERE title = 'Chat4')),
    ((SELECT id FROM chat.user WHERE name = 'Lory'), (SELECT id FROM chat.chatroom WHERE title = 'Chat10')),
    ((SELECT id FROM chat.user WHERE name = 'Lory'), (SELECT id FROM chat.chatroom WHERE title = 'Chat11')),
    ((SELECT id FROM chat.user WHERE name = 'Bill'), (SELECT id FROM chat.chatroom WHERE title = 'Chat5')),
    ((SELECT id FROM chat.user WHERE name = 'Bill'), (SELECT id FROM chat.chatroom WHERE title = 'Chat6')),
    ((SELECT id FROM chat.user WHERE name = 'Nick'), (SELECT id FROM chat.chatroom WHERE title = 'Chat6')),
    ((SELECT id FROM chat.user WHERE name = 'Nick'), (SELECT id FROM chat.chatroom WHERE title = 'Chat7')),
    (11, 1), (11, 2), (11, 3),
    (12, 2), (12, 3), (12, 4),
    (13, 3), (13, 4), (13, 5), (13, 6),
    (14, 4), (14, 5), (14, 6), (14, 7),
    (15, 5), (15, 6), (15, 7), (15, 8)
ON CONFLICT DO NOTHING;
