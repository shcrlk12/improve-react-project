CREATE DATABASE Monitoring
ON
(
    NAME = N'Monitoring_Data',
    FILENAME = N'D:\\MonitoringSystem\\database\\monitoring.mdf',
    SIZE = 102400KB,          -- 초기 파일 크기
    MAXSIZE = 2048576KB,      -- 최대 파일 크기
    FILEGROWTH  = 102400KB    -- 파일 증가 크기
)
LOG ON
(
    NAME = N'Monitoring_Log',
    FILENAME = N'D:\\MonitoringSystem\\database\\monitoring.ldf',
    SIZE = 20480KB,
    MAXSIZE = 512000KB,
    FILEGROWTH  = 51200KB
)
GO