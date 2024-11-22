-- Member  table
INSERT  INTO member (id, pw, role, name, created_at) VALUES ('admin@unison.co.kr', '$2a$10$kwsmnExaw.30g8nMJROov.naBQVmkeaccvWmVdGM76X5bV9mPGyJ2', 'ROLE_ADMIN', 'Administrator', GETDATE());
INSERT  INTO member (id, pw, role, name, created_at) VALUES ('admin2@unison.co.kr', '$2a$10$kwsmnExaw.30g8nMJROov.naBQVmkeaccvWmVdGM76X5bV9mPGyJ2', 'ROLE_USER', '김정원', GETDATE());

-- General Overview table
INSERT INTO general_overview (uuid, site_name, rated_power, description, remark, altitude, hub_height, commission_date, last_data_sync_date, last_alarm_sync_date, created_at, created_by) VALUES ('8a80252a-1ee6-4aa3-99da-519fa19fae46', 'u151', 4300, '', '테스트다1', 0, 100, '2019-09-03 00:00:00', '2024-11-01 00:00:00', '2024-11-02 00:00:00', GETDATE(), 'System');
INSERT INTO general_overview (uuid, site_name, rated_power, description, remark, altitude, hub_height, commission_date, last_data_sync_date, last_alarm_sync_date, created_at, created_by) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 'u113', 2300, '', '테스트다2', 0, 100, '2020-11-23 00:00:00', '2024-11-01 00:00:00', '2024-11-02 00:00:00', GETDATE(), 'System');
INSERT INTO general_overview (uuid, site_name, rated_power, description, remark, altitude, hub_height, commission_date, last_data_sync_date, last_alarm_sync_date, created_at, created_by) VALUES ('e9884c5a-5a55-411b-bbfd-9b9fab8347a2', 'u120', 2500, '', '', 0, 100, '2020-11-23 00:00:00', '2022-11-01 00:00:00', '2024-11-02 00:00:00', GETDATE(), 'System');

-- Archived Data table
INSERT INTO archived_data (general_overview_uuid, full_performance, partial_performance, out_of_electrical, out_of_environment, requested_shutdown, scheduled_maintenance, technical_standby, active_power, created_at, created_by) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 0, 0, 0, 0, 0, 0, 0, 0, GETDATE(), 'System');
INSERT INTO archived_data (general_overview_uuid, full_performance, partial_performance, out_of_electrical, out_of_environment, requested_shutdown, scheduled_maintenance, technical_standby, active_power, created_at, created_by) VALUES ('8a80252a-1ee6-4aa3-99da-519fa19fae46', 87604478, 3807188, 183316, 39395912, 8225040, 1233796, 2306810, 236780719, GETDATE(), 'System');
INSERT INTO archived_data (general_overview_uuid, full_performance, partial_performance, out_of_electrical, out_of_environment, requested_shutdown, scheduled_maintenance, technical_standby, active_power, created_at, created_by) VALUES ('e9884c5a-5a55-411b-bbfd-9b9fab8347a2', 0, 0, 0, 0, 0, 0, 0, 0, GETDATE(), 'System');

-- Power Curve table
-- U113
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 0, 0);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 2.0, -2.33);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 2.5, -1.53);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 3, 14.06);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 3.5, 40.85);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 4.0, 95.73);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 4.5, 194.45);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 5.0, 284.93);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 5.5, 396.18);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 6.0, 537.31);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 6.5, 711.12);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 7.0, 893.97);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 7.5, 1105.88);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 8.0, 1317.4);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 8.5, 1526.93);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 9.0, 1765.19);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 9.5, 1990.75);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 10.0, 2163.14);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 10.5, 2240.58);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 11.0, 2285.26);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 11.5, 2306.8);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 12.0, 2311.75);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 12.5, 2316.16);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 13.0, 2316.65);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 13.5, 2317.32);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 14.0, 2319.08);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 14.5, 2317.7);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 15.0, 2318.39);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 15.5, 2318.05);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 16.0, 2318.3);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 16.5, 2318.05);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 17.0, 2318.64);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 17.5, 2318.34);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 18.0, 2318.91);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 18.5, 2319.68);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 19.0, 2321.38);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('b016e89f-b126-4b47-8ea1-4bc42ded44dc', 19.5, 2323.52);


