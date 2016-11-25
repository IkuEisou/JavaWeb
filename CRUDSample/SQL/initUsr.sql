USE [devdb]
GO

INSERT INTO [dbo].[user]
           ([name]
           ,[pwd]
           ,[real]
           ,[dep]
           ,[role])
     VALUES
           (<name, nvarchar(50),>
           ,<pwd, nchar(10),>
           ,<real, nchar(10),>
           ,<dep, nchar(10),>
           ,<role, nchar(10),>)
GO

