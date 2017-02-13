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

	//������Ƭ����������,onAttach()����������ִ��,�����������ݵĳ�ʼ������
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		newsList = getNews(); // ��ʼ����������,�Լ�д��,���ڴ�����
		adapter = new NewsAdapter(activity, R.layout.news_item, newsList);
	}

	// ��Ƭ��һ�������,��������
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// ��������,���ListView,������adapter,���ü�����
		View view = inflater
				.inflate(R.layout.news_title_frag, container, false);
		newsTitleListView = (ListView) view
				.findViewById(R.id.news_title_list_view);
		newsTitleListView.setAdapter(adapter);
		newsTitleListView.setOnItemClickListener(this);
		return view;
	}

	// ��ƬActivity����
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
		news1.setTitle("�й���������2020�����ȫ�� ����оƬ�������ܻ�");
		news1.setContent("�Ѻ��Ƽ���Ϣ7��24�գ��ۺ���������Ϣ��" +
				"�й����ǵ���ϵͳ����칫���ܹ���ʦ�Ŵ����ڡ�2016�й�������������ҵ�������ֻᡱ" +
				"�ϱ�ʾ���й��������Ƶı������ǵ���ϵͳ���ѳ����߱���ȫ����չ�Ļ�����" +
				"���ڳ�Ϊ�����⽻��Ƭ�������⡣��ϵͳ����2018���γ�����һ��һ·��" +
				"���߹��ҵķ���������2020���γ�ȫ������������Ŵ����ʾ��" +
				"����ϵͳ�ṩ�����������3��࣬�����������������ڱ������Ͼ��Լ���γ�ȵ�����" +
				"��λ���ȸ��ߡ��ڽ��귢��������IGSO����֮��" +
				"�������ڱ���ϵͳ�Ŀɿ��Ժͷ������ܽ��õ���һ����ߡ����������ñ��ܹ�ע��" +
				"���˽⣬��һ����������оƬģ��Ⱥ��Ļ�����Ʒ������Ͷ���г��������������ֻ���" +
				"ƽ����ԡ�����ʽ�豸��Ӧ�����󣬸ı����й��߾������ǵ������Ĳ�Ʒ��ȫ�������ڵľ���" +
				"�����⣬��ͨ���䲿������֡�����֡���������Ϣ�ֵȶ������" +
				"���ѽ��ɸ���ȫ�������ػ���ǿ��ܻ�׼վ��������ϵͳ��ʽͶ��ʹ�ú�" +
				"��Ϊ�й������û��ṩ�׼������׼�ʵʱ��λ���񣬲��ֵ������ȿɴ����׼���" +
				"�����ں��׼����п�Ժ΢�����о�������Ҷ�𴺳ƣ�" +
				"���������ִ�������ҵ�ж�����������ĸ��ٶ�λ�������ڽ������Ϸ������Ϣ���䣬" +
				"���������ֺ�Ԥ������Ĺ̶����������Ƕ�λʵʱ��⣬������Ϊ����ϵͳӦ�ù���г���");
		newsList.add(news1);
		News news2 = new News();
		news2.setTitle("��LOL��������ģʽ�򽫻ع飡��Ҳ���µ�������������");
		news2.setContent("���Ÿ�λߣ�Ѷ��е��ű����濪����ѹ�ľ�����˵ʵ����" +
				"�����ı���������ƽ�������ڵ羺��Ϸ��˵������ƽ��������û��Ŀ�ʼ��" +
				"�������գ�һλ�������ѷ������ϣ�ȭͷ��������¿��ǡ�LOL���ĵ���ģʽ��" +
				"������ϲ��ǿ�Ѩ���磬��Ϊ����һ����λ���յ�������ϵͳ��һ�ݵ����ʾ�" +
				"�����������ģ��װ�����ң����Ǻ�������Ŀ�������ѡ��" +
				"�Ҳ�ϣ���Ƴ���̬����λģʽ��Ҳ����������˵�Ŀ���ģʽ����" +
				"Ȼ�������������ѡ����������ֱ��Ǵ�ʮ��ͬ�⵽ʮ�ֲ�ͬ�⡣" +
				"����������Ȼ��һλ����ģʽ����ʵά���ߣ���ѡ���ˡ�ʮ�ֲ�ͬ�⡰��" +
				"����֮�����ʮ��ϣ���Ƴ�����ģʽ�����������Ҳ�����˲�����ҵĹ�����" +
				"������ֻ�ǹ�����Ҷ��ڵ���ģʽ�Ŀ�������������������ˡ�˵���µ�Ԥѡλģʽ��" +
				"�Դ����ߺ�ͱ��ܺܶ���ҵ����飬������һЩ������ң�����û�й̶���ӣ�" +
				"����ֻ�ܵ��ţ����ǵ����־���������һЩ���ĵ��£��������̫�ӣ��ŵĸ߶�λ��ң�" +
				"�����Ƕ���3�ڱ���ѹ�ȵȡ�����Ҷ��ڵ��ŵĺ���Ҳ��֮ǰ��ȭͷ��һֽ���潽����ˮ��" +
				"��������ݵ����ʾ�����Ҳ���ѷ��֣�ȭͷҲ�ѵ���Ҫ���¿��Ǵ�����ģʽ��");
		newsList.add(news2);
		return newsList;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		News news = newsList.get(position);
		if (isTwoPane) {
			// �����˫ҳģʽ,��ˢ��NewsContentFragment�е�����
			NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager()
					.findFragmentById(R.id.news_content_fragment);
			newsContentFragment.refresh(news.getTitle(), news.getContent());
		} else {
			// ����ǵĵ�ҳģʽ,��ֱ������NewsContentActivity
			NewsContentActivity.actionStart(getActivity(), news.getTitle(),
					news.getContent());

		}
	}

}
