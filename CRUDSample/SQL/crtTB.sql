USE [devdb]
GO

/****** Object:  Table [dbo].[user]    Script Date: 2016/11/15 16:01:53 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[user](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[pwd] [nchar](10) NOT NULL,
	[real] [nchar](10) NOT NULL,
	[dep] [nchar](10) NOT NULL,
	[role] [nchar](10) NOT NULL
) ON [PRIMARY]

GO

