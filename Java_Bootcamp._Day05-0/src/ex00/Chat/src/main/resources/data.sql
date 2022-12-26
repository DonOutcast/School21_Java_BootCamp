INSERT INTO chat.user(name, password) VALUES
    ('Kate','123'),
    ('Jack','123'),
    ('Wer','123'),
    ('Lory','123'),
    ('Bill','123'),
    ('Nick','123')
ON CONFLICT DO NOTHING;

INSERT INTO chat.chatroom(title, owner) VALUES
    ('1', (SELECT id FROM chat.user WHERE name = 'Kate')),
    ('2', (SELECT id FROM chat.user WHERE name = 'Jack')),
    ('3', (SELECT id FROM chat.user WHERE name = 'Wer')),
    ('4', (SELECT id FROM chat.user WHERE name = 'Lory')),
    ('5', (SELECT id FROM chat.user WHERE name = 'Bill')),
    ('6', (SELECT id FROM chat.user WHERE name = 'Nick'))
ON CONFLICT DO NOTHING;

INSERT INTO chat.message (author, room, text) VALUES
    ((SELECT id FROM chat.user WHERE name = 'Kate'), (SELECT id FROM chat.chatroom WHERE title = '1'), 'Msg1'),
    ((SELECT id FROM chat.user WHERE name = 'Jack'), (SELECT id FROM chat.chatroom WHERE title = '2'), 'Msg2'),
    ((SELECT id FROM chat.user WHERE name = 'Wer'), (SELECT id FROM chat.chatroom WHERE title = '3'), 'Msg3'),
    ((SELECT id FROM chat.user WHERE name = 'Lory'), (SELECT id FROM chat.chatroom WHERE title = '4'), 'Msg4'),
    ((SELECT id FROM chat.user WHERE name = 'Bill'), (SELECT id FROM chat.chatroom WHERE title = '5'), 'Msg5'),
    ((SELECT id FROM chat.user WHERE name = 'Nick'), (SELECT id FROM chat.chatroom WHERE title = '6'), 'Msg6')
ON CONFLICT DO NOTHING;

INSERT INTO chat.user_chatroom (user_id, chat_id) VALUES
    ((SELECT id FROM chat.user WHERE name = 'Kate'), (SELECT id FROM chat.chatroom WHERE title = '1')),
    ((SELECT id FROM chat.user WHERE name = 'Jack'), (SELECT id FROM chat.chatroom WHERE title = '2')),
    ((SELECT id FROM chat.user WHERE name = 'Wer'), (SELECT id FROM chat.chatroom WHERE title = '3')),
    ((SELECT id FROM chat.user WHERE name = 'Lory'), (SELECT id FROM chat.chatroom WHERE title = '4')),
    ((SELECT id FROM chat.user WHERE name = 'Bill'), (SELECT id FROM chat.chatroom WHERE title = '5')),
    ((SELECT id FROM chat.user WHERE name = 'Nick'), (SELECT id FROM chat.chatroom WHERE title = '6'))
ON CONFLICT DO NOTHING;
