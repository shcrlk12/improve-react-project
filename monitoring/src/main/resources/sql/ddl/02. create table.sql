USE [Monitoring]
GO
/****** Object:  Table [dbo].[alarm]    Script Date: 2024-11-25 오후 2:50:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[alarm](
	[timestamp] [datetime2](6) NOT NULL,
	[turbine_uuid] [uniqueidentifier] NOT NULL,
	[alarm_number] [varchar](255) NOT NULL,
	[alarm_code] [varchar](255) NOT NULL,
	[comment] [varchar](255) NULL,
	[remark] [varchar](255) NULL,
PRIMARY KEY CLUSTERED
(
	[timestamp] ASC,
	[turbine_uuid] ASC,
	[alarm_number] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[archived_data]    Script Date: 2024-11-25 오후 2:50:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[archived_data](
	[general_overview_uuid] [uniqueidentifier] NOT NULL,
	[full_performance] [int] NOT NULL,
	[partial_performance] [int] NOT NULL,
	[technical_standby] [int] NOT NULL,
	[out_of_environment] [int] NOT NULL,
	[out_of_electrical] [int] NOT NULL,
	[requested_shutdown] [int] NOT NULL,
	[scheduled_maintenance] [int] NOT NULL,
	[active_power] [float] NOT NULL,
	[created_at] [datetime2](6) NOT NULL,
	[created_by] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED
(
	[general_overview_uuid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[data]    Script Date: 2024-11-25 오후 2:50:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[data](
	[full_performance] [int] NULL,
	[partial_performance] [int] NULL,
	[technical_standby] [int] NULL,
	[out_of_environment] [int] NULL,
	[out_of_electrical] [int] NULL,
	[requested_shutdown] [int] NULL,
	[scheduled_maintenance] [int] NULL,
	[wind_speed] [float] NULL,
	[active_power] [float] NULL,
	[rotor_speed] [float] NULL,
	[nac_out_tmp] [float] NULL,
	[created_at] [datetime2](6) NOT NULL,
	[timestamp] [datetime2](6) NOT NULL,
	[general_overview_uuid] [uniqueidentifier] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[timestamp] ASC,
	[general_overview_uuid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[general_overview]    Script Date: 2024-11-25 오후 2:50:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[general_overview](
	[uuid] [uniqueidentifier] NOT NULL,
	[site_name] [varchar](255) NOT NULL,
	[rated_power] [int] NOT NULL,
	[description] [varchar](255) NOT NULL,
	[remark] [varchar](255) NOT NULL,
	[altitude] [int] NOT NULL,
	[hub_height] [int] NOT NULL,
	[commission_date] [datetime2](6) NOT NULL,
	[last_alarm_sync_date] [datetime2](6) NOT NULL,
	[last_data_sync_date] [datetime2](6) NOT NULL,
	[is_active] [bit] NOT NULL,
	[is_delete] [bit] NOT NULL,
	[created_at] [datetime2](6) NOT NULL,
	[created_by] [varchar](255) NOT NULL,
	[updated_at] [datetime2](6) NULL,
	[updated_by] [varchar](255) NULL,
PRIMARY KEY CLUSTERED
(
	[uuid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[member]    Script Date: 2024-11-25 오후 2:50:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[member](
	[id] [varchar](255) NOT NULL,
	[name] [varchar](30) NOT NULL,
	[role] [varchar](255) NOT NULL,
	[pw] [varchar](64) NOT NULL,
	[is_active] [bit] NOT NULL,
	[is_delete] [bit] NOT NULL,
	[last_login_time] [datetime2](6) NULL,
	[created_at] [datetime2](6) NOT NULL,
	[created_by] [varchar](255) NOT NULL,
	[updated_at] [datetime2](6) NULL,
	[updated_by] [varchar](255) NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[power_curve]    Script Date: 2024-11-25 오후 2:50:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[power_curve](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[wind_speed] [float] NOT NULL,
	[active_power] [float] NOT NULL,
	[general_overview_uuid] [uniqueidentifier] NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[remark_data]    Script Date: 2024-11-25 오후 2:50:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[remark_data](
	[uuid] [uniqueidentifier] NOT NULL,
	[description] [varchar](255) NOT NULL,
	[timestamp] [datetime2](6) NOT NULL,
	[remark_meta_uuid] [uniqueidentifier] NULL,
	[general_overview_uuid] [uniqueidentifier] NULL,
	[created_at] [datetime2](6) NOT NULL,
	[created_by] [varchar](255) NOT NULL,
	[updated_at] [datetime2](6) NULL,
	[updated_by] [varchar](255) NULL,
PRIMARY KEY CLUSTERED
(
	[uuid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[remark_meta]    Script Date: 2024-11-25 오후 2:50:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[remark_meta](
	[uuid] [uniqueidentifier] NOT NULL,
	[order_id] [int] NOT NULL,
	[title] [varchar](255) NOT NULL,
	[default_description] [varchar](255) NOT NULL,
	[general_overview_uuid] [uniqueidentifier] NOT NULL,
	[created_at] [datetime2](6) NOT NULL,
	[created_by] [varchar](255) NOT NULL,
	[updated_at] [datetime2](6) NULL,
	[updated_by] [varchar](255) NULL,
PRIMARY KEY CLUSTERED
(
	[uuid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[archived_data] ADD  DEFAULT ('System') FOR [created_by]
GO
ALTER TABLE [dbo].[general_overview] ADD  DEFAULT ((1)) FOR [is_active]
GO
ALTER TABLE [dbo].[general_overview] ADD  DEFAULT ((0)) FOR [is_delete]
GO
ALTER TABLE [dbo].[general_overview] ADD  DEFAULT ('System') FOR [created_by]
GO
ALTER TABLE [dbo].[member] ADD  DEFAULT ((1)) FOR [is_active]
GO
ALTER TABLE [dbo].[member] ADD  DEFAULT ((0)) FOR [is_delete]
GO
ALTER TABLE [dbo].[member] ADD  DEFAULT ('System') FOR [created_by]
GO
ALTER TABLE [dbo].[remark_meta] ADD  DEFAULT ('System') FOR [created_by]
GO
ALTER TABLE [dbo].[archived_data]  WITH CHECK ADD  CONSTRAINT [FKdstdj9246c91pkwountvujjbl] FOREIGN KEY([general_overview_uuid])
REFERENCES [dbo].[general_overview] ([uuid])
GO
ALTER TABLE [dbo].[archived_data] CHECK CONSTRAINT [FKdstdj9246c91pkwountvujjbl]
GO
ALTER TABLE [dbo].[data]  WITH CHECK ADD  CONSTRAINT [FK8aq6gjt5q9l8cinp1fmktema2] FOREIGN KEY([general_overview_uuid])
REFERENCES [dbo].[general_overview] ([uuid])
GO
ALTER TABLE [dbo].[data] CHECK CONSTRAINT [FK8aq6gjt5q9l8cinp1fmktema2]
GO
ALTER TABLE [dbo].[power_curve]  WITH CHECK ADD  CONSTRAINT [FKonq4cdhlslv9ci5e6sf3m3cj] FOREIGN KEY([general_overview_uuid])
REFERENCES [dbo].[general_overview] ([uuid])
GO
ALTER TABLE [dbo].[power_curve] CHECK CONSTRAINT [FKonq4cdhlslv9ci5e6sf3m3cj]
GO
ALTER TABLE [dbo].[remark_data]  WITH CHECK ADD  CONSTRAINT [FKixvk009eo0g5phkb1yapw7l4k] FOREIGN KEY([remark_meta_uuid])
REFERENCES [dbo].[remark_meta] ([uuid])
GO
ALTER TABLE [dbo].[remark_data] CHECK CONSTRAINT [FKixvk009eo0g5phkb1yapw7l4k]
GO
ALTER TABLE [dbo].[remark_data]  WITH CHECK ADD  CONSTRAINT [FKrprj7nlql35yhhmgip4arpyrb] FOREIGN KEY([general_overview_uuid])
REFERENCES [dbo].[general_overview] ([uuid])
GO
ALTER TABLE [dbo].[remark_data] CHECK CONSTRAINT [FKrprj7nlql35yhhmgip4arpyrb]
GO
ALTER TABLE [dbo].[remark_meta]  WITH CHECK ADD  CONSTRAINT [FKgio5s1wf1mluh5wvm2k6tmivw] FOREIGN KEY([general_overview_uuid])
REFERENCES [dbo].[general_overview] ([uuid])
GO
ALTER TABLE [dbo].[remark_meta] CHECK CONSTRAINT [FKgio5s1wf1mluh5wvm2k6tmivw]
GO
