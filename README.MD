# Android Button with a ProgressBar

- 这是一个带环形progressBar效果的Button，用于显示正在加载数据和反馈结果：   
  
<div align=center>![GIF](http://oxtf52ecv.bkt.clouddn.com/blog/181012/8Fef5dmfD0.gif)  </div>  

## 使用方法
> 使用起来非常简单，先把progressbutton文件夹的内容拷贝到合适的位置，然后   

引用控件：
```xml
    <com.even.progressbutton.ProgressButton
        android:id="@+id/btn_progress"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="ProgressButton"
        android:textColor="@color/white"
        android:theme="@style/BlueButton"/>
```
新建style修改ProgressButton颜色且不丢失水波纹效果：
```xml
    <style name="BlueButton" parent="AppTheme">
        <item name="buttonStyle">@style/Widget.AppCompat.Button</item>
        <item name="colorButtonNormal">@color/colorBlue</item>
    </style>
```
在activity中设置各种效果：
```
progressButton.startLoad();
progressButton.stopLoad();
progressButton.loadSuccess();
progressButton.loadFail();
```

