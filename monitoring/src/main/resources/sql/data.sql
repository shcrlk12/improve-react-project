-- Member  table
INSERT  INTO member (id, pw, role, name, created_at) VALUES ('admin@unison.co.kr', '$2a$10$kwsmnExaw.30g8nMJROov.naBQVmkeaccvWmVdGM76X5bV9mPGyJ2', 'ROLE_ADMIN', 'Administrator', GETDATE());

-- General Overview table
INSERT INTO general_overview (uuid, site_name, created_at, created_by) VALUES ('325dbb35-9e49-4466-98f2-57e2145748f7', 'u151', GETDATE(), GETDATE());
INSERT INTO general_overview (uuid, site_name, created_at, created_by) VALUES ('31f8eca8-228f-424a-9d14-98563c852bcf', 'u113', GETDATE(), GETDATE());
INSERT INTO general_overview (uuid, site_name, created_at, created_by) VALUES ('2175331d-4168-4f34-a7f6-0e97ad3d523c', 'u120', GETDATE(), GETDATE());
