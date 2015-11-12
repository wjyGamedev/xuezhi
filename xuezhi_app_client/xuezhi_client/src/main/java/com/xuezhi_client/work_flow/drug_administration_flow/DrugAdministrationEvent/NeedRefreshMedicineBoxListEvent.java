package com.xuezhi_client.work_flow.drug_administration_flow.DrugAdministrationEvent;

import com.module.event.BaseLogicalEvent;
import com.module.event.EventID;

/**
 * Created by Administrator on 2015/11/12.
 */
public class NeedRefreshMedicineBoxListEvent extends BaseLogicalEvent
{
	public NeedRefreshMedicineBoxListEvent()
	{
		super(EventID.REFRESH_MEDICINE_BOX_LIST);
	}
}