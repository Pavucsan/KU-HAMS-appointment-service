CREATE TABLE patients (
                          id SERIAL PRIMARY KEY,
                          user_id INT NOT NULL,
                          full_name VARCHAR(255) NOT NULL,
                          date_of_birth DATE NOT NULL,
                          gender VARCHAR(50) NOT NULL,
                          phone_number VARCHAR(20),
                          email VARCHAR(255),
                          FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


CREATE TABLE doctors (
                         id SERIAL PRIMARY KEY,
                         user_id INT NOT NULL,
                         full_name VARCHAR(255) NOT NULL,
                         specialization VARCHAR(100),
                         phone_number VARCHAR(20),
                         email VARCHAR(255),
                         availability_start TIME,
                         availability_end TIME,
                         FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE appointments (
                              id SERIAL PRIMARY KEY,
                              patient_id INT NOT NULL,
                              doctor_id INT NOT NULL,
                              appointment_date DATE NOT NULL,
                              appointment_time TIME NOT NULL,
                              status VARCHAR(50) DEFAULT 'Scheduled', -- Scheduled, Rescheduled, Canceled
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
                              FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE
);


CREATE TABLE medical_records (
                                 id SERIAL PRIMARY KEY,
                                 appointment_id INT NOT NULL,
                                 diagnosis TEXT,
                                 prescribed_treatment TEXT,
                                 medical_notes TEXT,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE CASCADE
);


CREATE TABLE appointment_logs (
                                  id SERIAL PRIMARY KEY,
                                  appointment_id INT NOT NULL,
                                  action VARCHAR(50) NOT NULL,  -- Booked, Rescheduled, Canceled
                                  action_by INT NOT NULL,       -- User ID (could be patient or doctor)
                                  action_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE CASCADE,
                                  FOREIGN KEY (action_by) REFERENCES users(id) ON DELETE CASCADE
);


-- Insert Users (Doctor and Patient)
INSERT INTO users (username, password, role) VALUES ('doctor_john', 'password123', 'doctor');
INSERT INTO users (username, password, role) VALUES ('patient_alice', 'password123', 'patient');

-- Insert Doctors
INSERT INTO doctors (user_id, full_name, specialization, phone_number, email, availability_start, availability_end)
VALUES ((SELECT id FROM users WHERE username = 'doctor_john'), 'Dr. John Doe', 'Cardiology', '1234567890', 'john.doe@example.com', '09:00:00', '17:00:00');

-- Insert Patients
INSERT INTO patients (user_id, full_name, date_of_birth, gender, phone_number, email)
VALUES ((SELECT id FROM users WHERE username = 'patient_alice'), 'Alice Smith', '1990-06-15', 'Female', '9876543210', 'alice.smith@example.com');

-- Insert Appointments
INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, status)
VALUES ((SELECT id FROM patients WHERE full_name = 'Alice Smith'), (SELECT id FROM doctors WHERE full_name = 'Dr. John Doe'), '2025-04-18', '10:00:00', 'Scheduled');