-- U151
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('8a80252a-1ee6-4aa3-99da-519fa19fae46', 3.0, 60.2118);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('8a80252a-1ee6-4aa3-99da-519fa19fae46', 3.5, 141.971);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('8a80252a-1ee6-4aa3-99da-519fa19fae46', 4.0, 245.051);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('8a80252a-1ee6-4aa3-99da-519fa19fae46', 4.5, 366.097);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('8a80252a-1ee6-4aa3-99da-519fa19fae46', 5.0, 522.061);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('8a80252a-1ee6-4aa3-99da-519fa19fae46', 5.5, 718.677);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('8a80252a-1ee6-4aa3-99da-519fa19fae46', 6.0, 958.851);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('8a80252a-1ee6-4aa3-99da-519fa19fae46', 6.5, 1246.71);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('8a80252a-1ee6-4aa3-99da-519fa19fae46', 7.0, 1590.22);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('8a80252a-1ee6-4aa3-99da-519fa19fae46', 7.5, 1981.7);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('8a80252a-1ee6-4aa3-99da-519fa19fae46', 8.0, 2432.72);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('8a80252a-1ee6-4aa3-99da-519fa19fae46', 8.5, 2887.12);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('8a80252a-1ee6-4aa3-99da-519fa19fae46', 9.0, 3355.21);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('8a80252a-1ee6-4aa3-99da-519fa19fae46', 9.5, 3828.27);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('8a80252a-1ee6-4aa3-99da-519fa19fae46', 10.0, 4300.0);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('8a80252a-1ee6-4aa3-99da-519fa19fae46', 20.0, 4300.0);

--U120
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('e9884c5a-5a55-411b-bbfd-9b9fab8347a2', 3.0, 45.5157);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('e9884c5a-5a55-411b-bbfd-9b9fab8347a2', 3.5, 89.4159);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('e9884c5a-5a55-411b-bbfd-9b9fab8347a2', 4.0, 145.73);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('e9884c5a-5a55-411b-bbfd-9b9fab8347a2', 4.5, 220.842);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('e9884c5a-5a55-411b-bbfd-9b9fab8347a2', 5.0, 321.633);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('e9884c5a-5a55-411b-bbfd-9b9fab8347a2', 5.5, 443.314);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('e9884c5a-5a55-411b-bbfd-9b9fab8347a2', 6.0, 592.306);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('e9884c5a-5a55-411b-bbfd-9b9fab8347a2', 6.5, 767.828);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('e9884c5a-5a55-411b-bbfd-9b9fab8347a2', 7.0, 969.603);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('e9884c5a-5a55-411b-bbfd-9b9fab8347a2', 7.5, 1204.11);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('e9884c5a-5a55-411b-bbfd-9b9fab8347a2', 8.0, 1469.22);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('e9884c5a-5a55-411b-bbfd-9b9fab8347a2', 8.5, 1747.0);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('e9884c5a-5a55-411b-bbfd-9b9fab8347a2', 9.0, 2033.37);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('e9884c5a-5a55-411b-bbfd-9b9fab8347a2', 9.5, 2300.01);
INSERT INTO power_curve (general_overview_uuid, wind_speed, active_power) VALUES ('e9884c5a-5a55-411b-bbfd-9b9fab8347a2', 20.0, 2300.01);

--Remark Meta Table
INSERT INTO remark_meta (id, general_overview_uuid, title, default_description, created_at) VALUES (1, 'b016e89f-b126-4b47-8ea1-4bc42ded44dc', '주요 이벤트 현황', '1) 특이사항 없음', GETDATE());
INSERT INTO remark_meta (id, general_overview_uuid, title, default_description, created_at) VALUES (2, 'b016e89f-b126-4b47-8ea1-4bc42ded44dc', '주요 에러 발생현황 및 조치사항', '1) 특이사항 없음', GETDATE());
INSERT INTO remark_meta (id, general_overview_uuid, title, default_description, created_at) VALUES (3, 'b016e89f-b126-4b47-8ea1-4bc42ded44dc', '현장이슈 발생현황', '1) 특이사항 없음', GETDATE());

INSERT INTO remark_meta (id, general_overview_uuid, title, default_description, created_at) VALUES (4, '8a80252a-1ee6-4aa3-99da-519fa19fae46', '주요 이벤트 현황', '1) 특이사항 없음', GETDATE());
INSERT INTO remark_meta (id, general_overview_uuid, title, default_description, created_at) VALUES (5, '8a80252a-1ee6-4aa3-99da-519fa19fae46', '주요 에러 발생현황 및 조치사항', '1) 특이사항 없음', GETDATE());
INSERT INTO remark_meta (id, general_overview_uuid, title, default_description, created_at) VALUES (6, '8a80252a-1ee6-4aa3-99da-519fa19fae46', '현장이슈 발생현황', '1) 특이사항 없음', GETDATE());

INSERT INTO remark_meta (id, general_overview_uuid, title, default_description, created_at) VALUES (7, 'e9884c5a-5a55-411b-bbfd-9b9fab8347a2', '주요 이벤트 현황', '1) 특이사항 없음', GETDATE());
INSERT INTO remark_meta (id, general_overview_uuid, title, default_description, created_at) VALUES (8, 'e9884c5a-5a55-411b-bbfd-9b9fab8347a2', '주요 에러 발생현황 및 조치사항', '1) 특이사항 없음', GETDATE());
INSERT INTO remark_meta (id, general_overview_uuid, title, default_description, created_at) VALUES (9, 'e9884c5a-5a55-411b-bbfd-9b9fab8347a2', '현장이슈 발생현황', '1) 특이사항 없음', GETDATE());


