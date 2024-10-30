-- Member  table
INSERT  INTO member (id, pw, role, name, created_at) VALUES ('admin@unison.co.kr', '$2a$10$kwsmnExaw.30g8nMJROov.naBQVmkeaccvWmVdGM76X5bV9mPGyJ2', 'ROLE_ADMIN', 'Administrator', GETDATE());
INSERT  INTO member (id, pw, role, name, created_at) VALUES ('admin2@unison.co.kr', '$2a$10$kwsmnExaw.30g8nMJROov.naBQVmkeaccvWmVdGM76X5bV9mPGyJ2', 'ROLE_USER', 'Administrator', GETDATE());

-- General Overview table
INSERT INTO general_overview (uuid, site_name, last_sync_date, created_at, created_by) VALUES ('8a80252a-1ee6-4aa3-99da-519fa19fae46', 'u151', '2024-05-01 00:00:00', GETDATE(), GETDATE());
INSERT INTO general_overview (uuid, site_name, last_sync_date, created_at, created_by) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 'u113', '2024-05-01 00:00:00', GETDATE(), GETDATE());
INSERT INTO general_overview (uuid, site_name, last_sync_date, created_at, created_by) VALUES ('e9884c5a-5a55-411b-bbfd-9b9fab8347a2', 'u120', '2024-05-01 00:00:00', GETDATE(), GETDATE());

-- Power Curve table
