package com.example.fragmentbestpractice;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class NewsTitleFragment extends Fragment implements OnItemClickListener {

	private ListView newsTitleListView;
	private List<News> newsList;
	private NewsAdapter adapter;
	private boolean isTwoPane;

	//根据碎片的生命周期,onAttach()方法会首先执行,在这里做数据的初始化操作
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		newsList = getNews(); // 初始化新闻数据,自己写的,就在此类中
		adapter = new NewsAdapter(activity, R.layout.news_item, newsList);
	}

	// 碎片的一个活动周期,创建界面
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 创建布局,获得ListView,并设置adapter,设置监听器
		View view = inflater
				.inflate(R.layout.news_title_frag, container, false);
		newsTitleListView = (ListView) view
				.findViewById(R.id.news_title_list_view);
		newsTitleListView.setAdapter(adapter);
		newsTitleListView.setOnItemClickListener(this);
		return view;
	}

	// 碎片Activity创建
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (getActivity().findViewById(R.id.visibility_layout) != null) {
			isTwoPane = true;
		} else {
			isTwoPane = false;
		}
	}

	private List<News> getNews() {
		List<News> newsList = new ArrayList<News>();
		News news1 = new News();
		news1.setTitle("中国北斗导航2020年服务全球 北斗芯片已用智能机");
		news1.setContent("搜狐科技消息7月24日，综合中新社消息，" +
				"中国卫星导航系统管理办公室总工程师张春领在“2016中国北斗与物联产业技术研讨会”" +
				"上表示，中国自主研制的北斗卫星导航系统，已初步具备向全球拓展的基础，" +
				"正在成为国家外交名片，走向海外。该系统将于2018年形成面向“一带一路”" +
				"沿线国家的服务能力，2020年形成全球服务能力。张春领表示，" +
				"北斗系统提供区域服务已有3年多，检测评估结果表明，在北京、南京以及低纬度地区，" +
				"定位精度更高。在今年发射了两颗IGSO卫星之后，" +
				"服务区内北斗系统的可靠性和服务性能将得到进一步提高。北斗的商用备受关注。" +
				"据了解，新一代国产北斗芯片模块等核心基础产品，正逐步投放市场，可满足智能手机、" +
				"平板电脑、穿戴式设备等应用需求，改变了中国高精度卫星导航核心产品完全依赖进口的局面" +
				"。另外，交通运输部、地震局、气象局、测绘地理信息局等多个部门" +
				"，已建成覆盖全国北斗地基增强框架基准站网，后续系统正式投入使用后，" +
				"将为中国境内用户提供米级、分米级实时定位服务，部分地区精度可达厘米级、" +
				"乃至于毫米级。中科院微电子研究所所长叶甜春称，" +
				"无论是在现代制造企业中对生产、运输的跟踪定位，还是在健康养老方面的信息传输，" +
				"乃至于在灾害预警方面的固定建筑物卫星定位实时监测，都将成为北斗系统应用广大市场。");
		newsList.add(news1);
		News news2 = new News();
		news2.setTitle("《LOL》纯单排模式或将回归！再也不怕单排遇到开黑了");
		news2.setContent("相信各位撸友都有单排被对面开黑碾压的经历，说实话，" +
				"这样的比赛并不公平，而对于电竞游戏来说，不公平往往就是没落的开始。" +
				"不过近日，一位国外网友发帖爆料，拳头方面或将重新考虑《LOL》的单排模式。" +
				"这个爆料并非空穴来风，因为他在一场排位后，收到了来自系统的一份调查问卷，" +
				"内容是这样的：亲爱的玩家，我们很尊重你的看法，请选择：" +
				"我不希望移除动态组排位模式（也就是我们所说的开黑模式），" +
				"然后请玩家在下面选择几种意见，分别是从十分同意到十分不同意。" +
				"而这个玩家显然是一位单排模式的忠实维护者，他选择了”十分不同意“，" +
				"言下之意就是十分希望移除组排模式。而这个帖子也引起了不少玩家的共鸣，" +
				"看来不只是国内玩家对于单排模式的渴望，国外友人亦是如此。说起新的预选位模式，" +
				"自从上线后就备受很多玩家的争议，尤其是一些休闲玩家，由于没有固定组队，" +
				"所以只能单排，但是单排又经常会遇到一些闹心的事，比如队友太坑，排的高段位玩家，" +
				"或者是对面3黑被碾压等等。而玩家对于单排的呼声也在之前被拳头的一纸公告浇了冷水。" +
				"不过从这份调查问卷，我们也不难发现，拳头也难得是要重新考虑纯单排模式吗？");
		newsList.add(news2);
		return newsList;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		News news = newsList.get(position);
		if (isTwoPane) {
			// 如果是双页模式,则刷新NewsContentFragment中的内容
			NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager()
					.findFragmentById(R.id.news_content_fragment);
			newsContentFragment.refresh(news.getTitle(), news.getContent());
		} else {
			// 如果是的单页模式,则直接启动NewsContentActivity
			NewsContentActivity.actionStart(getActivity(), news.getTitle(),
					news.getContent());

		}
	}

}
