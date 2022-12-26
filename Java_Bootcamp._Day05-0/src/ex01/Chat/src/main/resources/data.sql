INSERT INTO chat.user(name, password) VALUES
    ('Kate','123'),
    ('Jack','123'),
    ('Wer','123'),
    ('Lory','123'),
    ('Bill','123'),
    ('Nick','123')
ON CONFLICT DO NOTHING;

INSERT INTO chat.chatroom(title, owner) VALUES
    ('Chat1', (SELECT id FROM chat.user WHERE name = 'Kate')),
    ('Chat2', (SELECT id FROM chat.user WHERE name = 'Jack')),
    ('Chat3', (SELECT id FROM chat.user WHERE name = 'Wer')),
    ('Chat4', (SELECT id FROM chat.user WHERE name = 'Lory')),
    ('Chat5', (SELECT id FROM chat.user WHERE name = 'Bill')),
    ('Chat6', (SELECT id FROM chat.user WHERE name = 'Nick'))
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
    ((SELECT id FROM chat.user WHERE name = 'Jack'), (SELECT id FROM chat.chatroom WHERE title = 'Chat2')),
    ((SELECT id FROM chat.user WHERE name = 'Wer'), (SELECT id FROM chat.chatroom WHERE title = 'Chat3')),
    ((SELECT id FROM chat.user WHERE name = 'Lory'), (SELECT id FROM chat.chatroom WHERE title = 'Chat4')),
    ((SELECT id FROM chat.user WHERE name = 'Bill'), (SELECT id FROM chat.chatroom WHERE title = 'Chat5')),
    ((SELECT id FROM chat.user WHERE name = 'Nick'), (SELECT id FROM chat.chatroom WHERE title = 'Chat6'))
ON CONFLICT DO NOTHING;
