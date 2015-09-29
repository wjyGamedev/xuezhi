package com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_page.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.module.frame.IBaseAdapter;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedical;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicalStock;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicalStockList;
import com.xuzhi_client.xuzhi_app_client.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/29.
 */
public class DrugAdministrationAdapter extends IBaseAdapter
{
	private DMedicalStockList        m_medicalStockList = null;
	private LayoutInflater           m_layoutInflater   = null;
	private ArrayList<DMedicalStock> m_medicalStock     = new ArrayList<DMedicalStock>();

	@Override
	public int getCount()
	{
		ArrayList<DMedicalStock> medicalStocks = m_medicalStockList.getMedicalStocks();
		if (medicalStocks == null || medicalStocks.isEmpty())
			return 0;

		return medicalStocks.size();
	}


	public DrugAdministrationAdapter(Context context)
	{
		super(context);
		init();
	}

	private void init()
	{
		m_medicalStockList = DBusinessData.GetInstance().getMedicalStockList();
		m_layoutInflater = LayoutInflater.from(m_context);
	}

	@Override
	public Object getItem(int position)
	{
		return null;
	}


	@Override
	public long getItemId(int position)
	{
		if (position >= m_medicalStock.size())
		{
			TipsDialog.GetInstance().setMsg("position >= m_medicalStock.size()[position:=" + position + "][m_medicalStock.size():=" +
													m_medicalStock.size() + "]"
										   );
			TipsDialog.GetInstance().show();
			return 0;
		}

		DMedicalStock tmpMedicalStock = m_medicalStock.get(position);
		return tmpMedicalStock.getID();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = null;

		if (convertView == null)
		{
			convertView = m_layoutInflater.inflate(R.layout.item_drug_stock, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder)convertView.getTag();
		}

		m_medicalStock = m_medicalStockList.getMedicalStocks();
		viewHolder.initContent(m_medicalStock, position);
		return convertView;
	}

}

final class ViewHolder
{

	//widget
	@Bind (R.id.drug_name_tv)           TextView m_drugNameTV          = null;    //药瓶名称
	@Bind (R.id.drug_reminder_state_tv) TextView m_drugReminderStateTV = null;    //药品提醒状态
	@Bind (R.id.drug_keep_num_tv)       TextView m_drugKeepNumTV       = null;    //药瓶持有数量
	@Bind (R.id.drug_alert_num_tv)      TextView m_drugAlertNumTV      = null;    //药瓶警报数量
	@Bind (R.id.drug_add_date_tv)       TextView m_drugAddDateTV       = null;    //药瓶添加日期
	@Bind (R.id.drug_run_out_date_tv)   TextView m_drugRunOutDateTV    = null;    //药品预计警报日期

	//logical
	private View m_view = null;

	private SimpleDateFormat m_simpleDateFormat = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY);

	private final static String MEDICALREMINDERSTATEOPEN  = "开启";
	private final static String MEDICALREMINDERSTATECLOSE = "关闭";

	public ViewHolder(View view)
	{
		m_view = view;
		ButterKnife.bind(this, m_view);
	}

	public void initContent(ArrayList<DMedicalStock> m_medicalStock, int position)
	{
		if (m_medicalStock == null || m_medicalStock.isEmpty())
		{
			TipsDialog.GetInstance().setMsg("m_medicalStock == null");
			TipsDialog.GetInstance().show();
			return;
		}

		if (position >= m_medicalStock.size())
		{
			TipsDialog.GetInstance().setMsg("position >= m_medicalStock.size()[position:=" + position + "][m_medicalStock.size():=" +
													m_medicalStock.size() + "]"
										   );
			TipsDialog.GetInstance().show();
			return;
		}

		DMedicalStock tmpMedicalStock = m_medicalStock.get(position);
		int           medicalID       = tmpMedicalStock.getMID();
		DMedical      tmpmedical      = DBusinessData.GetInstance().getMedicalList().getMedicalByID(medicalID);

		m_drugNameTV.setText(tmpmedical.getName());

		if (tmpMedicalStock.isMedicalReminderState()) {m_drugReminderStateTV.setText(MEDICALREMINDERSTATEOPEN);}
		else {m_drugReminderStateTV.setText(MEDICALREMINDERSTATECLOSE);}

		m_drugKeepNumTV.setText(Double.toString(tmpMedicalStock.getRemianNum()));
		m_drugAlertNumTV.setText(Double.toString(tmpMedicalStock.getWaringNum()));

		Calendar addDate            = tmpMedicalStock.getAddCalendar();
		String   adddateDescription = null;
		if (addDate != null)
		{
			adddateDescription = m_simpleDateFormat.format(addDate);
			m_drugAddDateTV.setText(adddateDescription);
		}

		Calendar warningDate            = tmpMedicalStock.getWarningCalendar();
		String   warningDateDescription = null;
		if (warningDate != null)
		{
			warningDateDescription = m_simpleDateFormat.format(warningDate);
			m_drugRunOutDateTV.setText(warningDateDescription);
		}

	}
}