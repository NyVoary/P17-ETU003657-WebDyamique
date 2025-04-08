INSERT INTO labels (name) VALUES
('Food'),
('Transport'),
('Entertainment'),
('Health'),
('Education');

INSERT INTO movements (label_id, amount) VALUES
(1, 150.00),
(2, 50.00),
(3, 200.00),
(4, 100.00),
(5, 300.00);

INSERT INTO budget (label_id, amount) VALUES
(1, 500.00),
(2, 200.00),
(3, 300.00),
(4, 400.00),
(5, 600.00);

INSERT INTO user (username, password) VALUES
('user', 1234);