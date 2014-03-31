### 歷程記錄 ###
本專案改自 Google 的 DynaTable 範例，
因為該範例本身已具備一個搭配 Grid 實作好的 Pager，

所以第一階段透過修改後端資料提供的部分，
將相關的程式碼換成 Objectify 函式庫對資料庫進行分頁存取，
並從 DynaTable 範例自訂的 Pager 確認過後端資料庫可正常存取各分頁資料。

隨後完成 CellTableMain 等相關程式，確認既有的 CellTable, 
SimplePager 等類別可與 Objectify 一起運作，達成預設的分頁的目的。

過程中因為 RPC 的程式與既有專案的版本及風格差異太大，
也將 RPC 相關呼叫的部分替換為 GF 函式庫所使用的呼叫方式。

此外針對 Sort 及 Search 相關功能完成部分程式碼。

以上為第一次 Commit 包含的部分。

第二階段的部分主要將 RPC 呼叫中會使用到的 Objectify 參數，
包裝成一個資料物件，簡化 RPC 呼叫及方法撰寫數量。