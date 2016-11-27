USE [devdb]
GO

/****** Object:  Table [dbo].[dw]    Script Date: 2016/11/27 21:47:30 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[dw](
	[name] [nchar](30) NOT NULL,
	[address] [nchar](100) NULL,
	[owner] [nchar](10) NULL
) ON [PRIMARY]

GO

