USE [devdb]
GO

INSERT INTO [dbo].[aqf]
           ([xh]
           ,[tj]
           ,[gzjz]
           ,[gzyl]
           ,[zdyl]
           ,[fsdm]
           ,[azwz]
           ,[wh]
           ,[name]
           ,[dw]
           ,[zt])
     VALUES
           (<xh, nchar(30),>
           ,<tj, int,>
           ,<gzjz, nchar(10),>
           ,<gzyl, float,>
           ,<zdyl, float,>
           ,<fsdm, nchar(30),>
           ,<azwz, nchar(10),>
           ,<wh, nchar(30),>
           ,<name, nchar(10),>
           ,<dw, nchar(30),>
           ,<zt, nchar(30),>)
GO

