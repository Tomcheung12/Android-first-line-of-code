package com.example.fragmentbestpractice;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

/**
 * 2016年7月24日9:08:12
 * 碎片的最佳实践-一个简易版的新闻应用
 * @author XFHY
 * 
 * 步骤:
 *  1.新建一个新闻的实体类,News.里面有标题和内容2个属性.
 *  2.新建一个news_item.xml布局,新闻列表中子项的布局,就是新闻的标题的布局
 *  3.新建一个新闻标题列表的适配器,让这个适配器继承自ArrayAdapter,并将泛型指定为News类
 *  4.新建布局文件news_content_frag.xml,这是新闻内容布局, (新闻标题,细线,正文部分).
 *  5.新建一个NewsContentFragment类,继承自Fragment,这个类用于加载新闻内容布局(第4条)
 *  6.新建一个在活动中使用的新闻内容布局,新建news_content.xml,里面加载了新闻布局(第5条那个类),而且又是
 *     碎片.   碎片的布局和活动的布局都是这个news_content.xml
*   7.新建NewsContentActivity类,作为显示新闻内容的活动,在里面加载碎片布局,如果用户是小手机则点击新闻标题
*      后就跳转到这个活动
*   8.新建一个显示新闻列表的布局,news_title_frag.xml,里面只有一个ListView,这个布局是给碎片用的.
*   9.所有需要新建一个NewsTitleFragment,继承自Fragment,这个碎片是为了加载上面的ListView,在里面判断
*      是双页模式还是单页模式,如果是双页模式,则加载碎片,单页模式则启动活动NewsContentActivity
*   10.在主activity_main中,加载刚刚的ListView碎片,name=NewsTitleFragment
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }
}
