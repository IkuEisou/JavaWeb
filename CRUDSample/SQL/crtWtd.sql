USE [devdb]
GO

/****** Object:  Table [dbo].[wtd]    Script Date: 2016/12/4 0:16:08 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[wtd](
	[dh] [nchar](30) NOT NULL,
	[time] [datetime] NOT NULL,
	[dw] [nchar](20) NOT NULL,
	[wh] [nchar](30) NOT NULL,
	[jy] [nchar](10) NULL,
	[id] [int] IDENTITY(1,1) NOT NULL,
	[fee] [float] NOT NULL
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[wtd] ADD  CONSTRAINT [DF_wtd_time]  DEFAULT (getdate()) FOR [time]
GO

