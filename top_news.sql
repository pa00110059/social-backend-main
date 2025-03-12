-- 創建資料庫
CREATE DATABASE sm_db;
GO

-- 使用資料庫
USE sm_db;
GO

-- 停用臨時約束
SET ANSI_NULLS ON;
SET QUOTED_IDENTIFIER ON;
GO

-- ----------------------------
-- 建立 news_headline 表 (新聞標題)
-- ----------------------------
IF OBJECT_ID('news_headline', 'U') IS NOT NULL
    DROP TABLE news_headline;
GO

CREATE TABLE news_headline (
    hid INT IDENTITY(1,1) PRIMARY KEY,  -- 自動增長 ID
    title NVARCHAR(100) NOT NULL,  -- 標題
    article NVARCHAR(MAX) NOT NULL,  -- 內容
    type INT NOT NULL,  -- 新聞類型 ID
    publisher INT NOT NULL,  -- 發佈者 ID
    page_views INT NOT NULL DEFAULT 0,  -- 瀏覽量
    create_time DATETIME DEFAULT GETDATE(),  -- 創建時間
    update_time DATETIME DEFAULT GETDATE(),  -- 更新時間
    version INT DEFAULT 1,  -- 樂觀鎖
    is_deleted BIT DEFAULT 0  -- 是否刪除 (1: 已刪除, 0: 未刪除)
);
GO

-- 插入測試數據 (全球新聞)
INSERT INTO news_headline (title, article, type, publisher, page_views, create_time, update_time, is_deleted)
VALUES
(N'全球氣候變遷影響經濟發展', 
N'最新研究顯示，氣候變遷可能對全球經濟造成重大影響，特別是在農業與能源領域。',
1, 1, 500, GETDATE(), GETDATE(), 0),
(N'人工智慧技術對金融市場的影響', 
N'隨著人工智慧在金融領域的應用擴展，專家預測未來10年內交易模式將發生重大變化。',
4, 2, 450, GETDATE(), GETDATE(), 0),
(N'美國聯準會宣布最新利率政策', 
N'美聯儲今日宣布維持基準利率不變，以應對全球通膨壓力。',
3, 1, 300, GETDATE(), GETDATE(), 0);
GO

-- ----------------------------
-- 建立 news_type 表 (新聞類型)
-- ----------------------------
IF OBJECT_ID('news_type', 'U') IS NOT NULL
    DROP TABLE news_type;
GO

CREATE TABLE news_type (
    tid INT IDENTITY(1,1) PRIMARY KEY,  -- 類型 ID
    tname NVARCHAR(50) NOT NULL,  -- 類型名稱
    version INT DEFAULT 1,  -- 樂觀鎖
    is_deleted BIT DEFAULT 0  -- 是否刪除
);
GO

-- 插入測試數據 (全球新聞類型)
INSERT INTO news_type (tname) VALUES 
(N'政治'),
(N'科技'),
(N'經濟'),
(N'體育'),
(N'其他');
GO

-- ----------------------------
-- 建立 news_user 表 (使用者)
-- ----------------------------
IF OBJECT_ID('news_user', 'U') IS NOT NULL
    DROP TABLE news_user;
GO

CREATE TABLE news_user (
    uid INT IDENTITY(1,1) PRIMARY KEY,  -- 使用者 ID
    username NVARCHAR(50) NOT NULL UNIQUE,  -- 登入名稱
    user_pwd NVARCHAR(100) NOT NULL,  -- 加密密碼
    nick_name NVARCHAR(50) NOT NULL,  -- 暱稱
    version INT DEFAULT 1,  -- 樂觀鎖
    is_deleted BIT DEFAULT 0  -- 是否刪除
);
GO

-- 插入測試數據 (新聞使用者)
INSERT INTO news_user (username, user_pwd, nick_name) VALUES
(N'zhangsan', N'e10adc3949ba59abbe56e057f20f883e', N'張三'),
(N'lisi', N'e10adc3949ba59abbe56e057f20f883e', N'李四'),
(N'zhangxiaoming', N'e10adc3949ba59abbe56e057f20f883e', N'張小明'),
(N'xiaohei', N'e10adc3949ba59abbe56e057f20f883e', N'李小黑');
GO
