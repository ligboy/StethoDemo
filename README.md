# Stetho Demo
包含OkHttp、Glide的仅Debug build type具有的Stetho Demo示例

 Stetho是facebook推出的Android诊断调试桥接框架组件，让我们可以通过chrome开发者工具查看调试我们的应用数据及状态。  
 本demo通过一个分BuildType工具类实现仅在debug build type进行stetho调试，并且仅在需要时引入stetho library package。  
   
包含：  
 * SharedPreference Inspection  
 * OkHttp Network Inspection  
 * Glide Network Inspection  
 * Database Inspection  
 * View Hierarchy  
 
