INSERT INTO purchases (purchase_date, total_amount, customer_name, customer_email, status)
VALUES 
  ('2025-06-12 10:00:00', 39.99, 'Laura Pérez', 'laura.perez@email.com', 'COMPLETED'),
  ('2025-06-11 15:30:00', 24.50, 'Carlos Ruiz', 'carlos.ruiz@email.com', 'PENDING'),
  ('2025-06-10 09:15:00', 15.00, 'Ana Gómez', 'ana.gomez@email.com', 'CANCELLED');

INSERT INTO purchase_books (purchase_id, book)
VALUES 
  (1, 1),
  (1, 2),
  (2, 3),
  (3, 1),
  (3, 2);