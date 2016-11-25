USE [devdb]
GO

/****** Object:  Table [dbo].[aqf]    Script Date: 2016/11/25 13:39:21 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[aqf](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[xh] [nchar](30) NOT NULL,
	[tj] [int] NOT NULL,
	[gzjz] [nchar](10) NOT NULL,
	[gzyl] [float] NOT NULL,
	[zdyl] [float] NOT NULL,
	[fsdm] [nchar](30) NOT NULL,
	[azwz] [nchar](10) NOT NULL,
	[wh] [nchar](30) NOT NULL,
	[name] [nchar](10) NOT NULL,
	[dw] [nchar](30) NOT NULL,
	[zt] [nchar](30) NOT NULL
) ON [PRIMARY]

GO

