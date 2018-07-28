# OneNETHelper
一个适用于Android，方便从OneNET平台获取数据的工具。  

**本工具实现的功能：**  
1. 一个简易的框架，只需设置设备id、数据流id，就能通过http协议从OneNET平台上同步获取相关的数据。  
2. 一个用于显示历史数据点列表的ListView，自动异步加载数据，并支持上拉加载下一页。  

### 使用方法
- **添加依赖**  
**方法一**：下载本项目源码，导入module。  
**方法二**：从jitpack上下载依赖：  
在项目build.gradle中添加：  
```
	allprojects {  
		repositories {  
			...  
			maven { url 'https://jitpack.io' }  
		}
	}
```  
在module的build.gradle中添加依赖（项目依赖了retrofit2框架，需要一同导入）：
```
	dependencies {
          implementation 'com.github.Hydri0n:OneNETHelper:0.2'
          implementation 'com.squareup.retrofit2:retrofit:2.3.0'
          implementation 'com.squareup.retrofit2:converter-gson:2.3.0'
	}
```

- **使用框架同步获取数据**  
实例化一个Device类，设置设备的相关信息：
`Device device = new Device("设备的id",
"设备的api-key");`  
得到一个Device实例后，调用它的相应方法同步获取你需要的数据：  
	1. 获取设备的名称： `device.getDeviceTitle()`
	2. 查询设备是否在线，返回一个布尔值： `device.isOnline()`
	3. 获取数据流的一个最新的数据，返回一个DeviceDataPoint的实例：`device.getCurrentDataPoint("数据流id")`  
	你可以通过DeviceDataPoint的getValue()方法和getTime()方法来得到该数据点的值与更新时间。  
	4. 获取数据流在两个时间区间内的所有数据点： `device.getDataPointsWithinLimit(数据流id，开始时间，结束时间)`
	5. 你也可以通过*getDataPoints()*方法，设置任意的参数组合来获取需要的数据。该方法的具体参数及含义如下：  
		> **limit** 返回的数据点个数上限, **startTime** 提取数据点的开始时间, **endTime** 提取数据点的结束时间, **duration** 查询时间区间,单位为s, **cursor** 指定本次请求继续从cursor位置开始提取数据, **sort** 升序(ASC)/倒序(DESC)
		
	使用示例：  
	```
	      //获取并遍历两个时间点间所有的数据点
        Device device = new Device("设备id","设备apikey");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2 = format.parse("2018-05-12 21:41:02");
        Date date1 = format.parse("2018-05-13 21:40:48");
        List<DeviceDataPoint> dataPoints = device.getDataPointsBetweenTwoTimes(
                "temp",date1,date2);
        for (DeviceDataPoint p:dataPoints) {
            System.out.println(p.getValue() + " " +p.getTime());
        }
	```  

	\*另外需要注意的是，以上方法都是同步加载数据，所以不能在安卓应用的主线程中运行。
- **创建一个显示数据流历史数据的列表组件**  
该ListView组件用于倒序显示一个数据流的所有历史数据，上拉至底部时加载更多数据。  
使用时，直接在activity的布局文件中添加一个DataPointsListView：
```
    <xyz.hydrion.onenethelper.DataPointsListView
        android:id="@+id/listView"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
```  
  在获取该组件的实例后，调用
`setDataStream(String devId,String apiKey,String dataStreamId)`方法，组件即会自动设置adapter并异步加载数据。  
此后你也可以通过调用changeDataStream()方法来更改列表加载的数据流。
